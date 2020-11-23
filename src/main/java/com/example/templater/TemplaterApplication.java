package com.example.templater;

import com.example.templater.docBuilder.DocBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemplaterApplication {

    public static void main(String[] args) {
//        DocBuilder.handleSimpleDoc();
        SpringApplication.run(TemplaterApplication.class, args);

    }

}
