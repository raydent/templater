package com.example.templater.documentService.tempParamsGetter;

import com.example.templater.documentService.tempBuilder.*;
import com.example.templater.model.Temp_Full;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import java.util.Arrays;
import java.util.List;

public class AllTempParams {
    TempParams tempParams;
    TitleParams titleParams;
    List<ParagraphParams> paramsList;
    TableParams tableParams;

    public AllTempParams() {}

    public AllTempParams(TempParams tempParams, TitleParams titleParams, List<ParagraphParams> paramsList, TableParams tableParams) {
        this.tempParams = tempParams;
        this.titleParams = titleParams;
        this.paramsList = paramsList;
        this.tableParams = tableParams;
    }

    public AllTempParams(Temp_Full temp_full){
        ParagraphParams firstParagraph = new ParagraphParams(temp_full, 1);
        ParagraphParams secondParagraph = new ParagraphParams(temp_full, 2);
        ParagraphParams thirdParagraph = new ParagraphParams(temp_full, 3);
        ParagraphParams fourthParagraph = new ParagraphParams(temp_full, 4);
        ParagraphParams fifthParagraph = new ParagraphParams(temp_full, 5);
        ParagraphParams textField = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode("black"), Colors.getColorCode("black"));
        List<ParagraphParams> paragraphParamsList = Arrays.asList(firstParagraph, secondParagraph,
                thirdParagraph, fourthParagraph, fifthParagraph, null, null, textField);
        TitleParams titleParams = new TitleParams(temp_full);

        TempParams tempParams = new TempParams(temp_full);

        TableParams tableParams = new TableParams(temp_full);

        this.tempParams = tempParams;
        this.titleParams = titleParams;
        this.paramsList = paragraphParamsList;
        this.tableParams = tableParams;
    }

    public TempParams getTempParams() {
        return tempParams;
    }

    public void setTempParams(TempParams tempParams) {
        this.tempParams = tempParams;
    }

    public TitleParams getTitleParams() {
        return titleParams;
    }

    public void setTitleParams(TitleParams titleParams) {
        this.titleParams = titleParams;
    }

    public List<ParagraphParams> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<ParagraphParams> paramsList) {
        this.paramsList = paramsList;
    }

    public TableParams getTableParams() {
        return tableParams;
    }

    public void setTableParams(TableParams tableParams) {
        this.tableParams = tableParams;
    }

    @Override
    public String toString() {
        return "AllTempParams{" +
                "tempParams=" + tempParams +
                ", titleParams=" + titleParams +
                ", paramsList=" + paramsList +
                ", tableParams=" + tableParams +
                '}';
    }
}
