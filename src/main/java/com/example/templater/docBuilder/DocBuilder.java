package com.example.templater.docBuilder;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DocBuilder {

    public void createDoc() throws IOException {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Title");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Arial");
        titleRun.setFontSize(20);

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("Text in subtitle");
        subTitleRun.setColor("0000FF");
        subTitleRun.setFontFamily("Courier");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);
        subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);

        FileOutputStream out = new FileOutputStream("ResultFile.docx");
        document.write(out);
        out.close();
        document.close();
    }

    public void createDocViaFile(String path) throws IOException {
        Path msWordPath = Paths.get(path);
        XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        document.close();

        XWPFParagraph title = paragraphs.get(0);
        XWPFRun titleRun = title.getRuns().get(0);

        titleRun.getColor();
        titleRun.getFontFamily();
        title.getAlignment();
        titleRun.getFontSize();
        title.getText();
        titleRun.getUnderline();
        titleRun.getUnderlineColor();
    }
}
