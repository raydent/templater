package com.example.templater.controller;

import com.example.templater.model.Temp;
import com.example.templater.model.User;
import com.example.templater.service.IUserService;
import com.example.templater.tempBuilder.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//test
@Controller
public class MainController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;


    final String defaultDirectoryName = "C:\\files\\";
    final String defaultFileName = "test.docx";

    @GetMapping("/login")
    public String getLogin(Model model, @AuthenticationPrincipal User authenticatedUser, @RequestParam(required = false) String error) {
        System.out.println(model.toString());
        if (error != null){
            model.addAttribute("loginError", "Login or password is incorrect");
        }
        if (Objects.nonNull(authenticatedUser)) {
            return "redirect:/user";
        }
        model.addAttribute("userForm", new User());
        return "login";
    }



    @GetMapping("/403")
    public String error403() {
        return "/error";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }



    @PostMapping(value = "/download/{templateId}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    @ResponseBody
    byte[] getFile(@PathVariable String templateId) throws IOException {
        File file = new File(defaultDirectoryName + templateId + ".docx");
        FileInputStream fis = new FileInputStream(file);
        return IOUtils.toByteArray(fis);
    }

    @GetMapping(value = "/template", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    @ResponseBody
    public byte[] template() {
        Temp temp1 = new Temp(1);
        Temp temp2 = new Temp(1);
        Temp temp3 = new Temp(1);
        Temp temp4 = new Temp(1);
        Temp temp5 = new Temp(1);
        Temp firstLineTemp = new Temp(1);
        Temp secondLineTemp = new Temp(1);
        Temp thirdLineTemp = new Temp(1);
        Temp dateColomnTemp = new Temp(1);
        Temp nameFieldTemp = new Temp(1);
        Temp dateFieldTemp = new Temp(1);
        Temp generalTemp = new Temp(1);
        return saveTest(temp1, temp2, temp3, temp4, temp5, firstLineTemp, secondLineTemp, thirdLineTemp,
                dateColomnTemp, nameFieldTemp, dateFieldTemp, generalTemp);
    }



    @GetMapping("/temp")
    public String tempForm(Model model) {
        Temp temp1 = new Temp();
        Temp temp2 = new Temp();
        Temp temp3 = new Temp();
        Temp temp4 = new Temp();
        Temp temp5 = new Temp();
        model.addAttribute("temp1", temp1);
        model.addAttribute("temp2",temp2);
        model.addAttribute("temp3",temp3);
        model.addAttribute("temp4",temp4);
        model.addAttribute("temp5",temp5);
        return "temp";
    }


    public byte[] saveTest(Temp temp1,
                            Temp temp2,
                            Temp temp3,
                            Temp temp4,
                            Temp temp5,
                            Temp firstLineTemp,
                            Temp secondLineTemp,
                            Temp thirdLineTemp,
                            Temp dateColomnTemp,
                            Temp nameFieldTemp,
                            Temp dateFieldTemp,
                            Temp generalTemp) {
        //System.out.println(temp4.getBold());
        //Необходим особый стиль для заглавной страницы?

        ParagraphParams firstParagraph = new ParagraphParams(temp1);
        ParagraphParams secondParagraph = new ParagraphParams(temp2);
        ParagraphParams thirdParagraph = new ParagraphParams(temp3);
        ParagraphParams fourthParagraph = new ParagraphParams(temp4);
        ParagraphParams fifthParagraph = new ParagraphParams(temp5);
        List<ParagraphParams> paragraphParamsList = Arrays.asList(firstParagraph, secondParagraph,
                thirdParagraph, fourthParagraph, fifthParagraph);


        TitleParams titleParams = new TitleParams(1, firstLineTemp, secondLineTemp,
                thirdLineTemp, dateColomnTemp, nameFieldTemp, dateFieldTemp);

        TempParams tempParams = new TempParams(generalTemp);

        TableParams tableParams = new TableParams(generalTemp);


        TemplateCreater templateCreater = new TemplateCreater();
        File file;
        FileInputStream fis;
        byte[] bytes = null;
        try {
            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
            file = new File("Template.docx");
            fis = new FileInputStream(file);
            bytes = IOUtils.toByteArray(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
        //FileInputStream fis = new FileInputStream(file);
//        return (//TempParams tempParams, TitleParams titleParams, List< ParagraphParams > paragraphParamsList, TableParams
//        tableParams
        //return "tempresult";
    }

    @PostMapping(value = "/temp", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    @ResponseBody
    public byte[] saveBooks(@ModelAttribute("temp1") Temp temp1,
                            @ModelAttribute("temp2") Temp temp2,
                            @ModelAttribute("temp3") Temp temp3,
                            @ModelAttribute("temp4") Temp temp4,
                            @ModelAttribute("temp5") Temp temp5,
                            @ModelAttribute("firstLine") Temp firstLineTemp,
                            @ModelAttribute("secondLine") Temp secondLineTemp,
                            @ModelAttribute("thirdLine") Temp thirdLineTemp,
                            @ModelAttribute("dateColomn") Temp dateColomnTemp,
                            @ModelAttribute("nameField") Temp nameFieldTemp,
                            @ModelAttribute("dateField") Temp dateFieldTemp,
                            @ModelAttribute("generalTemp") Temp generalTemp) {
        //System.out.println(temp4.getBold());
        //Необходим особый стиль для заглавной страницы?

        ParagraphParams firstParagraph = new ParagraphParams(temp1);
        ParagraphParams secondParagraph = new ParagraphParams(temp2);
        ParagraphParams thirdParagraph = new ParagraphParams(temp3);
        ParagraphParams fourthParagraph = new ParagraphParams(temp4);
        ParagraphParams fifthParagraph = new ParagraphParams(temp5);
        List<ParagraphParams> paragraphParamsList = Arrays.asList(firstParagraph, secondParagraph,
                thirdParagraph, fourthParagraph, fifthParagraph);


        TitleParams titleParams = new TitleParams(1, firstLineTemp, secondLineTemp,
                thirdLineTemp, dateColomnTemp, nameFieldTemp, dateFieldTemp);

        TempParams tempParams = new TempParams(generalTemp);

        TableParams tableParams = new TableParams(generalTemp);


        TemplateCreater templateCreater = new TemplateCreater();
        File file;
        FileInputStream fis;
        byte[] bytes = null;
        try {
            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
            file = new File("Empty.docx");
            fis = new FileInputStream(file);
            bytes = IOUtils.toByteArray(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
        //FileInputStream fis = new FileInputStream(file);
//        return (//TempParams tempParams, TitleParams titleParams, List< ParagraphParams > paragraphParamsList, TableParams
//        tableParams
        //return "tempresult";
    }




}
