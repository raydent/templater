package com.example.templater.controller;

import com.example.templater.model.*;
import com.example.templater.service.IUserService;
//import com.example.templater.tempBuilder.*;
import org.apache.commons.compress.utils.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private IUserService userService;
    //@Autowired
    private AuthenticationManager authenticationManager;
    private List<MultipartFile> uploadedFiles = new ArrayList<>();
//test
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

    //@RequestMapping(value = "/upload_angular", method = RequestMethod.POST, produces = "application/json")
//    @PostMapping("/upload_angular")
//    public @ResponseBody
//    String fileUploadAngular(@RequestParam("file") MultipartFile file){
//        String message = "";
//        try {
//            //storageService.save(file);
//            System.out.println(file.getOriginalFilename());
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body((message)).toString();
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body((message)).toString();
//        }
//    }
    @PostMapping("/upload_angular")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file) {
        String message;
        System.out.println(file.getOriginalFilename());

        uploadedFiles.add(file);
        System.out.println(uploadedFiles.size());
        try {
            try {
                Path path = Paths.get("C:\\files\\");
                //Files.copy(file.getInputStream(), path.resolve("file_name.docx"));
            } catch (Exception e) {
                throw new RuntimeException("FAIL!");
            }
            //files.add(file.getOriginalFilename());

            message = "Successfully uploaded!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @RequestMapping(value = "/save_angular", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String saveTemplateAngular(@RequestBody String json, HttpServletRequest httpServletRequest){
        System.out.println("Json" + json);
        return "{\"username\" : \"angular\"}";
    }

    @RequestMapping(value = "/get_template_angular", method = RequestMethod.POST)
    public @ResponseBody
    Temp_Full getTemplateAngular(@RequestBody String json, HttpServletRequest httpServletRequest, Authentication authentication){
        JSONObject jsonObject = new JSONObject(json);
        String stringId = jsonObject.get("Id").toString();
        if (stringId.equals("")){
            return null;
        }
        int id = Integer.valueOf(stringId);
        System.out.println(id);
        User user = userService.getUserByName(authentication.getName());
        if (user != null){
            Temp_Full temp_full = null;
            for (Temp_Full temp : user.getTemp_FullList()){
                if (temp.getId() == id){
                    temp_full = temp;
                }
            }
            if (temp_full == null){
                return null;
            }
            temp_full.setUser(null);
            for (Header header : temp_full.getHeaders()){
                header.setTemp_full(null);
            }
            for (TitleHeader titleHeader : temp_full.getTitle_headers()){
                titleHeader.setTemp_full(null);
            }
            temp_full.getTable().setTemp_full(null);
            return temp_full;
        }
        return null;
    }

    @RequestMapping(value = "/get_templates_angular", method = RequestMethod.POST)
    public @ResponseBody
    List<Temp_Full> getTemplatesAngular(@RequestBody String json, HttpServletRequest httpServletRequest, Authentication authentication){
        List<Temp_Full> templates = userService.getUserByName(authentication.getName()).getTemp_FullList();
        for (Temp_Full temp_full : templates){
            temp_full.setUser(null);
            for (Header header : temp_full.getHeaders()){
                header.setTemp_full(null);
            }
            for (TitleHeader titleHeader : temp_full.getTitle_headers()){
                titleHeader.setTemp_full(null);
            }
            temp_full.getTable().setTemp_full(null);
        }
        return templates;
    }

    @RequestMapping(value = "/get_department_users_angular", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    List<String> getDepartmentUsersAngular(HttpServletRequest httpServletRequest, Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        List<String> usernames = new ArrayList<>();
        if (user.getDepartment() != null){
            List<User> users = user.getDepartment().getUsers();
            for (User u : users){
               usernames.add(u.getUsername());
            }
            return usernames;
        }
        return null;
    }

    @RequestMapping(value = "/download_angular", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public @ResponseBody
    byte[] downloadFile() throws IOException {
        File file = new File("C:\\files\\" + "1" + ".docx");
        FileInputStream fis = new FileInputStream(file);
        System.out.println("File return");
        return IOUtils.toByteArray(fis);
    }

    @GetMapping("/department") //admin's functional to create department
    public String getCreateDepartment(Model model) {
        Department d = new Department();
        model.addAttribute("departmentForm", d);
        return "department";
    }

    @PostMapping("/department")
    public String addUserToDepartment(@ModelAttribute("department") Department department, Authentication authentication){
        Integer id = department.getManagerId();
        User manager = userService.getUserById(id);
        if (manager == null){
            return "department";
        }
        manager.setDepartment(department);
        manager.addRoles(new Role(3L, "ROLE_MANAGER"));
        userService.saveUserUnsafe(manager);
        return "redirect:/user";
    }


    @GetMapping("/managing")
    public String managingForm(Model model) {
        User usernameForm = new User();
        model.addAttribute("usernameForm", usernameForm);
        return "managing";
    }

    @PostMapping("/managing")
    public String addUserToDepartment(@ModelAttribute("usernameForm") User usernameForm, Authentication authentication){
        User manager = userService.getUserByName(authentication.getName());
        User user = userService.getUserByName(usernameForm.getUsername());
        if (user == null || manager == null){
            System.out.println(user + " " + manager);
            return "managing";
        }
        user.setDepartment(manager.getDepartment());
        userService.saveUserUnsafe(user);
        //departmentService.saveDepartment(manager.getDepartment());
        return "redirect:/user";
    }

    @RequestMapping(value = "/add_user_to_department_angular", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String addUserToDepartmentAngular(@RequestBody String json, HttpServletRequest httpServletRequest, Authentication authentication){
        User manager = userService.getUserByName(authentication.getName());
        JSONObject jsonObject = new JSONObject(json);
        User user = userService.getUserByName(jsonObject.get("username").toString());
        Department department = user.getDepartment();
        if ((manager.getDepartment().getManagerId().equals(manager.getId())) && (department == null || !department.getManagerId().equals(user.getId()))){
            user.setDepartment(manager.getDepartment());
            userService.saveUserUnsafe(user);
            return "{\"success\" : \"OK\"}";
        }
        return "{\"success\" : \"NO\"}";
    }

    @RequestMapping(value = "/delete_user_from_department_angular", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String deleteUserFromDepartmentAngular(@RequestBody String json, HttpServletRequest httpServletRequest, Authentication authentication){
        User manager = userService.getUserByName(authentication.getName());
        JSONObject jsonObject = new JSONObject(json);
        User user = userService.getUserByName(jsonObject.get("username").toString());
        Department department = user.getDepartment();
        if ((manager.getDepartment().getManagerId().equals(manager.getId())) && (department == null || !department.getManagerId().equals(user.getId()))){
            user.setDepartment(null);
            userService.saveUserUnsafe(user);
            return "{\"success\" : \"OK\"}";
        }
        return "{\"success\" : \"NO\"}";
    }

    @PostMapping(value = "/delete_from_department")
    String deleteUserFromDepartment(@ModelAttribute("usernameForm") User usernameForm, Authentication authentication){
        User user = userService.getUserByName(usernameForm.getUsername());
        user.setDepartment(null);
        userService.saveUserUnsafe(user);
        System.out.println("Success");
        return "redirect:/user";
    }

    @GetMapping(value = "get_department_users_list")
    String getUsersFromDepartment(Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        if (user != null && user.getDepartment() != null){
            System.out.println(user.getDepartment().getUsers());
        }
        return "redirect:/user";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about(){
        return "/about";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("userForm", new TestClass());
        return "/home";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("userForm", new TestClass());
        return "/test";
    }

    @PostMapping("/test")
    public String postTest(@ModelAttribute("userForm") @Valid TestClass userForm, BindingResult bindingResult, Model model) {
        userForm.setField1(userForm.getField() + "0");
        return "/test";
    }


    @GetMapping("/template")
    public String template() { return "/template";}

    //@CrossOrigin(origins = "http://127.0.0.1:4200")
    @GetMapping("/test_angular")
    public @ResponseBody String test(HttpServletRequest httpServletRequest) {
        String credentials = httpServletRequest.getHeader("authorization");
        String usernameAndPass = new String(Base64.getDecoder().decode(credentials.substring(credentials.indexOf(' ') + 1)));
        String username = usernameAndPass.substring(0, usernameAndPass.indexOf(':'));
        User user = userService.getUserByName(username);
        if (user.getDepartment() != null && user.getDepartment().getManagerId().equals(user.getId())){
            return "{\"username\" : \"angular\", \"manager\" : \"manager\"}";
        }
        return "{\"username\" : \"angular\"}";
    }

    @GetMapping("/token")
    public Map<String,String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @Bean
    HeaderHttpSessionStrategy sessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }


//    @PostMapping(value = "/download/{templateId}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
//    @ResponseBody
//    byte[] getFile(@PathVariable String templateId) throws IOException {
//        File file = new File(defaultDirectoryName + templateId + ".docx");
//        FileInputStream fis = new FileInputStream(file);
//        return IOUtils.toByteArray(fis);
//    }




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

        List<Temp_Full> temps = userService.getTemplatesListByName(username);//(username);
        System.out.println(temps.size());
        System.out.println(temps.get(1).getTable().getTable_cell_border_color());
    }

//    @PostMapping(value = "/temp", params = "download", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
//    @ResponseBody
//    public byte[] downloadTemplate(@ModelAttribute("temp") Temp_Full temp,
//                                   Authentication authentication) {
//
//        temp.replaceCheckboxNulls();
//        ParagraphParams firstParagraph = new ParagraphParams(temp, 1);
//        ParagraphParams secondParagraph = new ParagraphParams(temp, 2);
//        ParagraphParams thirdParagraph = new ParagraphParams(temp, 3);
//        ParagraphParams fourthParagraph = new ParagraphParams(temp, 4);
//        ParagraphParams fifthParagraph = new ParagraphParams(temp, 5);
//        ParagraphParams textField = new ParagraphParams(Fonts.Arial, 14, false, false, false,
//                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
//        List<ParagraphParams> paragraphParamsList = Arrays.asList(firstParagraph, secondParagraph,
//                thirdParagraph, fourthParagraph, fifthParagraph, null, null, textField);
//
//
//        TitleParams titleParams = new TitleParams(temp);
//
//        TempParams tempParams = new TempParams(temp);
//
//        TableParams tableParams = new TableParams(temp);
//
//
//        TemplateCreater templateCreater = new TemplateCreater();
//        File file;
//        FileInputStream fis;
//        byte[] bytes = null;
//        try {
//            templateCreater.createTemplate(tempParams, titleParams, paragraphParamsList, tableParams);
//            file = new File("Template.docx");
//            fis = new FileInputStream(file);
//            bytes = IOUtils.toByteArray(fis);
//            fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bytes;
//    }
}
