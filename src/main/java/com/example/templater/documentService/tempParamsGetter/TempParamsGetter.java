package com.example.templater.documentService.tempParamsGetter;

import com.example.templater.documentService.tempBuilder.*;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempParamsGetter {

    public static AllTempParams getTempParams(XWPFDocument document) {
        AllTempParams all = new AllTempParams();
        TempParams tempParams = new TempParams();
        TitleParams titleParams = new TitleParams();
        List<ParagraphParams> paramsList = new ArrayList<>();
        TableParams tableParams = new TableParams();

        //getting paragraph params
        XWPFStyles styles = document.getStyles();
        XWPFStyle heading1 = styles.getStyle("Heading1");
        XWPFStyle heading2 = styles.getStyle("Heading2");
        XWPFStyle heading3 = styles.getStyle("Heading3");
        XWPFStyle heading4 = styles.getStyle("Heading4");
        XWPFStyle heading5 = styles.getStyle("Heading5");
        XWPFStyle header = styles.getStyle("Header");
        XWPFStyle footer = styles.getStyle("Footer");
        List<XWPFStyle> styleList = Arrays.asList(heading1, heading2, heading3, heading4, heading5);
        for (XWPFStyle style : styleList) {
            if (style == null) {
                paramsList.add(null);
                continue;
            }
            CTStyle ctStyle = style.getCTStyle();
            CTRPr ctrPr = ctStyle.getRPr();
            CTPPr ctpPr = ctStyle.getPPr();
            ParagraphParams params = new ParagraphParams();
            params.setFont(Fonts.getFontEnum(ctrPr.getRFonts().getAscii()));
            if (ctrPr.getSz() != null) {
                params.setFontSize(ctrPr.getSz().getVal().intValue() / 2);
            }
            else {
                params.setFontSize(11);
            }
            params.setTextColor(ctrPr.getColor().xgetVal().getStringValue());
            if (ctrPr.getB() != null && ctrPr.getB().getVal() != null) {
                params.setBold(ctrPr.getB().getVal().equals(STOnOff.ON));
            }
            if (ctrPr.getI() != null && ctrPr.getI().getVal() != null) {
                params.setItalic(ctrPr.getI().getVal().equals(STOnOff.ON));
            }
            if (ctrPr.getU() != null && ctrPr.getU().getVal() != null) {
                params.setUnderline(ctrPr.getU().getVal().equals(STUnderline.SINGLE));
            }
            if (ctpPr != null && ctrPr.getHighlight() != null) {
                params.setTextHighlightColor(Colors.getColorName(ctrPr.getHighlight().getVal()));
            }
            else {
                params.setTextHighlightColor("none");
            }
            params.setAlignment(ParagraphAlignment.LEFT);
            paramsList.add(params);
        }

        //getting template params
        tempParams.setTitle_page(isTitlePage(document));

        tempParams.setHeader(!document.getHeaderList().isEmpty());
        List<XWPFFooter> fList = document.getFooterList();
        if (fList.isEmpty()) {
            tempParams.setFooter(false);
            tempParams.setNumeration(false);
        }
        else if (fList.get(fList.size() - 1).getListParagraph().size() == 3){
            tempParams.setFooter(true);
            tempParams.setNumeration(true);
        }
        else {
            XWPFFooter f = fList.get(fList.size() - 1);
            if (f.getParagraphs().size() == 1) {
                tempParams.setNumeration(true);
                tempParams.setFooter(false);
            }
            else {
                tempParams.setNumeration(false);
                tempParams.setFooter(true);
            }
        }

        for (XWPFStyle style : styleList) {
            if (style != null) {
                BigInteger spacing = style.getCTStyle().getPPr().getSpacing().getLine();
                if (spacing != null) {
                    tempParams.setInterval_between_lines(spacing.doubleValue() / 240);
                }
                else {
                    tempParams.setInterval_between_lines(1);
                }
            }
        }
        CTSectPr sectPr = document.getDocument().getBody().getSectPr();
        if (sectPr == null) {
            tempParams.setField(Fields.average);
        }
        else {
            CTPageMar pageMar = sectPr.getPgMar();
            BigInteger left = pageMar.getLeft();
            BigInteger right = pageMar.getRight();
            BigInteger top = pageMar.getTop();
            BigInteger bottom = pageMar.getBottom();
            if (left.equals(BigInteger.valueOf(2880L)) && right.equals(BigInteger.valueOf(2880L)) &&
                    top.equals(BigInteger.valueOf(1440L)) && bottom.equals(BigInteger.valueOf(1440L))) {
                tempParams.setField(Fields.wide);
            }
            else if(left.equals(BigInteger.valueOf(720L)) && right.equals(BigInteger.valueOf(720L)) &&
                    top.equals(BigInteger.valueOf(720L)) && bottom.equals(BigInteger.valueOf(720L))) {
                tempParams.setField(Fields.narrow);
            }
            else if (left.equals(BigInteger.valueOf(1699L)) && right.equals(BigInteger.valueOf(850L)) &&
                    top.equals(BigInteger.valueOf(1138L)) && bottom.equals(BigInteger.valueOf(1138L))) {
                tempParams.setField(Fields.average);
            }
            else {
                tempParams.setField(Fields.average);
            }
        }

        //getting title params
        List<XWPFTable> tList = document.getTables();
        if (!tempParams.isTitle_page()) {
            titleParams = null;
        }
        else {
            if (tList.get(0).getNumberOfRows() == 1) {
                titleParams.setType(1);
                titleParams.setNameField(null);
                titleParams.setDateField(null);
                ParagraphParams dateC = new ParagraphParams();
                XWPFParagraph p = tList.get(0).getRow(0).getCell(0).getParagraphs().get(0);
                XWPFRun run = p.getRuns().get(0);
                dateC.setAlignment(p.getAlignment());
                dateC.setFont(Fonts.getFontEnum(run.getFontFamily()));
                dateC.setFontSize(run.getFontSize());
                dateC.setTextColor(run.getColor());
                dateC.setTextHighlightColor(Colors.getColorName(run.getTextHightlightColor().toString()));
                dateC.setBold(run.isBold());
                dateC.setUnderline(run.getUnderline().equals(UnderlinePatterns.SINGLE));
                dateC.setItalic(run.isItalic());
                titleParams.setDateColomn(dateC);
            }
            else {
                titleParams.setType(2);
                titleParams.setDateColomn(null);
            }
            int i = 0;
            if (titleParams.getType() == 1) {
                i = 1;
            }
            XWPFTable titleT = tList.get(i);
            XWPFParagraph first = titleT.getRow(0).getCell(0).getParagraphArray(0);
            XWPFParagraph second = titleT.getRow(1).getCell(0).getParagraphArray(0);
            XWPFParagraph third = titleT.getRow(2).getCell(0).getParagraphArray(0);
            List<XWPFParagraph> pList = Arrays.asList(first, second, third);
            List<ParagraphParams> parList = new ArrayList<>();
            XWPFRun run;
            for (XWPFParagraph p : pList) {
                if (p.getStyle() != null && p.getStyle().equals("NoSpacing")) {
                    ParagraphParams params = new ParagraphParams();
                    run = p.getRuns().get(0);
                    params.setAlignment(p.getAlignment());
                    params.setFont(Fonts.getFontEnum(run.getFontFamily()));
                    params.setFontSize(run.getFontSize());
                    params.setTextColor(run.getColor());
                    params.setTextHighlightColor(Colors.getColorName(run.getTextHightlightColor().toString()));
                    params.setBold(run.isBold());
                    params.setItalic(run.isItalic());
                    params.setUnderline(run.getUnderline().equals(UnderlinePatterns.SINGLE));
                    parList.add(params);
                }
            }
            titleParams.setFirstLine(parList.get(0));
            titleParams.setSecondLine(parList.get(1));
            titleParams.setThirdLine(parList.get(2));

            if (titleParams.getType() != 1) {
                XWPFParagraph name = tList.get(1).getRow(0).getCell(0).getParagraphArray(0);
                XWPFParagraph date = tList.get(1).getRow(0).getCell(0).getParagraphArray(0);
                XWPFRun runN = name.getRuns().get(0);
                XWPFRun runD = date.getRuns().get(0);
                ParagraphParams paramsN = new ParagraphParams();
                ParagraphParams paramsD = new ParagraphParams();
                paramsD.setAlignment(date.getAlignment());
                paramsN.setAlignment(name.getAlignment());
                paramsD.setFont(Fonts.getFontEnum(runD.getFontFamily()));
                paramsN.setFont(Fonts.getFontEnum(runN.getFontFamily()));
                paramsD.setFontSize(runD.getFontSize());
                paramsN.setFontSize(runN.getFontSize());
                paramsD.setTextColor(runD.getColor());
                paramsN.setTextColor(runN.getColor());
                paramsD.setTextHighlightColor(Colors.getColorName(runD.getTextHightlightColor().toString()));
                paramsN.setTextHighlightColor(Colors.getColorName(runN.getTextHightlightColor().toString()));
                paramsD.setBold(runD.isBold());
                paramsN.setBold(runN.isBold());
                paramsD.setItalic(runD.isItalic());
                paramsN.setItalic(runN.isItalic());
                paramsD.setUnderline(runD.getUnderline().equals(UnderlinePatterns.SINGLE));
                paramsN.setUnderline(runN.getUnderline().equals(UnderlinePatterns.SINGLE));
                titleParams.setNameField(paramsN);
                titleParams.setDateField(paramsD);
            }
        }

        //getting table params
        List<XWPFTable> tables = document.getTables();
        if (tables.isEmpty()) {
            tableParams = null;
        }
        else {
            int table_pos = 0;
            if (tempParams.isTitle_page()) {
                table_pos = 2;
            }
            if (tables.size() <= table_pos) {
                tableParams = null;
            }
            else {
                XWPFTable table = tables.get(table_pos);
                tableParams.setRows(table.getNumberOfRows());
                tableParams.setColoms(table.getRow(0).getTableCells().size());
                List<BigInteger> widthList = new ArrayList<>();
                List<XWPFTableCell> cells = table.getRow(0).getTableCells();
                for (XWPFTableCell cell : cells) {
                    widthList.add(cell.getCTTc().getTcPr().getTcW().getW());
                }
                tableParams.setWidth(widthList);
                XWPFRun run = table.getRow(0).getCell(0).getParagraphs().get(0).getRuns().get(0);
                tableParams.setHeadingCellTextColor(run.getColor());
                tableParams.setHeadingTextFont(Fonts.getFontEnum(run.getFontFamily()));
                tableParams.setHeadingTextBold(run.isBold());
                tableParams.setHeadingTextItalic(run.isItalic());
                tableParams.setHeadingTextFontSize(run.getFontSize());
                tableParams.setHeadingCellColor(cells.get(0).getColor());
                STHexColor color;
                if (table.getCTTbl().getTblPr().isSetTblBorders()) {
                    color = table.getCTTbl().getTblPr().getTblBorders().getTop().xgetColor();
                    tableParams.setBorderColor(color.getStringValue());
                }
                else {
                    color = STHexColor.Factory.newInstance();
                    color.setStringValue("000000");
                    tableParams.setBorderColor(color.getStringValue());
                }
                if (table.getNumberOfRows() > 1) {
                    tableParams.setCommonCellColor(table.getRow(1).getCell(0).getColor());
                }
            }
        }
        all.setTempParams(tempParams);
        all.setTitleParams(titleParams);
        all.setParamsList(paramsList);
        all.setTableParams(tableParams);
        return all;
    }
    public static AllTempParams getTempParams(File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(is);
        return getTempParams(document);
    }

    public static boolean isTitlePage(XWPFDocument document) {
        boolean isTitlePage = false;
        List<XWPFTable> tList = document.getTables();
        if (!tList.isEmpty()) {
            String style = tList.get(0).getRow(0).getCell(0).getParagraphs().get(0).getStyle();
            if (style != null && style.equals("NoSpacing")) {
                isTitlePage = true;
            }
        }
        return isTitlePage;
    }

    public static List<HeadingWithText> getHeadingsList(XWPFDocument document) {
        List<HeadingWithText> headingList = new ArrayList<>();
        List<XWPFParagraph> pList = document.getParagraphs();
        if (pList == null || pList.isEmpty()) {
            return null;
        }
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < pList.size(); ++i) {
            String style = pList.get(i).getStyle();
            if (style != null && (style.equals("Heading1") || style.equals("Heading2") || style.equals("Heading3")
                    || style.equals("Heading4") || style.equals("Heading5"))) {
                HeadingWithText hwt = new HeadingWithText();
                hwt.setHeading(pList.get(i));
                headingList.add(hwt);
                indexes.add(i);
                }
            }
        int headingNumber = 0;
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < indexes.size(); ++i) {
            int currentH = indexes.get(i);
            if (currentH != indexes.get(indexes.size() - 1)) {
                List<XWPFParagraph> text = new ArrayList<>();
                int nextH = indexes.get(i + 1);
                for (int j = currentH + 1; j < nextH; ++j) {
                    text.add(pList.get(j));
                }
                headingList.get(headingNumber).setText(text);
                if (tables != null && !tables.isEmpty()) {
                    List <XWPFTable> foundTables = new ArrayList<>();
                    int currentHPos = document.getPosOfParagraph(pList.get(currentH));
                    int nexHPos = document.getPosOfParagraph(pList.get(nextH));
                    for (XWPFTable t : tables) {
                        int tablePos = document.getPosOfTable(t);
                        if (tablePos > currentHPos && tablePos < nexHPos) {
                            foundTables.add(t);
                        }
                    }
                    headingList.get(headingNumber).setTables(foundTables);
                }
                ++headingNumber;
            }
            else {
                List<XWPFParagraph> text = new ArrayList<>();
                for (int j = currentH + 1; j < pList.size(); ++j) {
                    text.add(pList.get(j));
                }
                headingList.get(headingNumber).setText(text);
                if (tables != null && !tables.isEmpty()) {
                    List <XWPFTable> foundTables = new ArrayList<>();
                    int currentHPos = document.getPosOfParagraph(pList.get(currentH));
                    for (XWPFTable t : tables) {
                        int tablePos = document.getPosOfTable(t);
                        if (tablePos > currentHPos) {
                            foundTables.add(t);
                        }
                    }
                    headingList.get(headingNumber).setTables(foundTables);
                }
            }
        }
        return headingList;
    }

    public static List<HeadingWithText> getMainHeadingsList(XWPFDocument document) {
        List<HeadingWithText> result = new ArrayList<>();
        List<HeadingWithText> hList = getHeadingsList(document);
        if (hList == null) {
            return null;
        }
        for (HeadingWithText hwt : hList) {
            if (hwt.getHeading().getStyle() != null && hwt.getHeading().getStyle().equals("Heading1")) {
                result.add(hwt);
            }
        }
        return result;
    }

    public static List<String> getMainHeadingsNamesList(XWPFDocument document) {
        List<String> result = new ArrayList<>();
        List<XWPFParagraph> hList = document.getParagraphs();
        if (hList == null) {
            return null;
        }
        for (XWPFParagraph p : hList) {
            if (p.getStyle() != null &&p .getStyle().equals("Heading1")) {
                result.add(p.getText());
            }
        }
        return result;
    }

    public static int getHeadingNumLevel(XWPFParagraph paragraph) {
        String style = paragraph.getStyle();
        if (style != null) {
            switch (style) {
                case "Heading1": return 0;
                case "Heading2": return 1;
                case "Heading3": return 2;
                case "Heading4": return 3;
                case "Heading5": return 4;
            }
        }
        return 6;
    }

    public static List<XWPFParagraph> getSubHeadings(List<XWPFParagraph> parList, XWPFParagraph paragraph) {
        int level = getHeadingNumLevel(paragraph);
        int currentH = 0;
        for (currentH = 0; currentH < parList.size(); ++currentH) {
            if (parList.get(currentH).equals(paragraph)) {
                break;
            }
        }
        int nextH = parList.size();
        for (int k = currentH + 1; k < parList.size(); ++k) {
            if (getHeadingNumLevel(parList.get(k)) == level) {
                nextH = k;
                break;
            }
        }
        if (currentH == parList.size() - 1) {
            List<XWPFParagraph> subHList = new ArrayList<>();
            subHList.add(null);
            return subHList;
        }
        List<XWPFParagraph> subHList = new ArrayList<>();
        for (int j = currentH + 1; j < nextH; ++j) {
            if (getHeadingNumLevel(parList.get(j)) == level + 1) {
                subHList.add(parList.get(j));
            }
        }
        return subHList;
    }


    public static HeadingContent getHeadingContent(List<HeadingWithText> hList, XWPFParagraph heading) {
        HeadingContent hc = new HeadingContent();
        int level = getHeadingNumLevel(heading);
        int currentH = 0;
        for (currentH = 0; currentH < hList.size(); ++currentH) {
            if (hList.get(currentH).getHeading().equals(heading)) {
                break;
            }
        }
        int nextH = hList.size();
        for (int k = currentH + 1; k < hList.size(); ++k) {
            if (getHeadingNumLevel(hList.get(k).getHeading()) == level) {
                nextH = k;
                break;
            }
        }
        if (currentH == hList.size() - 1
                || getHeadingNumLevel(hList.get(currentH + 1).getHeading()) == level - 1) {
            hc.setSubHList(null);
        }
        else {
            List<HeadingWithText> hwtList = new ArrayList<>();
            for (int j = currentH + 1; j < nextH; ++j) {
                hwtList.add(hList.get(j));
            }
            hc.setSubHList(hwtList);
        }
        return hc;
    }
    public static HeadingContent getHeadingContent(XWPFDocument document, XWPFParagraph heading) {
        List<HeadingWithText> hList = TempParamsGetter.getHeadingsList(document);
        if (hList == null || hList.isEmpty()) {
            return null;
        }
        return getHeadingContent(hList, heading);
    }

}
