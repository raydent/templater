package com.example.templater.service;

import com.example.templater.documentService.docCombine.DocCombiner;
import com.example.templater.documentService.docCombine.HeadingsCorrection;
import com.example.templater.documentService.tempBuilder.TemplateCreater;
import com.example.templater.documentService.tempParamsGetter.AllTempParams;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    private String generalDirectory = "src/main/resources/Documents/";

    public File multipartFileToFile(MultipartFile multipartFile, String name, String methodName){
        File theDir = new File(generalDirectory + name + methodName);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        File file = new File(generalDirectory + name + methodName + "/" + multipartFile.getOriginalFilename());
        try {
            file.createNewFile();
            InputStream initialStream = multipartFile.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(buffer);
            outStream.close();
            initialStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void multipartFilesToFileMap(Map<String, List<File>> map,
                                           String name,
                                           MultipartFile[] multipartFiles){
        List<File> files = new ArrayList<>();
        for (int i = 0; i < multipartFiles.length; i++) {
            File file = multipartFileToFile(multipartFiles[i], name, "combine");
            files.add(file);
        }
        map.put(name, files);
    }

    public byte[] combineFiles(List<File> files, List<HeadingsCorrection> headingsCorrectionList){
        byte[] bytes = null;
        try {
            DocCombiner dc = new DocCombiner();
            System.out.println(files);
            XWPFDocument result = dc.combineDocs(files,
                    headingsCorrectionList, true);
            FileOutputStream fos = new FileOutputStream("Combined.docx");
            result.write(fos);
            fos.close();
            FileInputStream fis = new FileInputStream("Combined.docx");
            bytes = IOUtils.toByteArray(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public byte[] applyStyle(File file, AllTempParams allTempParams){
        byte[] bytes = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fis);
            DocCombiner combiner = new DocCombiner();
            XWPFDocument result = combiner.applyTemplateToDoc(document, allTempParams);
            FileOutputStream fos = new FileOutputStream("Applied.docx");
            result.write(fos);
            fos.close();
            fis.close();
            TemplateCreater creater = new TemplateCreater();
            creater.createTemplate(allTempParams);
            fis = new FileInputStream("Applied.docx");
            bytes = IOUtils.toByteArray(fis);
            fis.close();
        } catch (IOException | XmlException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
