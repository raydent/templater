package com.example.templater.controller;

import com.example.templater.documentService.docCombine.DocCombiner;
import com.example.templater.documentService.docCombine.HeadingsCorrection;
import com.example.templater.documentService.docCombine.MainHeadingInfo;
import com.example.templater.documentService.docCombine.MatchedHeadingInfo;
import com.example.templater.documentService.tempBuilder.*;
import com.example.templater.documentService.tempParamsGetter.AllTempParams;
import com.example.templater.model.*;
import com.example.templater.service.FileService;
import com.example.templater.service.IUserService;
//import com.example.templater.tempBuilder.*;
import com.example.templater.service.TemplateService;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private IUserService userService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private FileService fileService;
    //@Autowired
    private AuthenticationManager authenticationManager;
    Map<String, List<File>> userFilesToCombine = new ConcurrentHashMap<>();
    Map<String, AllTempParams> userStyles = new ConcurrentHashMap<>();
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

    @PostMapping("/check_name_uniqueness_angular")
    @ResponseBody
    public String checkTemplateNameUniqueness(@RequestBody String json, Authentication authentication){
        JSONObject jsonObject = new JSONObject(json);
        String name = jsonObject.get("Name").toString();
        User user = userService.getUserByName(authentication.getName());
        User manager = user.getDepartment() != null ? userService.getUserById(user.getDepartment().getManagerId()) : null;
        if  (templateService.checkNameUniqueness(name, user, manager)){
            return "{\"Success\": \"YES\"}";
        }
        return "{\"Success\": \"NO\"}";
    }

    @PostMapping("/upload_angular")
    public @ResponseBody List<MainHeadingInfo> handleFileUpload(@RequestPart("files") MultipartFile[] multipartFiles, Authentication authentication) {
        List<File> oldFiles = userFilesToCombine.get(authentication.getName());
        if (oldFiles != null){
            oldFiles.stream().forEach(file -> file.delete());
        }
        fileService.multipartFilesToFileMap(userFilesToCombine, authentication.getName(), multipartFiles);
        try {
            DocCombiner dc = new DocCombiner();
            XWPFDocument result = dc.combineDocs(userFilesToCombine.get(authentication.getName()),
                    null, true);
            System.out.println(dc.getMainHeadingsInfo());
            return dc.getMainHeadingsInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/combine_angular", method = RequestMethod.POST)
    public
    @ResponseBody byte[]
    combineDocs(@RequestBody List<HeadingsCorrection> correctionList, Authentication authentication){
        System.out.println(userStyles.get(authentication.getName()));
        byte[] bytes = null;
        bytes = fileService.combineFiles(userFilesToCombine.get(authentication.getName()), correctionList, userStyles.get(authentication.getName()));
        //userFilesToCombine.get(authentication.getName()).stream().forEach(file -> file.delete());
        return bytes;
    }

    @PostMapping("get_style_angular")
    public @ResponseBody String
    getStyleAngular(@RequestBody Temp_Full temp_full, Authentication authentication){
        AllTempParams allTempParams = new AllTempParams(temp_full);
        userStyles.put(authentication.getName(), allTempParams);
        return "{\"STATUS\" : \"OK\"}";
    }

    @PostMapping("delete_style_angular")
    public @ResponseBody String
    deleteStyleAngular(Authentication authentication){
        userStyles.remove(authentication.getName());
        return "{\"STATUS\" : \"OK\"}";
    }

    /*@PostMapping("style_file_angular")
    public @ResponseBody byte[]
    styleFileAngular(@RequestPart("file") MultipartFile multipartFile, Authentication authentication){
        return fileService.applyStyle(fileService.multipartFileToFile(multipartFile, authentication.getName(), "style"),
                userStyles.get(authentication.getName()));
    }*/


    @RequestMapping(value = "/save_angular", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody String
    saveTemplateAngular(@RequestBody Temp_Full temp_full, Authentication authentication, HttpServletRequest httpServletRequest){
        User user = userService.getUserByName(authentication.getName());
        //System.out.println(temp_full.toString());
        temp_full.fillAllDBParams(user);
        user.addTemp_Full(temp_full);
        userService.saveUserUnsafe(user);
        return "{\"username\" : \"angular\"}";
    }

    @RequestMapping(value = "/get_template_angular", method = RequestMethod.POST)
    public @ResponseBody
    Temp_Full getTemplateAngular(@RequestBody String json, HttpServletRequest httpServletRequest, Authentication authentication){
        JSONObject jsonObject = new JSONObject(json);
        System.out.println(json);
        String name = jsonObject.get("Name").toString();
        if (name.equals("")){
            return null;
        }
        System.out.println(name);
        User user = userService.getUserByName(authentication.getName());
        Temp_Full temp_full = templateService.getByName(name);
        if (temp_full == null){
            return null;
        }
        temp_full.cutRecursiveReferences();
        return temp_full;
    }

    @RequestMapping(value = "/get_templates_angular", method = RequestMethod.POST)
    public @ResponseBody
    List<String> getTemplatesAngular(@RequestBody String json, HttpServletRequest httpServletRequest, Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        User manager = user.getDepartment() != null ? userService.getUserById(user.getDepartment().getManagerId()) : null;
        return templateService.getTemplateNamesList(user, manager);
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

    @RequestMapping(value = "/get_department_angular", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String getDepartmentAngular(HttpServletRequest httpServletRequest, Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        return "{\"Department name\" : \"" + (user.getDepartment() != null ? user.getDepartment().getDepartmentName() : "No department") + "\"}";
    }

    @RequestMapping(value = "/download_angular", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public @ResponseBody
    byte[] downloadFile(@RequestBody Temp_Full temp_full, Authentication authentication) throws IOException {

        temp_full.replaceCheckboxNulls();
        //temp_full.setTitle_type("1"); // надо фиксить, проблемы фронта
        //temp_full.setFields("average");
        System.out.println(temp_full);
        ParagraphParams firstParagraph = new ParagraphParams(temp_full, 1);
        ParagraphParams secondParagraph = new ParagraphParams(temp_full, 2);
        ParagraphParams thirdParagraph = new ParagraphParams(temp_full, 3);
        ParagraphParams fourthParagraph = new ParagraphParams(temp_full, 4);
        ParagraphParams fifthParagraph = new ParagraphParams(temp_full, 5);
        ParagraphParams textField = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode("black"), Colors.getColorCode("black"));
        List<ParagraphParams> paragraphParamsList = Arrays.asList(firstParagraph, secondParagraph,
                thirdParagraph, fourthParagraph, fifthParagraph, null, null, textField);
        TitleParams titleParams = new TitleParams(temp_full);

        TempParams tempParams = new TempParams(temp_full);

        TableParams tableParams = new TableParams(temp_full);


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
        if ((manager.getDepartment().getManagerId().equals(manager.getId()))
                && (department == null || !department.getManagerId().equals(user.getId()))){
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
}
