package com.example.templater.documentService.tempParamsGetter;

import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.List;

public class HeadingContent {
    List<HeadingWithText> subHList;

    HeadingContent() {}

    public HeadingContent(List<HeadingWithText> subHList, List<XWPFTable> tableList) {
        this.subHList = subHList;
    }

    public List<HeadingWithText> getSubHList() {
        return subHList;
    }

    public void setSubHList(List<HeadingWithText> subHList) {
        this.subHList = subHList;
    }
}
