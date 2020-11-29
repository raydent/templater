package com.example.templater;

import com.example.templater.tempBuilder.*;
import org.apache.xmlbeans.XmlException;

import java.io.IOException;
import java.util.List;

public class MainTestTempCreation {
    public static void main(String... s){
        TemplateCreater templateCreater = new TemplateCreater();
        TempParams tempParams = TempParams.getDefaultTemp3Params();
        TitleParams titleParams = TitleParams.getDefaultTemp3TitleParams();
        List<ParagraphParams> paragraphParamsList = ParagraphParams.getDefaultTemp3ParParams();
        TableParams tableParams = TableParams.getDefaultTemplate3TableParams();
        try {
            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
        } catch (IOException | XmlException e) {
            e.printStackTrace();
        }
    }
}
