package com.example.templater.documentService.docCombine;

import com.example.templater.documentService.tempBuilder.TempParams;
import com.example.templater.documentService.tempParamsGetter.HeadingContent;
import com.example.templater.documentService.tempParamsGetter.HeadingWithText;
import com.example.templater.documentService.tempParamsGetter.TempParamsGetter;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

public class DocCombiner {
    List<UnresolvedHeadings> unresolvedMainHeadings;
    List<UnresolvedHeadings> unresolvedSubHeadings;

    public List<UnresolvedHeadings> getUnresolvedMainHeadings() {
        return unresolvedMainHeadings;
    }

    public void setUnresolvedMainHeadings(List<UnresolvedHeadings> unresolvedMainHeadings) {
        this.unresolvedMainHeadings = unresolvedMainHeadings;
    }

    public List<UnresolvedHeadings> getUnresolvedSubHeadings() {
        return unresolvedSubHeadings;
    }

    public void setUnresolvedSubHeadings(List<UnresolvedHeadings> unresolvedSubHeadings) {
        this.unresolvedSubHeadings = unresolvedSubHeadings;
    }


    public XWPFDocument combineDocs(List<XWPFDocument> documentList) {
        if (documentList == null) {
            return null;
        }
        XWPFDocument document = documentList.get(0);
        for (int i = 1; i < documentList.size(); ++i) {
            List<HeadingWithText> mainHList = TempParamsGetter.getMainHeadingsList(document);
            List<HeadingWithText> mainHListTBA = TempParamsGetter.getMainHeadingsList(documentList.get(i));
            for (HeadingWithText mH : mainHList) {
                List<Integer> scores = new ArrayList<>();
                for (HeadingWithText mHTBA : mainHListTBA) {
                    HeadingsMatcher matcher = new HeadingsMatcher();
                    int score = matcher.headingsMatch(document.getParagraphs(),
                            documentList.get(i).getParagraphs(), mH.getHeading(), mHTBA.getHeading());
                    scores.add(score);
                }
                int maxI = getIterByMaxValue(scores);
                if (scores.get(maxI) >= 100) {
                    document = combineMainHeadings(document, documentList.get(i), mH, mainHListTBA.get(maxI));
                    mainHListTBA.remove(maxI);
                }
            }
            if (!mainHListTBA.isEmpty()) {
                UnresolvedHeadings unres = new UnresolvedHeadings();
                List<HeadingWithText> headings = new ArrayList<>(mainHListTBA);
                unres.setDocument(documentList.get(i));
                unres.setHeadings(headings);
                if (unresolvedMainHeadings == null) {
                    unresolvedMainHeadings = new ArrayList<>();
                }
                unresolvedMainHeadings.add(unres);
            }
        }

        if (unresolvedMainHeadings != null && !unresolvedMainHeadings.isEmpty()) {
            for (UnresolvedHeadings unres : unresolvedMainHeadings) {
                document = insertUnresolvedMainHeadings(document, unres);
            }
        }

        return document;
    }

    public XWPFDocument combineMainHeadings(XWPFDocument docS, XWPFDocument docTBA, HeadingWithText l,
                                            HeadingWithText r) {
        XWPFDocument document = docS;
        document = insertTextAfterHeading(document, l, r.getText(), r.getTables());
        document = combineMainHeadingsContent(docS, docTBA, l.getHeading(), r.getHeading());
        return document;
    }

    public XWPFDocument insertTextAfterHeading(XWPFDocument document, HeadingWithText heading,
                                               List<XWPFParagraph> text, List<XWPFTable> tables) {
        XWPFParagraph targerPar;
        if (heading.getText() != null && !heading.getText().isEmpty()) {
            targerPar = heading.getText().get(heading.getText().size() - 1);
        }
        else {
            targerPar = heading.getHeading();
        }
        if (text != null && (!text.isEmpty())) {
            XmlCursor cursor = targerPar.getCTP().newCursor();
            for (XWPFParagraph p : text) {
                cursor.toEndToken();
                while(cursor.toNextToken() != XmlCursor.TokenType.START);
                XWPFParagraph newPar = document.insertNewParagraph(cursor);
                XWPFRun run = newPar.createRun();
                run.setText(p.getText() + "\n");
            }
            if (tables != null && !tables.isEmpty()) {
                for (XWPFTable t : tables) {
                    document = insertTable(document, t, cursor);
                }
            }
        }
        return document;
    }

    public XWPFDocument combineMainHeadingsContent(XWPFDocument docS, XWPFDocument docTBA,
                                                   XWPFParagraph l, XWPFParagraph r) {
        XWPFDocument document = docS;
        HeadingContent hcL = TempParamsGetter.getHeadingContent(docS, l);
        HeadingContent hcR = TempParamsGetter.getHeadingContent(docTBA, r);
        List<HeadingWithText> listL = hcL.getSubHList();
        List<HeadingWithText> listR = hcR.getSubHList();
        List<HeadingWithText> resolved = new ArrayList<>();

        for (HeadingWithText hwtL : listL) {
            for (HeadingWithText hwtR : listR) {
                if (hwtL.getHeading().getStyle().equals(hwtR.getHeading().getStyle())
                        && !resolved.contains(hwtR)) {
                    if (hwtL.getHeading().getText().equals(hwtR.getHeading().getText())) {
                        document = insertTextAfterHeading(document, hwtL, hwtR.getText(), hwtR.getTables());
                        resolved.add(hwtR);
                    }
                }
            }
        }
        List<HeadingWithText> unresolved = new ArrayList<>();
        for (HeadingWithText hwt : listR) {
            if (!resolved.contains(hwt)) {
                unresolved.add(hwt);
            }
        }

        if (!unresolved.isEmpty()) {
            UnresolvedHeadings unres = new UnresolvedHeadings();
            if (unresolvedSubHeadings == null) {
                unresolvedSubHeadings = new ArrayList<>();
            }
            unres.setDocument(docTBA);
            List<HeadingWithText> headings = new ArrayList<>(unresolved);
            unres.setHeadings(headings);
            unresolvedSubHeadings.add(unres);

            for (UnresolvedHeadings un : unresolvedSubHeadings) {
                for (HeadingWithText ht : un.getHeadings()) {
                    System.out.println(ht.getHeading().getText());
                }
            }
        }
        return document;
    }


    public XWPFDocument insertHeading(XWPFDocument document, HeadingWithText hwt) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        paragraph.setStyle(hwt.getHeading().getStyle());
        run.setText(hwt.getHeading().getText());
        for (XWPFParagraph p : hwt.getText()) {
            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setText(p.getText());
            if (!p.getRuns().isEmpty()) {
                run.setFontFamily(p.getRuns().get(0).getFontFamily());
                run.setFontSize(11);
                run.setColor(p.getRuns().get(0).getColor());
            }
        }
        for (XWPFTable t : hwt.getTables()) {
            XmlCursor cursor = paragraph.getCTP().newCursor();
            document = insertTable(document, t, cursor);
        }
        return document;
    }

    public XWPFDocument insertUnresolvedMainHeadings(XWPFDocument document, UnresolvedHeadings unres) {
        for (HeadingWithText hwt : unres.getHeadings()) {
            document = insertHeading(document, hwt);
            XWPFDocument doc = unres.getDocument();
            HeadingContent hc = TempParamsGetter.getHeadingContent(doc, hwt.getHeading());
            for (HeadingWithText heading : hc.getSubHList()) {
                document = insertHeading(document, heading);
            }
        }
        return document;
    }

    private int getIterByMaxValue(List<Integer> scores) {
        int max = 0;
        for (Integer score : scores) {
            if (max < score) {
                max = score;
            }
        }
        return scores.indexOf(max);
    }

    public XWPFDocument insertTable(XWPFDocument document, XWPFTable t, XmlCursor cursor) {
        cursor.toEndToken();
        while(cursor.toNextToken() != XmlCursor.TokenType.START);
        XWPFTable newTable = document.insertNewTbl(cursor);
        int num_rows = t.getNumberOfRows();
        int num_coloms = t.getRow(0).getTableCells().size();

        XWPFTableCell c = newTable.getRow(0).getCell(0);
        CTTblWidth cellW = c.getCTTc().addNewTcPr().addNewTcW();
        cellW.setType(STTblWidth.DXA);
        CTTcPr pr = c.getCTTc().addNewTcPr();
        pr.addNewNoWrap();
        cellW.setW(t.getRow(0).getCell(0).getCTTc().getTcPr().getTcW().getW());
        XWPFRun run = c.getParagraphArray(0).createRun();
        XWPFParagraph p = t.getRow(0).getCell(0).getParagraphs().get(0);
        XWPFRun r = null;
        if (p != null && p.getRuns() != null && !p.getRuns().isEmpty()) {
            r = p.getRuns().get(0);
        }
        if (r != null) {
            if (r.isBold()) {
                run.setBold(true);
            }
            if (r.isItalic()) {
                run.setItalic(true);
            }
            run.setFontSize(11);
            run.setFontFamily(r.getFontFamily());
            run.setColor(r.getColor());
            run.setText(r.getText(0));
        }

        for (int k = 1; k < num_coloms; ++k) {
            XWPFTableCell cell = newTable.getRow(0).createCell();
            CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
            cellWidth.setType(STTblWidth.DXA);
            CTTcPr ctTcPr = cell.getCTTc().addNewTcPr();
            pr.addNewNoWrap();
            cellWidth.setW(t.getRow(0).getCell(k).getCTTc().getTcPr().getTcW().getW());
            cell.setColor(t.getRow(0).getCell(k).getColor());
            XWPFParagraph par = cell.getParagraphs().get(0);
            run = par.createRun();
            p = t.getRow(0).getCell(k).getParagraphs().get(0);
            r = null;
            if (p != null && p.getRuns() != null && !p.getRuns().isEmpty()) {
                r = p.getRuns().get(0);
            }
            if (r != null) {
                if (r.isBold()) {
                    run.setBold(true);
                }
                if (r.isItalic()) {
                    run.setItalic(true);
                }
                run.setFontSize(11);
                run.setFontFamily(r.getFontFamily());
                run.setColor(r.getColor());
                run.setText(r.getText(0));
            }
        }

        if (num_rows > 1) {
            for (int i = 1; i < num_rows; ++i) {
                XWPFTableRow row = newTable.createRow();
                p = t.getRow(i).getCell(0).getParagraphs().get(0);
                r = null;
                if (p != null && p.getRuns() != null && !p.getRuns().isEmpty()) {
                    r = p.getRuns().get(0);
                }
                if (r != null) {
                    run = row.getCell(0).getParagraphs().get(0).createRun();
                    if (r.isBold()) {
                        run.setBold(true);
                    }
                    if (r.isItalic()) {
                        run.setItalic(true);
                    }
                    run.setFontSize(11);
                    run.setFontFamily(r.getFontFamily());
                    run.setColor(r.getColor());
                    run.setText(r.getText(0));
                }

                if (num_coloms > 1) {
                    for (int j = 1; j < num_coloms; ++j) {
                        XWPFTableCell cell = row.getCell(j);
                        cell.setColor(t.getRow(i).getCell(j).getColor());
                        p = t.getRow(i).getCell(j).getParagraphs().get(0);
                        r = null;
                        if (p != null && p.getRuns() != null && !p.getRuns().isEmpty()) {
                            r = p.getRuns().get(0);
                        }
                        if (r != null) {
                            run = cell.getParagraphArray(0).createRun();
                            if (r.isBold()) {
                                run.setBold(true);
                            }
                            if (r.isItalic()) {
                                run.setItalic(true);
                            }
                            run.setFontSize(11);
                            run.setFontFamily(r.getFontFamily());
                            run.setColor(r.getColor());
                            run.setText(r.getText(0));
                        }
                    }
                }
            }
        }
        return document;
    }


    public HeadingWithText findLastHeading(XWPFDocument document, List<HeadingWithText> list, String Hstyle,
                                           String SHstyle) {
        HeadingWithText lastH = list.get(list.size() - 1);
        for (HeadingWithText hwt : list) {
            if (hwt.getHeading().getStyle().equals(Hstyle)) {
                lastH = hwt;
            }
        }
        List<HeadingWithText> content = TempParamsGetter.getHeadingContent(document, lastH.getHeading()).getSubHList();
        HeadingWithText lastSH = null;
        for (HeadingWithText hwt : content) {
            if (hwt.getHeading().getStyle().equals(SHstyle)) {
                lastSH = hwt;
            }
        }
        if (lastSH == null) {
            return lastH;
        }
        return lastSH;
    }

    public XWPFDocument insertHeadingAfterHeading(XWPFDocument document, HeadingWithText heading,
                                                  HeadingWithText target) {
        XWPFParagraph targerPar;
        if (heading.getText() != null && !heading.getText().isEmpty()) {
            targerPar = heading.getText().get(heading.getText().size() - 1);
        }
        else {
            targerPar = heading.getHeading();
        }
        XmlCursor cursor = targerPar.getCTP().newCursor();
        cursor.toEndToken();
        while(cursor.toNextToken() != XmlCursor.TokenType.START);
        XWPFParagraph newPar = document.insertNewParagraph(cursor);
        if (newPar == null) {
            System.out.println("XXX");
        }
        newPar.setStyle(target.getHeading().getStyle());
        XWPFRun run = newPar.createRun();
        run.setText(target.getHeading().getText());
        targerPar = newPar;
        for (XWPFParagraph p : target.getText()) {
            cursor = targerPar.getCTP().newCursor();
            cursor.toEndToken();
            while(cursor.toNextToken() != XmlCursor.TokenType.START);
            newPar = document.insertNewParagraph(cursor);
            run = newPar.createRun();
            run.setText(p.getText() + "\n");
            targerPar = p;
        }
        List<XWPFTable> tables = target.getTables();
        if (tables != null && !tables.isEmpty()) {
            for (XWPFTable t : tables) {
                document = insertTable(document, t, cursor);
            }
        }
        return document;
    }

    public XWPFDocument insertHeadingContent(XWPFDocument document, HeadingWithText heading) {
        HeadingContent hc = TempParamsGetter.getHeadingContent(document, heading.getHeading());
        List<HeadingWithText> listHWT = hc.getSubHList();
        if (listHWT != null) {
            System.out.println(heading.getHeading().getText());
            HeadingWithText last = heading;
            for (HeadingWithText hwt : listHWT) {
                document = insertHeadingAfterHeading(document, last, hwt);
                last = hwt;
            }
        }
        return document;
    }


}
