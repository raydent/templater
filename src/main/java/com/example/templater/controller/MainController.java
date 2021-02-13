package com.example.templater.controller;


import com.example.templater.model.Temp_Full;
import com.example.templater.model.User;
import com.example.templater.service.IUserService;
import com.example.templater.service.TemplateService;
import com.example.templater.documentService.tempBuilder.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private TemplateService templateService;

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




    @GetMapping("/temp")
    public String tempForm(Model model) {
        Temp_Full temp = new Temp_Full();
        model.addAttribute("temp", temp);
        return "temp";
    }


    @PostMapping(value = "/temp", params = "save")
    public void saveTemplate(@ModelAttribute("temp") Temp_Full temp,
                             Authentication authentication){
        temp.replaceCheckboxNulls();
        String username = authentication.getName();
        User user = userService.getUserByName(username);
        temp.fillAllDBParams(user);
        user.addTemp_Full(temp);
        userService.saveUserUnsafe(user);
        //templateService.saveTemplate(temp);
    }

    //function for testing selecting templates from db
    @PostMapping(value = "/temp", params = "get")
    public void getTemplates(@ModelAttribute("temp") Temp_Full temp,
                             Authentication authentication){
        String username = authentication.getName();
        List<Temp_Full> temps = userService.getTemplatesListByName(username);
        System.out.println(temps.size());
        System.out.println(temps.get(1).getTable().getTable_cell_border_color());
    }

    @PostMapping(value = "/temp", params = "download", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    @ResponseBody
    public byte[] downloadTemplate(@ModelAttribute("temp") Temp_Full temp,
                            Authentication authentication) {

        temp.replaceCheckboxNulls();
        ParagraphParams firstParagraph = new ParagraphParams(temp, 1);
        ParagraphParams secondParagraph = new ParagraphParams(temp, 2);
        ParagraphParams thirdParagraph = new ParagraphParams(temp, 3);
        ParagraphParams fourthParagraph = new ParagraphParams(temp, 4);
        ParagraphParams fifthParagraph = new ParagraphParams(temp, 5);
        ParagraphParams textField = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, "none", "000000");
        List<ParagraphParams> paragraphParamsList = Arrays.asList(firstParagraph, secondParagraph,
                thirdParagraph, fourthParagraph, fifthParagraph, null, null, textField);


        TitleParams titleParams = new TitleParams(temp);

        TempParams tempParams = new TempParams(temp);

        TableParams tableParams = new TableParams(temp);


        TemplateCreater templateCreater = new TemplateCreater();
        File file;
        FileInputStream fis;
        byte[] bytes = null;
        try {
            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
            file = new File("Template.docx");
            fis = new FileInputStream(file);
            bytes = IOUtils.toByteArray(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }




}