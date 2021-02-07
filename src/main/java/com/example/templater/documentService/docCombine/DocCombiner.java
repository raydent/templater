package com.example.templater.documentService.docCombine;

import com.example.templater.documentService.tempBuilder.TempParams;
import com.example.templater.documentService.tempBuilder.TemplateCreater;
import com.example.templater.documentService.tempParamsGetter.AllTempParams;
import com.example.templater.documentService.tempParamsGetter.HeadingContent;
import com.example.templater.documentService.tempParamsGetter.HeadingWithText;
import com.example.templater.documentService.tempParamsGetter.TempParamsGetter;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.security.core.parameters.P;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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


    public XWPFDocument combineDocs(List<XWPFDocument> documentList) throws CustomException, IOException, XmlException {
        if (documentList == null) {
            return null;
        }

        XWPFDocument document = documentList.get(0);
        if (documentList.size() == 1) {
            return document;
        }
        document = new XWPFDocument();
        XWPFStyles styles = document.createStyles();
        styles.setStyles(documentList.get(0).getStyle());
        TemplateCreater creater = new TemplateCreater();
        AllTempParams allTempParams = TempParamsGetter.getTempParams(documentList.get(0));
        if (allTempParams.getTempParams().isTitle_page()) {
            document = creater.createTitlePage(document, allTempParams.getTitleParams(), allTempParams.getTempParams().getField());
        }
        if (allTempParams.getTempParams().isHeader()) {
            document = creater.createHeader(document, null);
        }
        if (allTempParams.getTempParams().isNumeration()) {
            document = creater.createNumeration(document);
        }
        if (allTempParams.getTempParams().isFooter()) {
            document = creater.createFooter(document, null);
        }

        List<HeadingWithText> levelListMain = TempParamsGetter.getHeadingsList(documentList.get(0));
        checkSameHeadingsException(documentList.get(0));
        for (int i = 1; i < documentList.size(); ++i) {
            XWPFDocument doc = documentList.get(i);
            checkSameHeadingsException(doc);
            List<HeadingWithText> levelListToMerge = TempParamsGetter.getHeadingsList(doc);
            levelListMain = combineMainHeadings(levelListMain, levelListToMerge);
        }

        document = insertHeadings(document, levelListMain);
        return document;
    }

    public List<HeadingWithText> combineMainHeadings(List<HeadingWithText> l, List<HeadingWithText> r) {
        List<HeadingWithText> result = new ArrayList<>();
        List<HeadingWithText> resolved = new ArrayList<>();
        List<XWPFParagraph> PL = new ArrayList<>();
        List<XWPFParagraph> PR = new ArrayList<>();

        for (HeadingWithText left : l) {
            PL.add(left.getHeading());
        }
        for (HeadingWithText right : r) {
            PR.add(right.getHeading());
        }

        for (HeadingWithText hwtL : l) {
            if (hwtL.getHeading().getStyle().equals("Heading1")) {
                boolean isMatched = false;
                List<XWPFParagraph> text = new ArrayList<>();
                List<XWPFTable> tables = new ArrayList<>();
                List<HeadingWithText> content = new ArrayList<>();
                if (hwtL.getText() != null && !hwtL.getText().isEmpty()) {
                    text.addAll(hwtL.getText());
                }
                if (hwtL.getTables() != null && !hwtL.getTables().isEmpty()) {
                    tables.addAll(hwtL.getTables());
                }
                for (HeadingWithText hwtR : r) {
                    if (hwtR.getHeading().getStyle().equals("Heading1")) {
                        HeadingsMatcher matcher = new HeadingsMatcher();
                        if (matcher.headingsMatch(PL, PR, hwtL.getHeading(), hwtR.getHeading()) >= 100
                                && !resolved.contains(hwtR)) {
                            if (hwtR.getText() != null && !hwtR.getText().isEmpty()) {
                                text.addAll(hwtR.getText());
                            }
                            if (hwtR.getTables() != null && !hwtR.getTables().isEmpty()) {
                                tables.addAll(hwtR.getTables());
                            }
                            resolved.add(hwtR);
                            isMatched = true;
                            content = combineSubHeadings(TempParamsGetter.getHeadingContent(l,
                                    hwtL.getHeading()).getSubHList(), TempParamsGetter.getHeadingContent(r,
                                    hwtR.getHeading()).getSubHList(), 1);
                            break;
                        }
                    }
                }
                HeadingWithText hwt = new HeadingWithText(hwtL.getHeading(), text, tables);
                result.add(hwt);
                if (content != null && !content.isEmpty()) {
                    result.addAll(content);
                }
                if (!isMatched) {
                    List<HeadingWithText> c = TempParamsGetter.getHeadingContent(l, hwtL.getHeading()).getSubHList();
                    if (c != null && !c.isEmpty()) {
                        result.addAll(c);
                    }
                }
            }
        }

        for (HeadingWithText hwt : r) {
            if (hwt.getHeading().getStyle().equals("Heading1")) {
                if (!resolved.contains(hwt)) {
                    List<HeadingWithText> list = new ArrayList<>();
                    list.add(hwt);
                    list.addAll(TempParamsGetter.getHeadingContent(r, hwt.getHeading()).getSubHList());
                    result.addAll(list);
                }
            }
        }

        return result;
    }

    public List<HeadingWithText> combineSubHeadings(List<HeadingWithText> l, List<HeadingWithText> r,
                                                    int level) {
        if (level == 6 || l == null || l.isEmpty()) {
            return null;
        }
        List<HeadingWithText> result = new ArrayList<>();
        List<HeadingWithText> resolved = new ArrayList<>();
        List<XWPFParagraph> PL = new ArrayList<>();
        List<XWPFParagraph> PR = new ArrayList<>();
        for (HeadingWithText left : l) {
            PL.add(left.getHeading());
        }
        for (HeadingWithText right : r) {
            PR.add(right.getHeading());
        }

        for (HeadingWithText hwtL : l) {
            if (TempParamsGetter.getHeadingNumLevel(hwtL.getHeading()) == level) {
                List<XWPFParagraph> text = new ArrayList<>();
                List<XWPFTable> tables = new ArrayList<>();
                List<HeadingWithText> content = new ArrayList<>();
                if (hwtL.getText() != null && !hwtL.getText().isEmpty()) {
                    text.addAll(hwtL.getText());
                }
                if (hwtL.getTables() != null && !hwtL.getTables().isEmpty()) {
                    tables.addAll(hwtL.getTables());
                }
                for (HeadingWithText hwtR : r) {
                    if (TempParamsGetter.getHeadingNumLevel(hwtR.getHeading()) == level) {
                        HeadingsMatcher matcher = new HeadingsMatcher();
                        if (matcher.headingsNameMatch(hwtL.getHeading(), hwtR.getHeading()) == 100
                                && !resolved.contains(hwtR)) {
                            if (hwtR.getText() != null && !hwtR.getText().isEmpty()) {
                                text.addAll(hwtR.getText());
                            }
                            if (hwtR.getTables() != null && !hwtR.getTables().isEmpty()) {
                                tables.addAll(hwtR.getTables());
                            }
                            resolved.add(hwtR);
                            if (level != 5) {
                                content = combineSubHeadings(TempParamsGetter.getHeadingContent(l,
                                        hwtL.getHeading()).getSubHList(), TempParamsGetter.getHeadingContent(r,
                                        hwtR.getHeading()).getSubHList(), level + 1);
                            }
                            break;
                        }
                    }
                }
                HeadingWithText hwt = new HeadingWithText(hwtL.getHeading(), text, tables);
                result.add(hwt);
                if (content != null && !content.isEmpty()) {
                    result.addAll(content);
                }
            }
        }

        for (HeadingWithText hwt : r) {
            if (TempParamsGetter.getHeadingNumLevel(hwt.getHeading()) == level) {
                if (!resolved.contains(hwt)) {
                    List<HeadingWithText> list = new ArrayList<>();
                    list.add(hwt);
                    List<HeadingWithText> content = TempParamsGetter.getHeadingContent(r, hwt.getHeading()).getSubHList();
                    if (content != null && !content.isEmpty()) {
                        list.addAll(content);
                    }
                    result.addAll(list);
                }
            }
        }

        return result;
    }

    public XWPFDocument insertHeadings(XWPFDocument document, List<HeadingWithText> list) {
        for (HeadingWithText hwt : list) {
            insertHeading(document, hwt);
        }
        return document;
    }

    private void checkSameHeadingsException(XWPFDocument document) throws CustomException {
        List<HeadingWithText> headings = TempParamsGetter.getMainHeadingsList(document);
        List<String> headingsNames = new ArrayList<>();
        for (HeadingWithText hwt : headings) {
            headingsNames.add(hwt.getHeading().getText());
        }
        for (String name : headingsNames) {
            if (headingsNames.stream().filter(name::equals).count() > 1) {
                throw Exceptions.TwoSameHeadersInFile.getException();
            }
        }
    }

    public XWPFDocument combineMainHeadings(XWPFDocument docS, XWPFDocument docTBA, HeadingWithText l,
                                            HeadingWithText r) {
        XWPFDocument document = docS;
        document = insertTextAfterHeading(document, l, r.getText(), r.getTables());
        document = combineHeadingsContent(docS, docTBA, l.getHeading(), r.getHeading());
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

    public XWPFDocument combineHeadingsContent(XWPFDocument docS, XWPFDocument docTBA,
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
        paragraph.setAlignment(ParagraphAlignment.LEFT);
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
            document.createParagraph();
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
        /*cursor.toEndToken();
        while(cursor.toNextToken() != XmlCursor.TokenType.START);
        XWPFTable newTable = document.insertNewTbl(cursor);*/
        XWPFTable newTable = document.createTable();
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
