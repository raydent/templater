package com.example.templater;

import com.example.templater.documentService.docCombine.DocCombiner;
import com.example.templater.documentService.tempBuilder.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;


import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainTestTempCreation {
    public static void main(String... s) {
/*
        TemplateCreater templateCreater = new TemplateCreater();
        TempParams tempParams = TempParams.getDefaultTemp2Params();
        TitleParams titleParams = TitleParams.getDefaultTemp2TitleParams();
        // порядок элементов (соблюдать строго): header 1, header 2, header 3, header 4, header 5 (если нет хедера то null),
        // header(верхний колонтитул) (null если нет),footer (null если нет), textField (null, если нет).
        List<ParagraphParams> paragraphParamsList = ParagraphParams.getDefaultTemp2ParParams();
        TableParams tableParams = TableParams.getDefaultTemplate2TableParams();
        try {
            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
        } catch (IOException | XmlException e) {
            e.printStackTrace();
        }
*/
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
*/
        File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Template1.docx");
        File file1 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Template2.docx");
        File file2 = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Template3.docx");
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            XWPFDocument document1 = new XWPFDocument(is);
            is = new FileInputStream(file1);
            XWPFDocument document2 = new XWPFDocument(is);
            is = new FileInputStream(file2);
            XWPFDocument document3 = new XWPFDocument(is);
            List<XWPFDocument> listDoc = new ArrayList<>();
            listDoc.add(document1);
            listDoc.add(document2);
            //listDoc.add(document3);
            DocCombiner dc = new DocCombiner();
            XWPFDocument result = dc.combineDocs(listDoc);
            FileOutputStream fos = new FileOutputStream("Combined.docx");
            result.write(fos);
            is.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
