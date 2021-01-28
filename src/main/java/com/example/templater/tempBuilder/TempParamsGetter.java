package com.example.templater.tempBuilder;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempParamsGetter {
    public static AllTempParams getTempParams(File file) throws IOException {
        //XWPFDocument document = new XWPFDocument(file.getInputStream());

        FileInputStream is = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(is);

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
            params.setFontSize(ctrPr.getSz().getVal().intValue() / 2);
            params.setTextColor(ctrPr.getColor().xgetVal().getStringValue());
            params.setBold(ctrPr.getB().getVal().equals(STOnOff.ON));
            params.setItalic(ctrPr.getI().getVal().equals(STOnOff.ON));
            params.setUnderline(ctrPr.getU().getVal().equals(STUnderline.SINGLE));
            //params.setTextHighlightColor();
            //params.setAlignment();
            paramsList.add(params);
        }

        //getting template params
        tempParams.setTitle_page(false);
        List<XWPFTable> tList = document.getTables();
        if (!tList.isEmpty()) {
            String style = tList.get(0).getRow(0).getCell(0).getParagraphs().get(0).getStyle();
            if (style != null && style.equals("NoSpacing")) {
                tempParams.setTitle_page(true);
            }
        }

        tempParams.setHeader(!document.getHeaderList().isEmpty());
        List<XWPFFooter> fList = document.getFooterList();
        if (fList.isEmpty()) {
            tempParams.setFooter(false);
            tempParams.setNumeration(false);
        }
        else if (fList.get(fList.size() - 1).getListParagraph().size() == 2){
            tempParams.setFooter(true);
            tempParams.setNumeration(true);
        }
        else {
            XWPFFooter f = fList.get(0);
            if (f.getParagraphArray(0).getCTP().getFldSimpleArray(0) != null) {
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
                tempParams.setInterval_between_lines(style.getCTStyle().getPPr().getSpacing().getLine().doubleValue() / 240);
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
                //dateC.setTextHighlightColor(run.getTextHightlightColor());
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
                    //params.setTextHighlightColor(run.getTextHightlightColor());
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
                //params.setTextHighlightColor(run.getTextHightlightColor());
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
                STHexColor color = table.getCTTbl().getTblPr().getTblBorders().getTop().xgetColor();
                tableParams.setBorderColor(color.getStringValue());
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

    public static List<XWPFParagraph> getHeadingsList(XWPFDocument document) {
        List<XWPFParagraph> parList = new ArrayList<>();
        List<XWPFParagraph> allParList = document.getParagraphs();
        for (XWPFParagraph p : allParList) {
            String style = p.getStyle();
            if (style != null && (style.equals("Heading1") || (style.equals("Heading2")) || (style.equals("Heading3"))
                    || (style.equals("Heading4")) || (style.equals("Heading5")))) {
                parList.add(p);
            }
        }
        return parList;
    }

    public static int getParNumLevel(XWPFParagraph paragraph) {
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
        return 5;
    }

    public static int getSubParAmount(List<XWPFParagraph> parList, XWPFParagraph paragraph) {
        int level = getParNumLevel(paragraph);
        int i = 0;
        for (i = 0; i < parList.size(); ++i) {
            if (parList.get(i).equals(paragraph)) {
                break;
            }
        }
        if (i == parList.size() - 1) {
            return 0;
        }
        int count = 0;
        for (int j = i; j < parList.size(); ++j) {
            if (getParNumLevel(parList.get(j)) == level + 1) {
                ++count;
            }
        }
        return count;
    }
}
