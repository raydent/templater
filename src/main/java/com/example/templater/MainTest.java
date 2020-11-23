package com.example.templater;

import com.example.templater.docBuilder.DocBuilder;
import com.example.templater.tempBuilder.ParagraphParams;
import com.example.templater.tempBuilder.TempParams;
import com.example.templater.tempBuilder.TemplateCreater;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainTest {
    public static void main(String... s){
        TemplateCreater templateCreater = new TemplateCreater();
        TempParams tempParams = new TempParams();
        ParagraphParams paragraphParams1 = new ParagraphParams();
        ParagraphParams paragraphParams2 = new ParagraphParams();
        List<ParagraphParams> paragraphParamsList = Arrays.asList(paragraphParams1, paragraphParams2);
        templateCreater.createDefaultTemplate(2);
    }
}
