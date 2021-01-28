package com.example.templater.controller;

import com.example.templater.model.Temp_Full;
import com.example.templater.tempBuilder.AllTempParams;
import com.example.templater.tempBuilder.TempParamsGetter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @PostMapping
    public void upload(@RequestParam("file") MultipartFile file) {
        System.out.println("uploaded file " + file.getOriginalFilename());
        /*try {
            AllTempParams all = TempParamsGetter.getTempParams(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}