package com.example.templater;

import com.example.templater.documentService.docCombine.*;
import com.example.templater.documentService.tempBuilder.*;
import com.example.templater.documentService.tempParamsGetter.AllTempParams;
import com.example.templater.documentService.tempParamsGetter.TempParamsGetter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTestTempCreation {
    public static void main(String... s) throws IOException, XmlException {
        /*
        TemplateCreater templateCreater = new TemplateCreater();
        TempParams tempParams = TempParams.getDefaultTemp1Params();
        TitleParams titleParams = TitleParams.getDefaultTemp1TitleParams();
        // порядок элементов (соблюдать строго): header 1, header 2, header 3, header 4, header 5 (если нет хедера то null),
        // header(верхний колонтитул) (null если нет),footer (null если нет), textField (null, если нет).
        List<ParagraphParams> paragraphParamsList = ParagraphParams.getDefaultTemp1ParParams();
        TableParams tableParams = TableParams.getDefaultTemplate1TableParams();
        AllTempParams allParams = new AllTempParams();
        allParams.setTempParams(tempParams);
        allParams.setTitleParams(titleParams);
        allParams.setParamsList(paragraphParamsList);
        allParams.setTableParams(tableParams);
        try {
            File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\paper New York\\Test2.docx");
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fis);
            DocCombiner combiner = new DocCombiner();
            XWPFDocument result = combiner.applyTemplateToDoc(document, allParams);
            FileOutputStream fos = new FileOutputStream("Applied.docx");
            result.write(fos);
            fos.close();
            fis.close();
            TemplateCreater creater = new TemplateCreater();
            creater.createTemplate(allParams);
        } catch (IOException | XmlException e) {
            e.printStackTrace();
        }

       File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Template.docx");
            try {
                FileInputStream is = new FileInputStream(file);
                XWPFDocument document = new XWPFDocument(is);
                List<HeadingWithText> list = TempParamsGetter.getHeadingsList(document);
                for (HeadingWithText hwt : list) {
                    System.out.println(hwt.toString());
                    if (hwt.getText() != null) {
                        for (XWPFParagraph p : hwt.getText()) {
                            System.out.println(p.getText());
                        }
                    }
                }
                List<XWPFParagraph> list = TempParamsGetter.getSubHeadings(document.getParagraphs(),
                        document.getParagraphs().get(document.getParagraphs().size() - 1));
                for (XWPFParagraph p : list) {
                    System.out.println(p.getText());
                }
                //AllTempParams all = TempParamsGetter.getTempParams(file);
                //System.out.println(all.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
        }

        */
        //File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\1.docx");
        //File file1 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\2.docx");
        //File file2 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\3.docx");
        //File file3 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\4.docx");
        /*File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\paper New York\\Test1.docx");
        File file1 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\paper New York\\Test2.docx");
        File file2 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\paper New York\\Test3.docx");
        try {
            HeadingsCorrection c1 = new HeadingsCorrection(Arrays.asList(
                    new MatchedHeadingInfo("Etymology", "Test1.docx"),
                    new MatchedHeadingInfo("Etymology", "Test3.docx")), "Corrected1");
            HeadingsCorrection c2 = new HeadingsCorrection(Arrays.asList(
                    new MatchedHeadingInfo("History", "Test1.docx"),
                    new MatchedHeadingInfo("History", "Test2.docx"),
                    new MatchedHeadingInfo("History", "Test3.docx")), "Corrected2");
            HeadingsCorrection c3 = new HeadingsCorrection(Arrays.asList(
                    new MatchedHeadingInfo("Economy", "Test3.docx")), "Corrected3");
            HeadingsCorrection c4 = new HeadingsCorrection(Arrays.asList(
                    new MatchedHeadingInfo("Geography", "Test2.docx")), "Corrected4");
            List<HeadingsCorrection> correctionList = Arrays.asList(c1, c2, c3, c4);
            DocCombiner dc = new DocCombiner();
            long start = System.currentTimeMillis();
            XWPFDocument result = dc.combineDocs(Arrays.asList(file, file1, file2), correctionList, true);
            long end = System.currentTimeMillis();
            System.out.println("Total time (ms): " + (end - start));
            FileOutputStream fos = new FileOutputStream("Combined.docx");
            result.write(fos);
            fos.close();
            List<MainHeadingInfo> mainHeadingsInfo = dc.getMainHeadingsInfo();
            for (MainHeadingInfo info : mainHeadingsInfo) {
                System.out.println("Name: " + info.getHeadingName() + ", Final Name: " + info.getFinalName() + ", File name:"
                        + info.getFileName() + ", Is matched: " + info.getIsMatched());
                if (info.getMatched() != null) {
                    System.out.println("Matched: ");
                    for (MatchedHeadingInfo m : info.getMatched()) {
                        System.out.print(m.getHeadingName() + ", " + m.getFileName() + "\n");
                    }
                }
                System.out.println("Subheadings: ");
                for (String str : info.getSubheadingsNames()) {
                    System.out.print(str + ", ");
                }
                System.out.println("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        File file1 = new File("C:\\Users\\a-r-t\\Downloads\\doc (12).docx");
        File file = new File("C:\\Users\\a-r-t\\Downloads\\Res (2).docx");
        FileInputStream fis = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(fis);
        fis.close();
        fis = new FileInputStream(file1);
        XWPFDocument template = new XWPFDocument(fis);
        fis.close();
        DocCombiner combiner = new DocCombiner();
        XWPFDocument result = combiner.applyTemplateToDoc(file, TempParamsGetter.getTempParams(file1));
        FileOutputStream fos = new FileOutputStream("Result.docx");
        result.write(fos);
        fos.close();
    }
}
