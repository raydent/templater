package com.example.templater.docBuilder;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DocBuilder {
    public static String output = "rest-with-spring.docx";

    public static void CreateDoc() throws IOException {
        //System.out.println("Main");
        String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Build Your REST API with Spring");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("from HTTP fundamentals to API Mastery");
        subTitleRun.setColor("00CC44");
        subTitleRun.setFontFamily("Courier");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);
        subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);

        XWPFParagraph para1 = document.createParagraph();
        para1.setAlignment(ParagraphAlignment.BOTH);
        String string1 = "Test 1";//convertTextFileToString(paragraph1);
        XWPFRun para1Run = para1.createRun();
        para1Run.setText(string1);

        XWPFParagraph para2 = document.createParagraph();
        para2.setAlignment(ParagraphAlignment.RIGHT);
        String string2 = "Second test";//convertTextFileToString(paragraph2);
        XWPFRun para2Run = para2.createRun();
        para2Run.setText(string2);
        para2Run.setItalic(true);

        XWPFParagraph para3 = document.createParagraph();
        para3.setAlignment(ParagraphAlignment.LEFT);
        String string3 = "Third test";//convertTextFileToString(paragraph3);
        XWPFRun para3Run = para3.createRun();
        para3Run.setText(string3);

        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();
    }
    static void createDocViaFile(String path) throws IOException {
        path = "rest-with-spring.docx";
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
