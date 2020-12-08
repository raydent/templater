package com.example.templater;

import com.example.templater.tempBuilder.*;
import org.apache.xmlbeans.XmlException;

import java.io.IOException;
import java.util.List;

public class MainTestTempCreation {
    public static void main(String... s){
        TemplateCreater templateCreater = new TemplateCreater();
        TempParams tempParams = TempParams.getDefaultTemp2Params();
        TitleParams titleParams = TitleParams.getDefaultTemp2TitleParams();
        // порядок элементов (соблюдать строго): header 1, header 2, header 3, ..., header n (n <= 5), header(верхний колонтитул),
        // footer, textField. Последние три элемента должны быть: два колонтитула(null оба если нет), textField.
        List<ParagraphParams> paragraphParamsList = ParagraphParams.getDefaultTemp2ParParams();
        TableParams tableParams = TableParams.getDefaultTemplate2TableParams();
        try {
            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
        } catch (IOException | XmlException e) {
            e.printStackTrace();
        }
    }
}
