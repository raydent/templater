package com.example.templater.documentService.tempBuilder;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.thymeleaf.model.IDocType;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
//заглушка
public class TemplateCreater {
    public XWPFDocument createTitlePage(XWPFDocument document, TitleParams titleParams, Fields fields) {
        //создание поля Date в правом верхнем углу
        if (titleParams.getType() == 1) {
            XWPFTable table = document.createTable();
            table.setTableAlignment(TableRowAlign.RIGHT);
            table.removeBorders();
            CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
            width.setType(STTblWidth.DXA);
            width.setW(BigInteger.valueOf(1600));
            XWPFTableRow tableRow = table.getRow(0);
            XWPFTableCell cell = tableRow.getCell(0);
            cell.setColor("4682B4");
            XWPFParagraph p = cell.getParagraphs().get(0);
            p.setSpacingBefore(600);
            p.setAlignment(ParagraphAlignment.RIGHT);
            p.setIndentationLeft(200);
            p.setIndentationRight(70);
            p.setStyle("NoSpacing");
            XWPFRun run = p.createRun();
            run.setText("Date");
            run.setColor(titleParams.getDateColomn().getTextColor());
            run.setFontFamily(Fonts.getFontString(titleParams.getDateColomn().getFont()));
            run.setFontSize(titleParams.getDateColomn().getFontSize());
            if (titleParams.getDateColomn().isBold()) {
                run.setBold(true);
            }
            if (titleParams.getDateColomn().isItalic()) {
                run.setItalic(true);
            }
            if (titleParams.getDateColomn().isUnderline()) {
                run.setUnderline(UnderlinePatterns.SINGLE);
            }
        }

        XWPFTable table = document.createTable();
        table.getCTTbl().getTblPr().addNewTblpPr().setVertAnchor(STVAnchor.TEXT);
        table.getCTTbl().getTblPr().getTblpPr().setTblpY(BigInteger.valueOf(2500));
        CTJc jc = table.getCTTbl().getTblPr().addNewJc();
        jc.setVal(STJc.LEFT);
        table.setTableAlignment(TableRowAlign.LEFT);
        table.removeBorders();
        if (titleParams.getType() == 1) {
            table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 10, "4682B4");
        }
        XWPFTableRow tableRowOne = table.getRow(0);
        XWPFTableRow tableRowTwo = table.createRow();
        XWPFTableRow tableRowThree = table.createRow();
        if (titleParams.getType() != 1) {
            XWPFParagraph p1 = tableRowOne.getCell(0).getParagraphs().get(0);
            p1.setSpacingAfter(800);
        }
        tableRowOne.getCell(0).getParagraphs().get(0).setStyle("NoSpacing");
        tableRowTwo.getCell(0).getParagraphs().get(0).setStyle("NoSpacing");
        tableRowThree.getCell(0).getParagraphs().get(0).setStyle("NoSpacing");
        XWPFRun run = tableRowOne.getCell(0).getParagraphs().get(0).createRun();
        XWPFRun run2 = tableRowTwo.getCell(0).getParagraphs().get(0).createRun();
        XWPFRun run3 = tableRowThree.getCell(0).getParagraphs().get(0).createRun();
        if (titleParams.getFirstLine().isBold()) {
            run.setBold(true);
        }
        if (titleParams.getFirstLine().isItalic()) {
            run.setItalic(true);
        }
        if (titleParams.getFirstLine().isUnderline()) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
        if (titleParams.getSecondLine().isBold()) {
            run2.setBold(true);
        }
        if (titleParams.getSecondLine().isItalic()) {
            run2.setItalic(true);
        }
        if (titleParams.getSecondLine().isUnderline()) {
            run2.setUnderline(UnderlinePatterns.SINGLE);
        }
        if (titleParams.getThirdLine().isBold()) {
            run3.setBold(true);
        }
        if (titleParams.getThirdLine().isItalic()) {
            run3.setItalic(true);
        }
        if (titleParams.getThirdLine().isUnderline()) {
            run3.setUnderline(UnderlinePatterns.SINGLE);
        }

        switch (titleParams.getType()) {
            case 1: {
                run.setText("Organisation");
                run.setFontFamily(Fonts.getFontString(titleParams.getFirstLine().getFont()));
                run.setFontSize(titleParams.getFirstLine().getFontSize());
                run.setColor(titleParams.getFirstLine().getTextColor());
                run2.setText("Document's name");
                run2.setFontFamily(Fonts.getFontString(titleParams.getSecondLine().getFont()));
                run2.setFontSize(titleParams.getSecondLine().getFontSize());
                run2.setColor(titleParams.getSecondLine().getTextColor());
                run3.setText("Description");
                run3.setFontFamily(Fonts.getFontString(titleParams.getThirdLine().getFont()));
                run3.setFontSize(titleParams.getThirdLine().getFontSize());
                run3.setColor(titleParams.getThirdLine().getTextColor());
                break;
            }
            case 2:
            case 3: {
                run.setText("Document's name");
                run.setFontFamily(Fonts.getFontString(titleParams.getFirstLine().getFont()));
                run.setFontSize(titleParams.getFirstLine().getFontSize());
                run.setColor(titleParams.getFirstLine().getTextColor());
                run2.setText("DESCRIPTION");
                run2.setFontFamily(Fonts.getFontString(titleParams.getSecondLine().getFont()));
                run2.setFontSize(titleParams.getSecondLine().getFontSize());
                run2.setColor(titleParams.getSecondLine().getTextColor());
                run3.setText("NAME");
                run3.setFontFamily(Fonts.getFontString(titleParams.getThirdLine().getFont()));
                run3.setFontSize(titleParams.getThirdLine().getFontSize());
                run3.setColor(titleParams.getThirdLine().getTextColor());
                break;
            }
        }

        //создание полей Name и Date снизу титульника
        if (titleParams.getType() != 1) {
            XWPFTable table1 = document.createTable();
            table1.getCTTbl().getTblPr().addNewTblpPr().setVertAnchor(STVAnchor.TEXT);
            table1.getCTTbl().getTblPr().getTblpPr().setTblpY(BigInteger.valueOf(10500));
            table1.setTableAlignment(TableRowAlign.LEFT);
            table1.removeBorders();
            XWPFTableRow table1RowOne = table1.getRow(0);
            table1RowOne.getCell(0).getParagraphs().get(0).setStyle("NoSpacing");
            run = table1RowOne.getCell(0).getParagraphs().get(0).createRun();
            run.setText("Name");
            run.setFontFamily(Fonts.getFontString(titleParams.getNameField().getFont()));
            run.setFontSize(titleParams.getNameField().getFontSize());
            run.setColor(titleParams.getNameField().getTextColor());
            if (titleParams.getNameField().isBold()) {
                run.setBold(true);
            }
            if (titleParams.getNameField().isItalic()) {
                run.setItalic(true);
            }
            if (titleParams.getNameField().isUnderline()) {
                run.setUnderline(UnderlinePatterns.SINGLE);
            }

            XWPFTableRow table1RowTwo = table1.createRow();
            table1RowTwo.getCell(0).getParagraphs().get(0).setStyle("NoSpacing");
            run = table1RowTwo.getCell(0).getParagraphs().get(0).createRun();
            run.setText("Date");
            run.setFontFamily(Fonts.getFontString(titleParams.getDateField().getFont()));
            run.setFontSize(titleParams.getDateField().getFontSize());
            run.setColor(titleParams.getDateField().getTextColor());
            if (titleParams.getDateField().isBold()) {
                run.setBold(true);
            }
            if (titleParams.getDateField().isItalic()) {
                run.setItalic(true);
            }
            if (titleParams.getDateField().isUnderline()) {
                run.setUnderline(UnderlinePatterns.SINGLE);
            }
        }

        //установка полей на странице
        XWPFParagraph paragraph = document.createParagraph();
        CTSectPr ctSectPr = paragraph.getCTP().addNewPPr().addNewSectPr();

        setFields(ctSectPr, fields);

        return document;
    }

    public XWPFDocument createFieldText(XWPFDocument document, ParagraphParams paragraphParams, double number) {
        if (paragraphParams == null) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("Text");
            return document;
        }
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(paragraphParams.getAlignment());
        paragraph.setSpacingAfter(150);
        setSpacingLine(paragraph, number);
        XWPFRun run = paragraph.createRun();
        run.setText("Text");
        run.setColor(paragraphParams.getTextColor());
        run.setFontFamily(Fonts.getFontString(paragraphParams.getFont()));
        run.setFontSize(paragraphParams.getFontSize());

        if (paragraphParams.isBold()) {
            run.setBold(true);
        }
        if (paragraphParams.isItalic()) {
            run.setItalic(true);
        }
        if (paragraphParams.isUnderline()) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
        return document;
    }

    public static BigInteger getNumId(XWPFDocument document) {

        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(0));

        CTLvl cTLvl0 = cTAbstractNum.addNewLvl();
        cTLvl0.addNewPStyle().setVal("Heading1");
        cTLvl0.setIlvl(BigInteger.ZERO);
        cTLvl0.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl0.addNewLvlText().setVal("%1");
        cTLvl0.addNewStart().setVal(BigInteger.ONE);
        cTLvl0.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl1 = cTAbstractNum.addNewLvl();
        cTLvl1.addNewPStyle().setVal("Heading2");
        cTLvl1.setIlvl(BigInteger.ONE);
        cTLvl1.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl1.addNewLvlText().setVal("%1.%2");
        cTLvl1.addNewStart().setVal(BigInteger.ONE);
        cTLvl1.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl2 = cTAbstractNum.addNewLvl();
        cTLvl2.addNewPStyle().setVal("Heading3");
        cTLvl2.setIlvl(BigInteger.TWO);
        cTLvl2.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl2.addNewLvlText().setVal("%1.%2.%3");
        cTLvl2.addNewStart().setVal(BigInteger.ONE);
        cTLvl2.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl3 = cTAbstractNum.addNewLvl();
        cTLvl3.addNewPStyle().setVal("Heading4");
        cTLvl3.setIlvl(BigInteger.valueOf(3));
        cTLvl3.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl3.addNewLvlText().setVal("%1.%2.%3.%4");
        cTLvl3.addNewStart().setVal(BigInteger.ONE);
        cTLvl3.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl4 = cTAbstractNum.addNewLvl();
        cTLvl4.addNewPStyle().setVal("Heading5");
        cTLvl4.setIlvl(BigInteger.valueOf(4));
        cTLvl4.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl4.addNewLvlText().setVal("%1.%2.%3.%4.%5");
        cTLvl4.addNewStart().setVal(BigInteger.ONE);
        cTLvl4.addNewSuff().setVal(STLevelSuffix.SPACE);

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        XWPFNumbering numbering = document.createNumbering();
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        /*return an ID for the numbering*/
        return numbering.addNum(abstractNumID);
    }

    private void createNumberedParagraph(XWPFDocument doc, BigInteger numId, BigInteger numLevel, String style,
                                                ParagraphParams paragraphParams) {
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setStyle(style);
        paragraph.setAlignment(paragraphParams.getAlignment());
        //paragraph.setNumID(numId);
        paragraph.setSpacingAfter(100);
        //CTDecimalNumber ctDecimalNumber = paragraph.getCTP().getPPr().getNumPr().addNewIlvl();
        //ctDecimalNumber.setVal(numLevel);
        XWPFRun run = paragraph.createRun();
        run.setText("Header");
    }

    public XWPFDocument createDefaultMainPage(XWPFDocument document, TempParams tempParams, List<ParagraphParams> paragraphParamsList,
                                              Fields fields, TableParams tableParams) {
        BigInteger numId = getNumId(document);
        createNumberedParagraph(document, numId,BigInteger.valueOf(0),"Heading1", paragraphParamsList.get(0));
        createNumberedParagraph(document, numId,BigInteger.valueOf(1),"Heading2", paragraphParamsList.get(1));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1),
                tempParams.getInterval_between_lines());
        createNumberedParagraph(document, numId,BigInteger.valueOf(2),"Heading3", paragraphParamsList.get(2));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1),
                tempParams.getInterval_between_lines());
        createNumberedParagraph(document, numId,BigInteger.valueOf(3),"Heading4", paragraphParamsList.get(3));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1),
                tempParams.getInterval_between_lines());
        createNumberedParagraph(document, numId,BigInteger.valueOf(4),"Heading5", paragraphParamsList.get(4));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1),
                tempParams.getInterval_between_lines());

        XWPFTable table = document.createTable(tableParams.getRows(), tableParams.getColoms());

        STHexColor color = STHexColor.Factory.newInstance();
        color.setStringValue(tableParams.getBorderColor());
        CTTblBorders borders= table.getCTTbl().getTblPr().getTblBorders();
        borders.getLeft().setColor(color);
        borders.getBottom().setColor(color);
        borders.getRight().setColor(color);
        borders.getTop().setColor(color);
        borders.getInsideH().setColor(color);
        borders.getInsideV().setColor(color);

        table.setTableAlignment(TableRowAlign.LEFT);
        CTTblLayoutType t = table.getCTTbl().getTblPr().addNewTblLayout();
        t.setType(STTblLayoutType.FIXED);
        for (int i = 0; i < table.getNumberOfRows(); i++) {
            XWPFTableRow row = table.getRow(i);
            int numCells = row.getTableCells().size();
            for (int j = 0; j < numCells; j++) {
                XWPFTableCell cell = row.getCell(j);
                CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
                CTTcPr pr = cell.getCTTc().addNewTcPr();
                pr.addNewNoWrap();
                cellWidth.setW(tableParams.getWidth().get(j));
            }
        }

        XWPFRun run;
        for (int i = 0; i < tableParams.getRows(); ++i) {
            XWPFTableRow tableRow = table.getRow(i);
            for (int j = 0; j < tableParams.getColoms(); ++j) {
                if (i == 0) {
                    tableRow.getCell(j).setColor(tableParams.getHeadingCellColor());
                }
                else {
                    tableRow.getCell(j).setColor(tableParams.getCommonCellColor());
                }
                run = tableRow.getCell(j).getParagraphs().get(0).createRun();
                run.setText(" ");
                if (i == 0) {
                    run.setFontFamily(Fonts.getFontString(tableParams.getHeadingTextFont()));
                    run.setFontSize(tableParams.getHeadingTextFontSize());
                    run.setColor(tableParams.getHeadingCellTextColor());
                    if (tableParams.isHeadingTextBold()) {
                        run.setBold(true);
                    }
                    if (tableParams.isHeadingTextItalic()) {
                        run.setItalic(true);
                    }
                }
                else {
                    run.setFontFamily(Fonts.getFontString(tableParams.getHeadingTextFont()));
                    run.setFontSize(tableParams.getHeadingTextFontSize());
                    run.setColor(tableParams.getHeadingCellTextColor());
                }

            }
        }

        //установка полей на странице
        CTSectPr ctSectPr = null;
        CTDocument1 ctDocument = document.getDocument();
        CTBody ctBody = ctDocument.getBody();
        ctSectPr = ctBody.getSectPr();
        if (ctSectPr == null) {
            ctSectPr = document.getDocument().getBody().addNewSectPr();
        }
        setFields(ctSectPr, fields);
        return document;
    }

    public void setFields(CTSectPr sectPr, Fields fieldType) {
        CTPageMar pageMar2 = sectPr.addNewPgMar();
        switch (fieldType) {
            case wide: {
                pageMar2.setLeft(BigInteger.valueOf(2880L));
                pageMar2.setTop(BigInteger.valueOf(1440L));
                pageMar2.setRight(BigInteger.valueOf(2880L));
                pageMar2.setBottom(BigInteger.valueOf(1440L));
                break;
            }
            case narrow: {
                pageMar2.setLeft(BigInteger.valueOf(720L));
                pageMar2.setTop(BigInteger.valueOf(720L));
                pageMar2.setRight(BigInteger.valueOf(720L));
                pageMar2.setBottom(BigInteger.valueOf(720L));
                break;
            }
            case average: {
                pageMar2.setLeft(BigInteger.valueOf(1699L));
                pageMar2.setTop(BigInteger.valueOf(1138L));
                pageMar2.setRight(BigInteger.valueOf(850L));
                pageMar2.setBottom(BigInteger.valueOf(1138L));
                break;
            }
            default: {
                pageMar2.setLeft(BigInteger.valueOf(1699L));
                pageMar2.setTop(BigInteger.valueOf(1138L));
                pageMar2.setRight(BigInteger.valueOf(850L));
                pageMar2.setBottom(BigInteger.valueOf(1138L));
            }
        }
    }

    public static byte[] hexToBytes(String hexString) {
        HexBinaryAdapter adapter = new HexBinaryAdapter();
        byte[] bytes = adapter.unmarshal(hexString);
        return bytes;
    }

    private void setSpacingLine(XWPFParagraph paragraph, double number) {
        CTPPr ppr = paragraph.getCTP().getPPr();
        if (ppr == null) {
            paragraph.getCTP().addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf((long) (number * 240)));
    }

    public void createTemplate(TempParams tempParams, TitleParams titleParams, List<ParagraphParams> paragraphParamsList, TableParams tableParams) throws IOException, XmlException {
        FileInputStream fis = new FileInputStream(new File("Empty.docx"));
        XWPFDocument temp = new XWPFDocument(fis);
        XWPFDocument document = new XWPFDocument();
        XWPFStyles styles = document.createStyles();
        styles.setStyles(temp.getStyle());

        styles = document.getStyles();
        XWPFStyle style1 = styles.getStyle("Heading1");
        XWPFStyle style2 = styles.getStyle("Heading2");
        XWPFStyle style3 = styles.getStyle("Heading3");
        XWPFStyle style4 = styles.getStyle("Heading4");
        XWPFStyle style5 = styles.getStyle("Heading5");
        XWPFStyle hStyle = styles.getStyle("Header");
        XWPFStyle fStyle = styles.getStyle("Footer");
        List<XWPFStyle> styleList = Arrays.asList(style1, style2, style3, style4, style5, hStyle, fStyle);

        CTStyle ctStyle1 = style1.getCTStyle();
        CTPPr ppr = ctStyle1.getPPr();
        CTOnOff ctOnOffPB = CTOnOff.Factory.newInstance();
        ctOnOffPB.setVal(STOnOff.ON);
        ppr.setPageBreakBefore(ctOnOffPB);
        CTPBdr ctpBdr = CTPBdr.Factory.newInstance();
        CTBorder ctBorder = CTBorder.Factory.newInstance();
        ctBorder.setVal(STBorder.APPLES);
        ctpBdr.setBottom(ctBorder);
        ppr.setPBdr(ctpBdr);

        for (int i = 0; i < styleList.size(); ++i) {
            if (paragraphParamsList.get(i) == null) {
                continue;
            }
            CTStyle ctStyle = styleList.get(i).getCTStyle();
            CTRPr rpr = ctStyle.getRPr();
            if (rpr == null) {
                rpr = ctStyle.addNewRPr();
            }
            CTSpacing spacing = CTSpacing.Factory.newInstance();
            spacing.setAfter(BigInteger.valueOf(0));
            spacing.setBefore(BigInteger.valueOf(0));
            spacing.setLineRule(STLineSpacingRule.AUTO);
            spacing.setLine(BigInteger.valueOf((long) tempParams.getInterval_between_lines() * 240));
            ctStyle.getPPr().setSpacing(spacing);

            //color
            CTColor color = CTColor.Factory.newInstance();
            STHexColor col = STHexColor.Factory.newInstance();
            col.setStringValue(paragraphParamsList.get(i).getTextColor());
            color.setVal(col);
            //color.setVal(hexToBytes(paragraphParamsList.get(i).getTextColor()));
            rpr.setColor(color);
            //CTHighlight highlight = CTHighlight.Factory.newInstance();
            //highlight.setVal(STHighlightColor.BLUE);
            //rpr.setHighlight(highlight);

            //font
            CTFonts fonts = CTFonts.Factory.newInstance();
            fonts.setAscii(Fonts.getFontString(paragraphParamsList.get(i).getFont()));
            rpr.setRFonts(fonts);
            CTHpsMeasure size = CTHpsMeasure.Factory.newInstance();
            size.setVal(new BigInteger(String.valueOf(paragraphParamsList.get(i).getFontSize() * 2)));
            rpr.setSz(size);
            CTOnOff ctOnOffBold = CTOnOff.Factory.newInstance();
            ctOnOffBold.setVal(STOnOff.OFF);
            CTOnOff ctOnOffItalic = CTOnOff.Factory.newInstance();
            ctOnOffItalic.setVal(STOnOff.OFF);
            CTUnderline ctUnderline = CTUnderline.Factory.newInstance();
            ctUnderline.setVal(STUnderline.NONE);
            if (paragraphParamsList.get(i).isBold()) {
                ctOnOffBold.setVal(STOnOff.ON);
            }
            if (paragraphParamsList.get(i).isItalic()) {
                ctOnOffItalic.setVal(STOnOff.ON);
            }
            if (paragraphParamsList.get(i).isUnderline()) {
                ctUnderline.setVal(STUnderline.SINGLE);
            }
            rpr.setB(ctOnOffBold);
            rpr.setI(ctOnOffItalic);
            rpr.setU(ctUnderline);
            ctStyle.setRPr(rpr);
        }

        if (tempParams.isHeader()) {
            ParagraphParams params = paragraphParamsList.get(paragraphParamsList.size() - 3);
            document = createHeader(document, params);
        }

        if (tempParams.isTitle_page()) {
            document = createTitlePage(document, titleParams, tempParams.getField());
        }
        document = createDefaultMainPage(document, tempParams, paragraphParamsList, tempParams.getField(), tableParams);

        if (tempParams.isNumeration()) {
            document = createNumeration(document);
        }
        if (tempParams.isFooter()) {
            ParagraphParams params = paragraphParamsList.get(paragraphParamsList.size() - 2);
            document = createFooter(document, params);
        }

        try {
            FileOutputStream fos = new FileOutputStream("Template.docx");
            document.write(fos);
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public XWPFDocument createHeader(XWPFDocument document, ParagraphParams params) {
        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
        XWPFParagraph paragraph = header.getParagraphArray(0);
        if (paragraph == null) {
            paragraph = header.createParagraph();
        }
        paragraph.setStyle("Header");
        XWPFRun run = paragraph.createRun();
        run.setText("Header text");
        if (params != null) {
            paragraph.setAlignment(params.getAlignment());
            if (params.isBold()) {
                run.setBold(true);
            }
            if (params.isItalic()) {
                run .setItalic(true);
            }
            if (params.isUnderline()) {
                run.setUnderline(UnderlinePatterns.SINGLE);
            }
        }
        return document;
    }

    public XWPFDocument createNumeration(XWPFDocument document) {
        XWPFFooter footer = document.createFooter(HeaderFooterType.FIRST);
        XWPFParagraph paragraph = footer.getParagraphArray(0);
        if (paragraph == null) {
            paragraph = footer.createParagraph();
        }
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* ARABIC MERGEFORMAT");

        CTDocument1 ctDocument = document.getDocument();
        CTBody ctBody = ctDocument.getBody();
        CTSectPr ctSectPrLastSect = ctBody.getSectPr();
        CTHdrFtrRef ctHdrFtrRef = ctSectPrLastSect.getFooterReferenceArray(0);
        ctHdrFtrRef.setType(STHdrFtr.DEFAULT);
        CTHdrFtrRef[] ctHdrFtrRefs = new CTHdrFtrRef[]{ctHdrFtrRef};
        ctSectPrLastSect.setFooterReferenceArray(ctHdrFtrRefs);

        ctSectPrLastSect.unsetTitlePg();
        return document;
    }

    public XWPFDocument createFooter(XWPFDocument document, ParagraphParams params) {
        XWPFFooter footer = document.getFooterArray(0);
        if (footer == null) {
            footer = document.createFooter(HeaderFooterType.DEFAULT);
        }
        XWPFParagraph paragraph = footer.createParagraph();
        paragraph.setStyle("Footer");
        XWPFRun run = paragraph.createRun();
        run.setText("© 2020 Netcracker Technology Corp.\tCONFIDENTIAL AND PROPRIETARY\n");
        XWPFParagraph paragraph1 = footer.createParagraph();
        paragraph1.setStyle("Footer");
        XWPFRun run1 = paragraph1.createRun();
        run1.setText("Disclose and distribute solely to those individuals with a need to know.");
        if (params != null) {
            paragraph.setAlignment(params.getAlignment());
            paragraph1.setAlignment(params.getAlignment());
            if (params.isBold()) {
                run.setBold(true);
                run1.setBold(true);
            }
            if (params.isItalic()) {
                run.setItalic(true);
                run1.setItalic(true);
            }
            if (params.isUnderline()) {
                run.setUnderline(UnderlinePatterns.SINGLE);
                run1.setUnderline(UnderlinePatterns.SINGLE);
            }
        }
        else {
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            paragraph1.setAlignment(ParagraphAlignment.CENTER);
        }
        return document;
    }

    //public void createCustomTemplate(TempParams tempParams, List<ParagraphParams> paragraphParamsList) {
    //пример как делать нумерацию
        /*
        XWPFDocument document= new XWPFDocument();
        //create first footer for section 2 - first created as first footer for the document
        XWPFFooter footer = document.createFooter(HeaderFooterType.FIRST);
        //making it HeaderFooterType.FIRST first to be able creating one more footer later
        //will changing this later to HeaderFooterType.DEFAULT
        XWPFParagraph paragraph = footer.getParagraphArray(0);
        if (paragraph == null)
            paragraph = footer.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run = paragraph.createRun();
        paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* ARABIC MERGEFORMAT");
        //create second footer for section 3 == last section in document
        footer = document.createFooter(HeaderFooterType.DEFAULT);
        paragraph = footer.getParagraphArray(0);
        if (paragraph == null) {
            paragraph = footer.createParagraph();
        }
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* ARABIC MERGEFORMAT");
        //create document content.
        //section 1
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Cover");
        //paragraph with section setting for section above
        paragraph = document.createParagraph();
        CTSectPr ctSectPr = paragraph.getCTP().addNewPPr().addNewSectPr();
        //section 2
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Contents");
        //paragraph with section setting for section above
        paragraph = document.createParagraph();
        CTSectPr ctSectPrSect2 = paragraph.getCTP().addNewPPr().addNewSectPr(); //we need this later
        //section 3
        paragraph = document.createParagraph();
        run=paragraph.createRun();
        run.setText("Text");
        //section setting for section above == last section in document
        CTDocument1 ctDocument = document.getDocument();
        CTBody ctBody = ctDocument.getBody();
        CTSectPr ctSectPrLastSect = ctBody.getSectPr(); //there must be a SectPr already because of the footer settings above
        //get footer reference of first footer and move this to be footer reference for section 2
        CTHdrFtrRef ctHdrFtrRef = ctSectPrLastSect.getFooterReferenceArray(0);
        ctHdrFtrRef.setType(STHdrFtr.DEFAULT); //change this from STHdrFtr.FIRST to STHdrFtr.DEFAULT
        CTHdrFtrRef[] ctHdrFtrRefs = new CTHdrFtrRef[]{ctHdrFtrRef};
        ctSectPrSect2.setFooterReferenceArray(ctHdrFtrRefs);
        ctSectPrLastSect.removeFooterReference(0);
        //unset "there is a title page" for the whole document because we have a section for the title (cover)
        ctSectPrLastSect.unsetTitlePg();
        try {
            document.write(new FileOutputStream("CreateWordMultipleSectionPageNumbering.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    //}

}