package com.example.templater.documentService.docCombine;

import com.example.templater.documentService.tempBuilder.TemplateCreater;
import com.example.templater.documentService.tempParamsGetter.AllTempParams;
import com.example.templater.documentService.tempParamsGetter.HeadingWithText;
import com.example.templater.documentService.tempParamsGetter.TempParamsGetter;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocCombiner {

    private List<MainHeadingInfo> mainHeadingsInfo;
    private List<String> removedMainHeadings;

    public List<MainHeadingInfo> getMainHeadingsInfo() {
        return mainHeadingsInfo;
    }

    public void setMainHeadingsInfo(List<MainHeadingInfo> mainHeadingsInfo) {
        this.mainHeadingsInfo = mainHeadingsInfo;
    }

    public List<String> getRemovedMainHeadings() {
        return removedMainHeadings;
    }

    public void setRemovedMainHeadings(List<String> removedMainHeadings) {
        this.removedMainHeadings = removedMainHeadings;
    }

    public XWPFDocument combineDocs(List<File> documents, List<HeadingsCorrection> correctionList,
                                    boolean removeSamePar) throws CustomException, IOException, XmlException {
        if (documents == null || documents.isEmpty()) {
            return null;
        }
        List<XWPFDocument> documentList = new ArrayList<>();
        for (File file : documents) {
            FileInputStream is = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(is);
            documentList.add(document);
        }

        XWPFDocument document = documentList.get(0);
        if (documentList.size() == 1) {
            return document;
        }
        // подготовка нового документа
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
        // проверка на пустой файл
        List<HeadingWithText> levelListMain = TempParamsGetter.getHeadingsList(documentList.get(0));
        if (levelListMain == null) {
            throw Exceptions.FileIsEmptyOrNoMainHeaders.getException();
        }
        checkSameHeadingsException(documentList.get(0));
        // заполнение mainHeadingsInfo хедерами из первого файла
        List<String> mainHeadings = TempParamsGetter.getMainHeadingsNamesList(documentList.get(0));
        if (mainHeadings == null || mainHeadings.isEmpty()) {
            throw Exceptions.FileIsEmptyOrNoMainHeaders.getException();
        }
        if (mainHeadingsInfo == null) {
            mainHeadingsInfo = new ArrayList<>();
        }
        for (String name : mainHeadings) {
            MainHeadingInfo info = new MainHeadingInfo();
            info.setHeadingName(name);
            info.setFinalName(name);
            info.setFileName(documents.get(0).getName());
            List<XWPFParagraph> headings = documentList.get(0).getParagraphs();
            XWPFParagraph paragraph = null;
            for (XWPFParagraph p : headings) {
                if (p.getText().equals(name)) {
                    paragraph = p;
                }
            }
            List<XWPFParagraph> subheadings = TempParamsGetter.getSubHeadings(headings, paragraph);
            List<String> subH = new ArrayList<>();
            for (XWPFParagraph p : subheadings) {
                subH.add(p.getText());
            }
            info.setSubheadingsNames(subH);
            mainHeadingsInfo.add(info);
        }
        // мердж с осталными файлами
        for (int i = 1; i < documentList.size(); ++i) {
            XWPFDocument doc = documentList.get(i);
            checkSameHeadingsException(doc);
            List<HeadingWithText> levelListToMerge = TempParamsGetter.getHeadingsList(doc);
            if (levelListToMerge == null) {
                throw Exceptions.FileIsEmptyOrNoMainHeaders.getException();
            }
            // заполнение mainHeadingsInfo хедерами из остальных файлов
            List<String> mainHeadings1 = TempParamsGetter.getMainHeadingsNamesList(documentList.get(i));
            if (mainHeadings1 == null || mainHeadings1.isEmpty()) {
                throw Exceptions.FileIsEmptyOrNoMainHeaders.getException();
            }
            for (String name : mainHeadings1) {
                MainHeadingInfo info = new MainHeadingInfo();
                info.setHeadingName(name);
                info.setFileName(documents.get(i).getName());
                List<XWPFParagraph> headings = documentList.get(i).getParagraphs();
                XWPFParagraph paragraph = null;
                for (XWPFParagraph p : headings) {
                    if (p.getText().equals(name)) {
                        paragraph = p;
                    }
                }
                List<XWPFParagraph> subheadings = TempParamsGetter.getSubHeadings(headings, paragraph);
                List<String> subH = new ArrayList<>();
                for (XWPFParagraph p : subheadings) {
                    subH.add(p.getText());
                }
                info.setSubheadingsNames(subH);
                mainHeadingsInfo.add(info);
            }

            levelListMain = combineMainHeadings(levelListMain, levelListToMerge, documents.get(i).getName(),
                    correctionList, removeSamePar);
           /* for (HeadingWithText h : levelListMain) {
                System.out.println(h.getHeading().getText());
            }
            System.out.println();*/
        }
        document = insertHeadings(document, levelListMain);

        // заполнение mainHeadingsInfo
        List<String> headings = TempParamsGetter.getMainHeadingsNamesList(document);
        if (headings != null && !headings.isEmpty()) {
            for (String str : headings) {
                List<MatchedHeadingInfo> matchedHeadings = new ArrayList<>();
                for (MainHeadingInfo info : mainHeadingsInfo) {
                    if (info.getHeadingName().equals(str) && info.getFileName().equals(documents.get(0).getName())) {
                        MatchedHeadingInfo info1 = new MatchedHeadingInfo();
                        info1.setHeadingName(info.getHeadingName());
                        info1.setFileName(info.getFileName());
                        matchedHeadings.add(info1);
                        if (info.getMatched() != null && !info.getMatched().isEmpty()) {
                            matchedHeadings.addAll(info.getMatched());
                        }
                        break;
                    }
                }
                for (int i = 1; i < matchedHeadings.size(); ++i) {
                    for (MainHeadingInfo info : mainHeadingsInfo) {
                        if (info.getHeadingName().equals(matchedHeadings.get(i).getHeadingName())
                                && info.getFileName().equals(matchedHeadings.get(i).getFileName())) {
                            List<MatchedHeadingInfo> tba = new ArrayList<>(matchedHeadings);
                            tba.remove(matchedHeadings.get(i));
                            info.setFinalName(matchedHeadings.get(0).getHeadingName());
                            info.setMatched(tba);
                            if (!tba.isEmpty()) {
                                info.setMatched(true);
                            }
                        }
                    }
                }
            }
        }

        return document;
    }

    public List<HeadingWithText> combineMainHeadings(List<HeadingWithText> l, List<HeadingWithText> r, String fileName,
                                                     List<HeadingsCorrection> correctionList, boolean removeSamePar) {
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
                List<String> headingsToMerge = new ArrayList<>();
                if (correctionList != null) {
                    // доделать коррекцию
                }
                List<XWPFParagraph> text = new ArrayList<>();
                List<XWPFTable> tables = new ArrayList<>();
                List<HeadingWithText> content = new ArrayList<>();
                if (hwtL.getText() != null && !hwtL.getText().isEmpty()) {
                    text.addAll(hwtL.getText());
                }
                if (hwtL.getTables() != null && !hwtL.getTables().isEmpty()) {
                    tables.addAll(hwtL.getTables());
                }
                List<HeadingWithText> con = TempParamsGetter.getHeadingContent(l, hwtL.getHeading()).getSubHList();
                if (con != null && !con.isEmpty()) {
                    content.addAll(con);
                }
                for (HeadingWithText hwtR : r) {
                    if (hwtR.getHeading().getStyle().equals("Heading1")) {
                        Matcher matcher = new Matcher();
                        if (matcher.headingsMatch(PL, PR, hwtL.getHeading(), hwtR.getHeading()) >= 100
                                && !resolved.contains(hwtR)) {
                            if (hwtR.getText() != null && !hwtR.getText().isEmpty()) {
                                // проверка на наличие одинаковых абзацев
                                if (removeSamePar && !text.isEmpty()) {
                                    List<XWPFParagraph> pToAdd = new ArrayList<>();
                                    List<XWPFParagraph> matched = new ArrayList<>();
                                    for (XWPFParagraph pL : text) {
                                        for (XWPFParagraph pR : hwtR.getText()) {
                                            int score = matcher.paragraphsMatch(pL.getText(), pR.getText());
                                            if (score < 80 && !pToAdd.contains(pR) && !matched.contains(pR)) {
                                                pToAdd.add(pR);
                                            }
                                            if (score >= 80) {
                                                if (text.get(text.indexOf(pL)).getText().length() < pR.getText().length()) {
                                                    text.set(text.indexOf(pL), pR);
                                                }
                                                matched.add(pR);
                                            }
                                        }
                                    }
                                    text.addAll(pToAdd);
                                }
                                else {
                                    text.addAll(hwtR.getText());
                                }
                            }
                            if (hwtR.getTables() != null && !hwtR.getTables().isEmpty()) {
                                tables.addAll(hwtR.getTables());
                            }
                            resolved.add(hwtR);
                            content = combineSubHeadings(con, TempParamsGetter.getHeadingContent(r,
                                    hwtR.getHeading()).getSubHList(), removeSamePar, 1);
                            // дополнение инофрмации о хедере из source
                            MatchedHeadingInfo matchedInfo = new MatchedHeadingInfo();
                            matchedInfo.setFileName(fileName);
                            matchedInfo.setHeadingName(hwtR.getHeading().getText());
                            for (MainHeadingInfo info : mainHeadingsInfo) {
                                if (info.getHeadingName().equals(hwtL.getHeading().getText())) {
                                    List<MatchedHeadingInfo> infos = info.getMatched();
                                    if (infos == null) {
                                        infos = new ArrayList<>();
                                    }
                                    infos.add(matchedInfo);
                                    info.setMatched(infos);
                                    info.setMatched(true);
                                    break;
                                }
                            }
                            //
                            break;
                        }
                    }
                }
                if (!text.isEmpty() || !tables.isEmpty() || (content != null && !content.isEmpty())) {
                    HeadingWithText hwt = new HeadingWithText(hwtL.getHeading(), text, tables);
                    result.add(hwt);
                    if (content != null && !content.isEmpty()) {
                        result.addAll(content);
                    }
                }
            }
        }

        for (HeadingWithText hwt : r) {
            if (hwt.getHeading().getStyle().equals("Heading1")) {
                if (!resolved.contains(hwt)) {
                    List<HeadingWithText> list = new ArrayList<>();
                    List<HeadingWithText> content = TempParamsGetter.getHeadingContent(r, hwt.getHeading()).getSubHList();
                    if ((hwt.getText() != null && !hwt.getText().isEmpty())
                            || (hwt.getTables() != null && !hwt.getTables().isEmpty())
                            || (content != null && !content.isEmpty())) {
                        list.add(hwt);
                        list.addAll(content);
                        result.addAll(list);
                    }
                }
                HeadingWithText last = result.get(result.size() - 1);
                while ((last.getText() == null || last.getText().isEmpty()) && (last.getTables() == null
                        || last.getTables().isEmpty())) {
                    result.remove(last);
                    last = result.get(result.size() - 1);
                }
            }
        }

        return result;
    }

    public List<HeadingWithText> combineSubHeadings(List<HeadingWithText> l, List<HeadingWithText> r, boolean removeSamePar,
                                                    int level) {
        if (level == 5 || ((l == null || l.isEmpty()) && (r == null || r.isEmpty()))) {
            return null;
        }
        else if ((l == null || l.isEmpty()) && (r != null && !r.isEmpty())) {
            HeadingWithText h = r.get(0);
            if ((h.getText()!= null && !h.getText().isEmpty()) || (h.getTables() != null
                    && !h.getTables().isEmpty()) || r.size() > 1) {
                return r;
            }
            return null;
        }
        else if ((l != null && !l.isEmpty()) && (r == null || r.isEmpty())) {
            HeadingWithText h = l.get(0);
            if ((h.getText()!= null && !h.getText().isEmpty()) || (h.getTables() != null
                    && !h.getTables().isEmpty()) || l.size() > 1) {
                return l;
            }
           return null;
        }
        List<HeadingWithText> result = new ArrayList<>();
        List<HeadingWithText> resolved = new ArrayList<>();

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
                List<HeadingWithText> con = TempParamsGetter.getHeadingContent(l, hwtL.getHeading()).getSubHList();
                if (con != null && !con.isEmpty()) {
                    content.addAll(con);
                }
                for (HeadingWithText hwtR : r) {
                    if (TempParamsGetter.getHeadingNumLevel(hwtR.getHeading()) == level) {
                        Matcher matcher = new Matcher();
                        if (matcher.headingsNameMatch(hwtL.getHeading(), hwtR.getHeading()) == 100
                                && !resolved.contains(hwtR)) {
                            if (hwtR.getText() != null && !hwtR.getText().isEmpty()) {
                                // проверка на наличие одинаковых абзацев
                                if (removeSamePar && !text.isEmpty()) {
                                    List<XWPFParagraph> pToAdd = new ArrayList<>();
                                    List<XWPFParagraph> matched = new ArrayList<>();
                                    for (XWPFParagraph pL : text) {
                                        for (XWPFParagraph pR : hwtR.getText()) {
                                            int score = matcher.paragraphsMatch(pL.getText(), pR.getText());
                                            if (score >= 80) {
                                                if (text.get(text.indexOf(pL)).getText().length() < pR.getText().length()) {
                                                    text.set(text.indexOf(pL), pR);
                                                }
                                                matched.add(pR);
                                            }
                                        }
                                    }
                                    for (XWPFParagraph pR : hwtR.getText()) {
                                        if (!matched.contains(pR)) {
                                            pToAdd.add(pR);
                                        }
                                    }
                                    text.addAll(pToAdd);
                                }
                                else {
                                    text.addAll(hwtR.getText());
                                }
                            }
                            if (hwtR.getTables() != null && !hwtR.getTables().isEmpty()) {
                                tables.addAll(hwtR.getTables());
                            }
                            resolved.add(hwtR);
                            if (level != 4) {
                                content = combineSubHeadings(con, TempParamsGetter.getHeadingContent(r,
                                        hwtR.getHeading()).getSubHList(), removeSamePar, level + 1);
                            }
                            break;
                        }
                    }
                }
                if (!text.isEmpty() || !tables.isEmpty() || (content != null && !content.isEmpty())) {
                    HeadingWithText hwt = new HeadingWithText(hwtL.getHeading(), text, tables);
                    result.add(hwt);
                    if (content != null && !content.isEmpty()) {
                        result.addAll(content);
                    }
                }
            }
        }

        for (HeadingWithText hwt : r) {
            if (TempParamsGetter.getHeadingNumLevel(hwt.getHeading()) == level) {
                if (!resolved.contains(hwt)) {
                    List<HeadingWithText> list = new ArrayList<>();
                    List<HeadingWithText> content = TempParamsGetter.getHeadingContent(r, hwt.getHeading()).getSubHList();
                    if ((hwt.getText() != null && !hwt.getText().isEmpty())
                            || (hwt.getTables() != null && !hwt.getTables().isEmpty())
                            || (content != null && !content.isEmpty())) {
                        result.add(hwt);
                        if (content != null && !content.isEmpty()) {
                            list.addAll(content);
                        }
                        result.addAll(list);
                    }

                }
            }
        }

        return result;
    }

    public XWPFDocument insertHeadings(XWPFDocument document, List<HeadingWithText> list) {
        for (HeadingWithText hwt : list) {
            document = insertHeading(document, hwt);
        }
        return document;
    }

    private void checkSameHeadingsException(XWPFDocument document) throws CustomException {
        List<HeadingWithText> headings = TempParamsGetter.getMainHeadingsList(document);
        if (headings == null || headings.isEmpty()) {
            throw Exceptions.FileIsEmptyOrNoMainHeaders.getException();
        }
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
        if (hwt.getTables() != null && !hwt.getTables().isEmpty()) {
            for (XWPFTable t : hwt.getTables()) {
                document = insertTable(document, t);
                document.createParagraph();
            }
        }
        return document;
    }

    public XWPFDocument insertTable(XWPFDocument document, XWPFTable t) {
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
}
