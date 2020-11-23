package com.example.templater.tempBuilder;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;


import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class TemplateCreater {
    public XWPFDocument createDefaultTitlePage(XWPFDocument document, int type) {
        //создание поля Date в правом верхнем углу
        if (type != 1) {
            XWPFTable table = document.createTable();
            table.setTableAlignment(TableRowAlign.RIGHT);
            table.removeBorders();
            CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
            width.setType(STTblWidth.DXA);
            width.setW(BigInteger.valueOf(900));
            XWPFTableRow tableRow = table.getRow(0);
            XWPFTableCell cell = tableRow.getCell(0);
            cell.setColor("4682B4");
            cell.getParagraphs().get(0).setSpacingAfter(700);
            XWPFParagraph p = cell.addParagraph();
            p.setAlignment(ParagraphAlignment.RIGHT);
            p.setIndentationFirstLine(200);
            p.setIndentationRight(70);
            XWPFRun run = p.createRun();
            run.setText("Date");
            run.setColor("FFFFFF");
            run.setFontFamily("Calibry");
            run.setFontSize(12);
        }

        //создание заголовка
        XWPFParagraph emptyP = document.createParagraph();
        emptyP.createRun();
        emptyP.setSpacingAfter(3200);

        XWPFTable table = document.createTable();
        table.setTableAlignment(TableRowAlign.LEFT);
        table.removeBorders();
        if (type == 1) {
            table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 10, "4682B4");
        }
        XWPFTableRow tableRowOne = table.getRow(0);
        XWPFTableRow tableRowTwo = table.createRow();
        XWPFTableRow tableRowThree = table.createRow();
        if (type != 1) {
            XWPFParagraph p1 = tableRowOne.getCell(0).getParagraphs().get(0);
            p1.setSpacingAfter(800);
        }
        XWPFRun run = tableRowOne.getCell(0).getParagraphs().get(0).createRun();
        XWPFRun run2 = tableRowTwo.getCell(0).getParagraphs().get(0).createRun();
        XWPFRun run3 = tableRowThree.getCell(0).getParagraphs().get(0).createRun();
        switch (type) {
            case 1: {
                run.setText("Organisation");
                run.setFontFamily("Calibry");
                run.setFontSize(12);
                run.setColor("4682B4");
                run2.setText("Document's name");
                run2.setFontFamily("Calibri");
                run2.setFontSize(44);
                run2.setColor("4682B4");
                run3.setText("Description");
                run3.setFontFamily("Calibri");
                run3.setFontSize(12);
                run3.setColor("4682B4");
                break;
            }
            case 2: {
                run.setText("Document's name");
                run.setFontFamily("Calibria");
                run.setFontSize(36);
                run.setColor("4682B4");
                run2.setText("DESCRIPTION");
                run2.setFontFamily("Calibria");
                run2.setFontSize(14);
                run2.setColor("1F3864");
                run3.setText("NAME");
                run3.setFontFamily("Calibria");
                run3.setFontSize(12);
                run3.setColor("4682B4");
                break;
            }
            case 3: {
                run.setText("Document's name");
                run.setFontFamily("Arial");
                run.setFontSize(36);
                run.setColor("4682B4");
                run2.setText("DESCRIPTION");
                run2.setFontFamily("Arial");
                run2.setFontSize(14);
                run2.setColor("1F3864");
                run3.setText("NAME");
                run3.setFontFamily("Arial");
                run3.setFontSize(12);
                run3.setColor("4682B4");
                break;
            }
            default: {
                run.setText("Organisation");
                run.setFontFamily("Calibri");
                run.setFontSize(12);
                run.setColor("4682B4");
            }
        }

        //создание полей Name и Date снизу титульника
        if (type == 1) {
            XWPFParagraph emptyP1 = document.createParagraph();
            emptyP1.createRun();
            emptyP1.setSpacingAfter(7200);
            XWPFTable table1 = document.createTable();
            table1.setTableAlignment(TableRowAlign.LEFT);
            table1.removeBorders();
            XWPFTableRow table1RowOne = table1.getRow(0);
            run = table1RowOne.getCell(0).getParagraphs().get(0).createRun();
            run.setText("Name");
            run.setFontFamily("Calibri");
            run.setFontSize(14);
            run.setColor("4682B4");

            XWPFTableRow table1RowTwo = table1.createRow();
            run = table1RowTwo.getCell(0).getParagraphs().get(0).createRun();
            run.setText("Date");
            run.setFontFamily("Calibri");
            run.setFontSize(14);
            run.setColor("4682B4");
        }

        //установка полей на странице
        XWPFParagraph paragraph = document.createParagraph();
        CTSectPr ctSectPr = paragraph.getCTP().addNewPPr().addNewSectPr();
        switch (type) {
            case 1: {
                setFields(ctSectPr, Fields.narrow);
                break;
            }
            case 2:
            case 3: {
                setFields(ctSectPr, Fields.average);
                break;
            }
            default: {
                setFields(ctSectPr, Fields.average);
            }
        }
        return document;
    }

    public XWPFDocument createFieldText(XWPFDocument document, int type) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingAfter(300);
        XWPFRun run = paragraph.createRun();
        switch (type) {
            case 1: {
                run.setText("Text");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                break;
            }
            case 2: {
                run.setText("text");
                run.setFontFamily("Calibria");
                run.setFontSize(11);
                break;
            }
            case 3: {
                run.setText("Text");
                run.setFontFamily("Arial");
                run.setFontSize(14);
                break;
            }
            default: {
                run.setText("Text");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                break;
            }
        }
        return document;
    }

    public XWPFDocument createDefaultMainPage(XWPFDocument document, int type) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setBorderBottom(Borders.APPLES);
        paragraph.setSpacingAfter(300);
        XWPFRun run = paragraph.createRun();
        switch (type) {
            case 1: {
                run.setText("1 Header");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(20);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1 Header");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(14);
                document = createFieldText(document, 1);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1 Header");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                document = createFieldText(document, 1);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1.1 Header");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                document = createFieldText(document, 1);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1.1.1 ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = paragraph.createRun();
                run.setText("Header");
                run.setItalic(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                document = createFieldText(document, 1);

                XWPFTable table = document.createTable(2, 2);
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
                        cellWidth.setW(BigInteger.valueOf(5000));
                    }
                }
                XWPFTableRow tableRowOne = table.getRow(0);
                XWPFTableRow tableRowTwo = table.getRow(1);
                tableRowOne.getCell(0).setColor("4682B4");
                tableRowOne.getCell(1).setColor("4682B4");
                run = tableRowOne.getCell(0).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowOne.getCell(1).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowTwo.getCell(0).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowTwo.getCell(1).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                break;
            }
            case 2: {
                run.setText("Main header");
                run.setColor("1F3864");
                run.setBold(true);
                run.setFontFamily("Calibria");
                run.setFontSize(14);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1 Header");
                run.setColor("1F3864");
                run.setBold(true);
                run.setFontFamily("Calibria");
                run.setFontSize(12);
                document = createFieldText(document, 2);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1 Header");
                run.setColor("4682B4");
                run.setBold(true);
                run.setFontFamily("Calibria");
                run.setFontSize(11);
                document = createFieldText(document, 2);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1.1 ");
                run.setColor("4682B4");
                run.setFontFamily("Calibria");
                run.setFontSize(11);
                run = paragraph.createRun();
                run.setText("Header");
                run.setItalic(true);
                run.setColor("4682B4");
                run.setFontFamily("Calibria");
                run.setFontSize(11);
                document = createFieldText(document, 2);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1.1.1 Header");
                run.setColor("1F3864");
                run.setFontFamily("Calibria");
                run.setFontSize(11);
                document = createFieldText(document, 2);

                XWPFTable table = document.createTable(2, 2);
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
                        if (j == 0) {
                            cellWidth.setW(BigInteger.valueOf(1500));
                        }
                        else {
                            cellWidth.setW(BigInteger.valueOf(7000));
                        }

                    }
                }
                XWPFTableRow tableRowOne = table.getRow(0);
                XWPFTableRow tableRowTwo = table.getRow(1);
                tableRowOne.getCell(0).setColor("4682B4");
                tableRowOne.getCell(1).setColor("4682B4");
                run = tableRowOne.getCell(0).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowOne.getCell(1).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowTwo.getCell(0).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowTwo.getCell(1).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                break;
            }
            case 3: {
                run.setText("Main header");
                run.setBold(true);
                run.setFontFamily("Arial");
                run.setFontSize(18);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1 Header");
                run.setBold(true);
                run.setFontFamily("Arial");
                run.setFontSize(16);
                document = createFieldText(document, 3);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1 Header");
                run.setBold(true);
                run.setFontFamily("Arial");
                run.setFontSize(14);
                document = createFieldText(document, 3);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1.1 ");
                run.setFontFamily("Arial");
                run.setFontSize(14);
                run.setText("Header");
                run.setItalic(true);
                run.setFontFamily("Arial");
                run.setFontSize(14);
                document = createFieldText(document, 3);
                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                run = paragraph.createRun();
                run.setText("1.1.1.1.1 Header");
                run.setFontFamily("Arial");
                run.setFontSize(14);
                document = createFieldText(document, 3);

                XWPFTable table = document.createTable(2, 3);
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
                        cellWidth.setW(BigInteger.valueOf(3000));
                    }
                }
                XWPFTableRow tableRowOne = table.getRow(0);
                XWPFTableRow tableRowTwo = table.getRow(1);
                tableRowOne.getCell(0).setColor("4682B4");
                tableRowOne.getCell(1).setColor("4682B4");
                tableRowOne.getCell(2).setColor("4682B4");
                run = tableRowOne.getCell(0).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowOne.getCell(1).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowOne.getCell(2).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setBold(true);
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowTwo.getCell(0).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowTwo.getCell(1).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                run = tableRowTwo.getCell(2).getParagraphs().get(0).createRun();
                run.setText(" ");
                run.setFontFamily("Calibri");
                run.setFontSize(11);
                break;
            }
        }

        //установка полей на странице
        CTDocument1 ctDocument = document.getDocument();
        CTBody ctBody = ctDocument.getBody();
        CTSectPr ctSectPrLastSect = ctBody.getSectPr();
        if (type == 1) {
            setFields(ctSectPrLastSect, Fields.narrow);
        }
        else if (type == 2){
            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            setFields(sectPr, Fields.average);
        }
        else {
            setFields(ctSectPrLastSect, Fields.average);
        }
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

    public void createDefaultTemplate(int type) {
        XWPFDocument document= new XWPFDocument();
        if (type != 2) {
            XWPFFooter footer = document.createFooter(HeaderFooterType.FIRST);
            XWPFParagraph paragraph = footer.getParagraphArray(0);
            if (paragraph == null)
                paragraph = footer.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run = paragraph.createRun();
            paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* ARABIC MERGEFORMAT");
        }

        switch (type) {
            case 1: {
                document = createDefaultTitlePage(document, 1);
                document = createDefaultMainPage(document, 1);
                break;
            }
            case 2: {
                document = createDefaultTitlePage(document, 2);
                document = createDefaultMainPage(document, 2);
                break;
            }
            case 3: {
                document = createDefaultTitlePage(document, 3);
                document = createDefaultMainPage(document, 3);
                break;
            }
        }

        if (type != 2) {
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
            document.write(new FileOutputStream("CreateWordMultipleSectionPageNumbering.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCustomTemplate(TempParams tempParams, List<ParagraphParams> paragraphParamsList) {
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
    }

}
