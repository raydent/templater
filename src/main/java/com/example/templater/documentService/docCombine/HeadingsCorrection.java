package com.example.templater.documentService.docCombine;

import java.util.List;

public class HeadingsCorrection {
    List<MatchedHeadingInfo> headings;
    String finalName;

    public HeadingsCorrection() {}

    public HeadingsCorrection(List<MatchedHeadingInfo> headings, String finalName) {
        this.headings = headings;
        this.finalName = finalName;
    }

    public List<MatchedHeadingInfo> getHeadings() {
        return headings;
    }

    public void setHeadings(List<MatchedHeadingInfo> headings) {
        this.headings = headings;
    }

    public String getFinalName() {
        return finalName;
    }

    public void setFinalName(String finalName) {
        this.finalName = finalName;
    }

    @Override
    public String toString() {
        return "HeadingsCorrection{" +
                "headings=" + headings +
                ", finalName='" + finalName + '\'' +
                '}';
    }
}
