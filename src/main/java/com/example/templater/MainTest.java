package com.example.templater;

import com.example.templater.docBuilder.DocBuilder;

import java.io.IOException;

public class MainTest {
    public static void main(String... s){
        DocBuilder docBuilder = new DocBuilder();
        try {
            docBuilder.createDocViaFile("ResultFile.docx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
