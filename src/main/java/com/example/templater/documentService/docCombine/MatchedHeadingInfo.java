package com.example.templater.documentService.docCombine;

public class MatchedHeadingInfo {
    String headingName;
    String fileName;

    public String getHeadingName() {
        return headingName;
    }

    public void setHeadingName(String headingName) {
        this.headingName = headingName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "MatchedHeadingInfo{" +
                "headingName='" + headingName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
