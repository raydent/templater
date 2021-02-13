package com.example.templater;

import com.example.templater.documentService.docCombine.DocCombiner;
import com.example.templater.documentService.docCombine.MainHeadingInfo;
import com.example.templater.documentService.docCombine.MatchedHeadingInfo;
import com.example.templater.documentService.tempBuilder.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;


import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        try {
            DocCombiner dc = new DocCombiner();
            XWPFDocument result = dc.combineDocs(Arrays.asList(file, file1), null);
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


    }

}
