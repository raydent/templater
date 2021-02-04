package com.example.templater.documentService.docCombine;

import com.example.templater.documentService.tempParamsGetter.HeadingWithText;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.List;

public class UnresolvedHeadings {
    XWPFDocument document;
    List<HeadingWithText> headings;

    UnresolvedHeadings() {}

    public UnresolvedHeadings(XWPFDocument document, List<HeadingWithText> headings) {
        this.document = document;
        this.headings = headings;
    }

    public XWPFDocument getDocument() {
        return document;
    }

    public void setDocument(XWPFDocument document) {
        this.document = document;
    }

    public List<HeadingWithText> getHeadings() {
        return headings;
    }

    public void setHeadings(List<HeadingWithText> headings) {
        this.headings = headings;
    }
}
