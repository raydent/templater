package com.example.templater.documentService.tempParamsGetter;

import com.example.templater.documentService.docCombine.MatchedHeadingInfo;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.List;

public class HeadingWithText {
    XWPFParagraph heading;
    List<XWPFParagraph> text;
    List<XWPFTable> tables;
    boolean toCorrect = false;
    List<MatchedHeadingInfo> correctionList;
    String finalName;

    HeadingWithText() {
        toCorrect = false;
        correctionList = null;
        finalName = null;
    }

    public HeadingWithText(XWPFParagraph heading, List<XWPFParagraph> text, List<XWPFTable> tables) {
        this.heading = heading;
        this.text = text;
        this.tables = tables;
    }

    public String getFinalName() {
        return finalName;
    }

    public void setFinalName(String finalName) {
        this.finalName = finalName;
    }

    public boolean isToCorrect() {
        return toCorrect;
    }

    public void setToCorrect(boolean toCorrect) {
        this.toCorrect = toCorrect;
    }

    public List<MatchedHeadingInfo> getCorrectionList() {
        return correctionList;
    }

    public void setCorrectionList(List<MatchedHeadingInfo> correctionList) {
        this.correctionList = correctionList;
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
