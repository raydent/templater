package com.example.templater;

import com.example.templater.documentService.docCombine.*;
import com.example.templater.documentService.tempBuilder.*;
import com.example.templater.documentService.tempParamsGetter.AllTempParams;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTestTempCreation {
    public static void main(String... s) {

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
            /*TemplateCreater creater = new TemplateCreater();
            creater.createTemplate(allParams);*/
        } catch (IOException | XmlException e) {
            e.printStackTrace();
        }

       /*File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Template.docx");
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


        //File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\1.docx");
        //File file1 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\2.docx");
        //File file2 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\3.docx");
        //File file3 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\Big files\\4.docx");
        //File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\paper New York\\Test1.docx");
        //File file1 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\paper New York\\Test2.docx");
        //File file2 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Test files\\paper New York\\Test3.docx");
        try {
            /*List<HeadingsCorrection>  correctionList = new ArrayList<>();
            HeadingsCorrection c = new HeadingsCorrection();
            c.setFinalName("Corrected");
            MatchedHeadingInfo h1 = new MatchedHeadingInfo();
            h1.setFileName("Test1.docx");
            h1.setHeadingName("Etymology");
            MatchedHeadingInfo h2 = new MatchedHeadingInfo();
            h2.setFileName("Test2.docx");
            h2.setHeadingName("History");
            List<MatchedHeadingInfo> headings = Arrays.asList(h1, h2);
            c.setHeadings(headings);
            correctionList.add(c);

            HeadingsCorrection c1 = new HeadingsCorrection();
            c1.setFinalName("Corrected1");
            MatchedHeadingInfo h11 = new MatchedHeadingInfo();
            h11.setFileName("Test2.docx");
            h11.setHeadingName("Geography");
            MatchedHeadingInfo h22 = new MatchedHeadingInfo();
            h22.setFileName("Test3.docx");
            h22.setHeadingName("Economy");
            List<MatchedHeadingInfo> headings1 = Arrays.asList(h11, h22);
            c1.setHeadings(headings1);
            correctionList.add(c1);
*/
           /* DocCombiner dc = new DocCombiner();
            long start = System.currentTimeMillis();
            XWPFDocument result = dc.combineDocs(Arrays.asList(file, file1, file2, file3), null, true);
            long end = System.currentTimeMillis();
            System.out.println("Total time (ms): " + (end - start));
            FileOutputStream fos = new FileOutputStream("Combined.docx");
            result.write(fos);
            fos.close();
            List<MainHeadingInfo> mainHeadingsInfo = dc.getMainHeadingsInfo();
            for (MainHeadingInfo info : mainHeadingsInfo) {
                System.out.println("Name: " + info.getHeadingName() + ", Final Name: " + info.getFinalName() + ", File name:"
                        + info.getFileName() + ", Is matched: " + info.isMatched());
                if (info.getMatched() != null) {
                    System.out.println("Matched: ");
                    for (MatchedHeadingInfo m : info.getMatched()) {
                        System.out.print(m.getHeadingName() + ", " + m.getFileName() + "\n");
                    }
                    System.out.println("Subheadings: ");
                }
                for (String str : info.getSubheadingsNames()) {
                    System.out.print(str + ", ");
                }
                System.out.println("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
            */
    }
}
