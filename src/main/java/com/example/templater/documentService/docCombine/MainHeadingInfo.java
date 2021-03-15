package com.example.templater.documentService.docCombine;

import java.util.List;

public class MainHeadingInfo {
    String headingName;
    String finalName;
    boolean isMatched;
    List<MatchedHeadingInfo> matched;
    String fileName;
    List<String> subheadingsNames;

    public String getFinalName() {
        return finalName;
    }

    public void setFinalName(String finalName) {
        this.finalName = finalName;
    }

    public String getHeadingName() {
        return headingName;
    }

    public void setHeadingName(String headingName) {
        this.headingName = headingName;
    }

    public boolean getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(boolean matched) {
        isMatched = matched;
    }

    public List<MatchedHeadingInfo> getMatched() {
        return matched;
    }

    public void setMatched(List<MatchedHeadingInfo> matched) {
        this.matched = matched;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getSubheadingsNames() {
        return subheadingsNames;
    }

    public void setSubheadingsNames(List<String> subheadingsNames) {
        this.subheadingsNames = subheadingsNames;
    }
    
    

    @Override
    public String toString() {
        return "MainHeadingInfo{" +
                "headingName='" + headingName + '\'' +
                ", finalName='" + finalName + '\'' +
                ", isMatched=" + isMatched +
                ", matched=" + matched +
                ", fileName='" + fileName + '\'' +
                ", subheadingsNames=" + subheadingsNames +
                '}';
    }
}
