package com.example.templater.tempBuilder;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;



import java.io.*;
import java.math.BigInteger;
import java.util.List;

public class TemplateCreater {
    public XWPFDocument createTitlePage(XWPFDocument document, TitleParams titleParams, Fields fields) {
        //создание поля Date в правом верхнем углу
        if (titleParams.getType() != 1) {
            XWPFTable table = document.createTable();
            table.setTableAlignment(TableRowAlign.RIGHT);
            table.removeBorders();
            CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
            width.setType(STTblWidth.DXA);
            width.setW(BigInteger.valueOf(1000));
            XWPFTableRow tableRow = table.getRow(0);
            XWPFTableCell cell = tableRow.getCell(0);
            cell.setColor("4682B4");
            cell.getParagraphs().get(0).setSpacingAfter(700);
            XWPFParagraph p = cell.addParagraph();
            p.setAlignment(ParagraphAlignment.RIGHT);
            p.setIndentationFirstLine(200);
            p.setIndentationRight(70);
            p.setStyle("NoSpacing");
            XWPFRun run = p.createRun();
            run.setText("Date");
            run.setColor(Colors.getColorCode(titleParams.getDateColomn().getTextColor()));
            run.setFontFamily(Fonts.getFontString(titleParams.getDateColomn().getFont()));
            run.setFontSize(titleParams.getDateColomn().getFontSize());
        }

        //создание заголовка
        XWPFParagraph emptyP = document.createParagraph();
        emptyP.createRun();
        emptyP.setSpacingAfter(3200);

        XWPFTable table = document.createTable();
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
        switch (titleParams.getType()) {
            case 1: {
                run.setText("Organisation");
                Fonts font = titleParams.getFirstLine().getFont();
                run.setFontFamily(Fonts.getFontString(titleParams.getFirstLine().getFont()));
                run.setFontSize(titleParams.getFirstLine().getFontSize());
                run.setColor(Colors.getColorCode(titleParams.getFirstLine().getTextColor()));
                run2.setText("Document's name");
                run2.setFontFamily(Fonts.getFontString(titleParams.getSecondLine().getFont()));
                run2.setFontSize(titleParams.getSecondLine().getFontSize());
                run2.setColor(Colors.getColorCode(titleParams.getSecondLine().getTextColor()));
                run3.setText("Description");
                run3.setFontFamily(Fonts.getFontString(titleParams.getThirdLine().getFont()));
                run3.setFontSize(titleParams.getThirdLine().getFontSize());
                run3.setColor(Colors.getColorCode(titleParams.getThirdLine().getTextColor()));
                break;
            }
            case 2 :
            case 3: {
                run.setText("Document's name");
                run.setFontFamily(Fonts.getFontString(titleParams.getFirstLine().getFont()));
                run.setFontSize(titleParams.getFirstLine().getFontSize());
                run.setColor(Colors.getColorCode(titleParams.getFirstLine().getTextColor()));
                run2.setText("DESCRIPTION");
                run2.setFontFamily(Fonts.getFontString(titleParams.getSecondLine().getFont()));
                run2.setFontSize(titleParams.getSecondLine().getFontSize());
                run2.setColor(Colors.getColorCode(titleParams.getSecondLine().getTextColor()));
                run3.setText("NAME");
                run3.setFontFamily(Fonts.getFontString(titleParams.getThirdLine().getFont()));
                run3.setFontSize(titleParams.getThirdLine().getFontSize());
                run3.setColor(Colors.getColorCode(titleParams.getThirdLine().getTextColor()));
                break;
            }
        }

        //создание полей Name и Date снизу титульника
        if (titleParams.getType() == 1) {
            XWPFParagraph emptyP1 = document.createParagraph();
            emptyP1.createRun();
            emptyP1.setSpacingAfter(7200);
            XWPFTable table1 = document.createTable();
            table1.setTableAlignment(TableRowAlign.LEFT);
            table1.removeBorders();
            XWPFTableRow table1RowOne = table1.getRow(0);
            table1RowOne.getCell(0).getParagraphs().get(0).setStyle("NoSpacing");
            run = table1RowOne.getCell(0).getParagraphs().get(0).createRun();
            run.setText("Name");
            run.setFontFamily(Fonts.getFontString(titleParams.getNameField().getFont()));
            run.setFontSize(titleParams.getNameField().getFontSize());
            run.setColor(Colors.getColorCode(titleParams.getNameField().getTextColor()));

            XWPFTableRow table1RowTwo = table1.createRow();
            table1RowTwo.getCell(0).getParagraphs().get(0).setStyle("NoSpacing");
            run = table1RowTwo.getCell(0).getParagraphs().get(0).createRun();
            run.setText("Date");
            run.setFontFamily(Fonts.getFontString(titleParams.getDateField().getFont()));
            run.setFontSize(titleParams.getDateField().getFontSize());
            run.setColor(Colors.getColorCode(titleParams.getDateField().getTextColor()));
        }

        //установка полей на странице
        XWPFParagraph paragraph = document.createParagraph();
        CTSectPr ctSectPr = paragraph.getCTP().addNewPPr().addNewSectPr();

        setFields(ctSectPr, fields);

        return document;
    }

    public XWPFDocument createFieldText(XWPFDocument document, ParagraphParams paragraphParams) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(paragraphParams.getAlignment());
        paragraph.setSpacingAfter(150);
        XWPFRun run = paragraph.createRun();
        run.setText("Text");
        run.setColor(Colors.getColorCode(paragraphParams.getTextColor()));
        run.setFontFamily(Fonts.getFontString(paragraphParams.getFont()));
        run.setFontSize(paragraphParams.getFontSize());

        return document;
    }

    public static BigInteger getNumId(XWPFDocument document) {

        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(0));

        CTLvl cTLvl0 = cTAbstractNum.addNewLvl();
        cTLvl0.setIlvl(BigInteger.ZERO);
        cTLvl0.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl0.addNewLvlText().setVal("%1");
        cTLvl0.addNewStart().setVal(BigInteger.ONE);
        cTLvl0.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl1 = cTAbstractNum.addNewLvl();
        cTLvl1.setIlvl(BigInteger.ONE);
        cTLvl1.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl1.addNewLvlText().setVal("%1.%2");
        cTLvl1.addNewStart().setVal(BigInteger.ONE);
        cTLvl1.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl2 = cTAbstractNum.addNewLvl();
        cTLvl2.setIlvl(BigInteger.TWO);
        cTLvl2.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl2.addNewLvlText().setVal("%1.%2.%3");
        cTLvl2.addNewStart().setVal(BigInteger.ONE);
        cTLvl2.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl3 = cTAbstractNum.addNewLvl();
        cTLvl3.setIlvl(BigInteger.valueOf(3));
        cTLvl3.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl3.addNewLvlText().setVal("%1.%2.%3.%4");
        cTLvl3.addNewStart().setVal(BigInteger.ONE);
        cTLvl3.addNewSuff().setVal(STLevelSuffix.SPACE);

        CTLvl cTLvl4 = cTAbstractNum.addNewLvl();
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

    private static void createNumberedParagraph(XWPFDocument doc, BigInteger numId, BigInteger numLevel, String style,
                                                ParagraphParams paragraphParams) {
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setStyle(style);
        if (paragraphParams.isUnderline()) {
            paragraph.setBorderBottom(Borders.APPLES);
        }
        paragraph.setAlignment(paragraphParams.getAlignment());
        paragraph.setNumID(numId);
        paragraph.setSpacingAfter(100);
        CTDecimalNumber ctDecimalNumber = paragraph.getCTP().getPPr().getNumPr().addNewIlvl();
        ctDecimalNumber.setVal(numLevel);
        XWPFRun run = paragraph.createRun();
        run.setText("Header");
        run.setBold(paragraphParams.isBold());
        run.setFontFamily(Fonts.getFontString(paragraphParams.getFont()));
        run.setFontSize(paragraphParams.getFontSize());
        run.setColor(Colors.getColorCode(paragraphParams.getTextColor()));

    }

    public XWPFDocument createDefaultMainPage(XWPFDocument document, TempParams tempParams, List<ParagraphParams> paragraphParamsList,
                                              Fields fields, TableParams tableParams) {
        BigInteger numId = getNumId(document);
        createNumberedParagraph(document, numId,BigInteger.valueOf(0),"Heading1", paragraphParamsList.get(0));
        createNumberedParagraph(document, numId,BigInteger.valueOf(1),"Heading2", paragraphParamsList.get(1));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1));
        createNumberedParagraph(document, numId,BigInteger.valueOf(2),"Heading3", paragraphParamsList.get(2));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1));
        createNumberedParagraph(document, numId,BigInteger.valueOf(3),"Heading4", paragraphParamsList.get(3));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1));
        createNumberedParagraph(document, numId,BigInteger.valueOf(4),"Heading5", paragraphParamsList.get(4));
        document = createFieldText(document, paragraphParamsList.get(paragraphParamsList.size() - 1));

        XWPFTable table = document.createTable(tableParams.getRows(), tableParams.getColoms());
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
                    tableRow.getCell(j).setColor(Colors.getColorCode(tableParams.getHeadingCellColor()));
                }
                run = tableRow.getCell(j).getParagraphs().get(0).createRun();
                run.setText(" ");
                if (i == 0) {
                    run.setFontFamily(Fonts.getFontString(tableParams.getHeadingTextFont()));
                    run.setFontSize(tableParams.getHeadingTextFontSize());
                    run.setBold(true);
                }
                run.setFontFamily("Calibri");
                run.setFontSize(11);
            }
        }

        //установка полей на странице
        CTSectPr ctSectPr = null;
        if (tempParams.isNumeration()) {
            CTDocument1 ctDocument = document.getDocument();
            CTBody ctBody = ctDocument.getBody();
            ctSectPr = ctBody.getSectPr();
        }
        else {
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

    public void createTemplate(TempParams tempParams, TitleParams titleParams, List<ParagraphParams> paragraphParamsList, TableParams tableParams) throws IOException, XmlException {
        XWPFDocument temp = new XWPFDocument(new FileInputStream(new File("Empty.docx")));
        XWPFDocument document = new XWPFDocument();
        XWPFStyles styles = document.createStyles();
        styles.setStyles(temp.getStyle());

        if (tempParams.isNumeration()) {
            XWPFFooter footer = document.createFooter(HeaderFooterType.FIRST);
            XWPFParagraph paragraph = footer.getParagraphArray(0);
            if (paragraph == null)
                paragraph = footer.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run = paragraph.createRun();
            paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* ARABIC MERGEFORMAT");
        }

        document = createTitlePage(document, titleParams, tempParams.getField());
        document = createDefaultMainPage(document, tempParams, paragraphParamsList, tempParams.getField(), tableParams);

        if (tempParams.isNumeration()) {
            CTDocument1 ctDocument = document.getDocument();
            CTBody ctBody = ctDocument.getBody();
            CTSectPr ctSectPrLastSect = ctBody.getSectPr();
            CTHdrFtrRef ctHdrFtrRef = ctSectPrLastSect.getFooterReferenceArray(0);
            ctHdrFtrRef.setType(STHdrFtr.DEFAULT);
            CTHdrFtrRef[] ctHdrFtrRefs = new CTHdrFtrRef[]{ctHdrFtrRef};
            ctSectPrLastSect.setFooterReferenceArray(ctHdrFtrRefs);

            ctSectPrLastSect.unsetTitlePg();
        }
        try {
            document.write(new FileOutputStream("Template.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
