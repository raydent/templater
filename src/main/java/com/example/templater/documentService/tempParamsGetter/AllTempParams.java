package com.example.templater.documentService.tempParamsGetter;

import com.example.templater.documentService.tempBuilder.ParagraphParams;
import com.example.templater.documentService.tempBuilder.TableParams;
import com.example.templater.documentService.tempBuilder.TempParams;
import com.example.templater.documentService.tempBuilder.TitleParams;

import java.util.List;

public class AllTempParams {
    TempParams tempParams;
    TitleParams titleParams;
    List<ParagraphParams> paramsList;
    TableParams tableParams;

    AllTempParams() {}

    public AllTempParams(TempParams tempParams, TitleParams titleParams, List<ParagraphParams> paramsList, TableParams tableParams) {
        this.tempParams = tempParams;
        this.titleParams = titleParams;
        this.paramsList = paramsList;
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
