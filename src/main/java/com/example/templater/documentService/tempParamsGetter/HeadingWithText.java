package com.example.templater.documentService.tempParamsGetter;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.List;

public class HeadingWithText {
    XWPFParagraph heading;
    List<XWPFParagraph> text;
    List<XWPFTable> tables;

    HeadingWithText() {}

    public HeadingWithText(XWPFParagraph heading, List<XWPFParagraph> text, List<XWPFTable> tables) {
        this.heading = heading;
        this.text = text;
        this.tables = tables;
    }

    public HeadingWithText(XWPFParagraph heading, List<XWPFParagraph> text) {
        this.heading = heading;
        this.text = text;
    }

    public XWPFParagraph getHeading() {
        return heading;
    }

    public void setHeading(XWPFParagraph heading) {
        this.heading = heading;
    }

    public List<XWPFParagraph> getText() {
        return text;
    }

    public void setText(List<XWPFParagraph> text) {
        this.text = text;
    }

    public List<XWPFTable> getTables() {
        return tables;
    }

    public void setTables(List<XWPFTable> tables) {
        this.tables = tables;
    }
}
