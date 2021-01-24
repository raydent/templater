package com.example.templater.tempBuilder;

import com.example.templater.model.Temp_Full;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempParamsGetter {
    public static Temp_Full getTempParams(MultipartFile file) throws IOException {
        XWPFDocument document = new XWPFDocument(file.getInputStream());

        Temp_Full temp_full = new Temp_Full();
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
        List<XWPFStyle> styleList = Arrays.asList(heading1, heading2, heading3, heading4, heading5, header, footer);
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
            byte[] colorBytes = (byte[]) ctrPr.getColor().getVal();
            String color = Arrays.toString(colorBytes);
            Integer colorInt = Integer.parseInt(color, 2);
            String hexColor = Integer.toHexString(colorInt);
            params.setTextColor(hexColor);
            params.setBold(ctrPr.isSetB());
            params.setItalic(ctrPr.isSetI());
            params.setUnderline(ctrPr.isSetU());
            //params.setTextHighlightColor();
            //params.setAlignment();
            paramsList.add(params);
        }

        //getting template params
        tempParams.setTitle_page(false);
        List<XWPFParagraph> list = document.getParagraphs();
        if (list.get(0).getStyle().equals("NoSpacing")) {
            tempParams.setTitle_page(true);
        }
        else {
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getStyle().equals("Heading1") && i != 0) {
                    tempParams.setTitle_page(true);
                    break;
                }
            }
        }

        tempParams.setHeader(!document.getHeaderList().isEmpty());
        if (document.getFooterList().isEmpty()) {
            tempParams.setFooter(false);
            tempParams.setNumeration(false);
        }
        else if (tempParams.isTitle_page()) {
            List<XWPFParagraph> pL = document.getFooterArray(1).getListParagraph();
            tempParams.setNumeration(false);
            for (XWPFParagraph p : pL) {
                if (p.getCTP().getFldSimpleArray(0) != null) {
                    tempParams.setNumeration(true);
                }
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
        if (tempParams.isTitle_page()) {
            titleParams = null;
        }
        else {
            List<XWPFTable> tList = document.getTables();
            if (!tList.isEmpty()) {
                if (tList.get(0).getRow(0).getCell(0).getParagraphs().get(0).getStyle().equals("NoSpacing")) {
                    titleParams.setType(1);
                    ParagraphParams dateC = new ParagraphParams();
                    XWPFParagraph p = tList.get(0).getRow(0).getCell(0).getParagraphs().get(0);
                    XWPFRun run = p.getRuns().get(0);
                    dateC.setAlignment(p.getAlignment());
                    dateC.setFont(Fonts.getFontEnum(run.getFontFamily()));
                    dateC.setFontSize(run.getFontSize());
                    dateC.setTextColor(run.getColor());
                    //dateC.setTextHighlightColor(run.getTextHightlightColor());
                    dateC.setBold(run.isBold());
                    dateC.setUnderline(run.getUnderline() != null);
                    dateC.setItalic(run.isItalic());
                    titleParams.setDateColomn(dateC);
                }
            }
            else {
                titleParams.setType(2);
                titleParams.setDateColomn(null);
            }
            List<XWPFParagraph> pList = document.getParagraphs();
            List<ParagraphParams> parList = new ArrayList<>();
            ParagraphParams params = new ParagraphParams();
            XWPFRun run;
            for (XWPFParagraph p : pList) {
                if (p.getStyle().equals("NoSpacing")) {
                    run = p.getRuns().get(0);
                    params.setAlignment(p.getAlignment());
                    params.setFont(Fonts.getFontEnum(run.getFontFamily()));
                    params.setFontSize(run.getFontSize());
                    params.setTextColor(run.getColor());
                    //params.setTextHighlightColor(run.getTextHightlightColor());
                    params.setBold(run.isBold());
                    params.setItalic(run.isItalic());
                    params.setUnderline(run.getUnderline() != null);
                    parList.add(params);
                }
            }
            titleParams.setFirstLine(parList.get(0));
            titleParams.setSecondLine(parList.get(1));
            titleParams.setThirdLine(parList.get(2));
            if (parList.get(3) != null) {
                titleParams.setNameField(parList.get(3));
            }
            if (parList.get(4) != null) {
                titleParams.setDateField(parList.get(4));
            }
        }

        //getting table params
        List<XWPFTable> tables = document.getTables();
        int table_pos = 0;
        if (tables.isEmpty()) {
            tableParams = null;
        }
        if (tempParams.isTitle_page() && titleParams.getType() == 1) {
            table_pos = 1;
        }
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
        STHexColor color = (STHexColor) table.getCTTbl().getTblPr().getTblBorders().getTop().getColor();
        tableParams.setBorderColor(color.getStringValue());
        if (table.getNumberOfRows() > 1) {
            tableParams.setCommonCellColor(table.getRow(1).getCell(0).getColor());
        }

        return temp_full;
    }
}
