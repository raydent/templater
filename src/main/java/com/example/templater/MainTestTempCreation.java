package com.example.templater;

import com.example.templater.tempBuilder.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class MainTestTempCreation {
    public static void main(String... s) {
/*
        TemplateCreater templateCreater = new TemplateCreater();
        TempParams tempParams = TempParams.getDefaultTemp3Params();
        TitleParams titleParams = TitleParams.getDefaultTemp3TitleParams();
        // порядок элементов (соблюдать строго): header 1, header 2, header 3, header 4, header 5 (если нет хедера то null),
        // header(верхний колонтитул) (null если нет),footer (null если нет), textField (null, если нет).
        List<ParagraphParams> paragraphParamsList = ParagraphParams.getDefaultTemp3ParParams();
        TableParams tableParams = TableParams.getDefaultTemplate3TableParams();
        try {
            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
        } catch (IOException | XmlException e) {
            e.printStackTrace();
        }
*/
        File file = new File("C:\\Users\\a-r-t\\Desktop\\IDEA projects\\templater\\Template.docx");
            try {
                FileInputStream is = new FileInputStream(file);
                XWPFDocument document = new XWPFDocument(is);
                List<XWPFParagraph> list = TempParamsGetter.getHeadingsList(document);
                int number = TempParamsGetter.getSubParAmount(list, list.get(0));
                System.out.println(number);
                //AllTempParams all = TempParamsGetter.getTempParams(file);
                //System.out.println(all.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
        }
    }
}
