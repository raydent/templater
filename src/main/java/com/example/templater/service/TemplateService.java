package com.example.templater.service;

import com.example.templater.model.*;
import com.example.templater.repo.Temp_FullRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Transactional
public class TemplateService {
    @Autowired
    private Temp_FullRepository temp_fullRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private int[] defaultTemplateIds = {0, 11};

    public Temp_Full saveTemplate(Temp_Full template) {
        entityManager.persist(template);
        return template;
    }
    public Temp_Full getById(int id){
        return temp_fullRepository.findTemp_FullById(id);
        //return temp_fullRepository.findTemp_FullByU();
    }

    public Temp_Full getByName(String name){
        return temp_fullRepository.findTemp_FullByName(name);
    }
    public List<String> getTemplateNamesList(User user, User manager){
        //return temp_fullRepository.findAllByUser(user);
        List<Temp_Full> userTemplates = user.getTemp_FullList();
        //Department department = user.getDepartment();
        if (manager != null && !manager.equals(user)) {
            List<Temp_Full> managerTemplates = manager.getTemp_FullList();
            userTemplates = Stream.concat(userTemplates.stream(), managerTemplates.stream()).collect(Collectors.toList());
        }
        List<String> templateNames = new ArrayList<>();
        for (int i : defaultTemplateIds){
            Temp_Full temp_full = getById(i);
            if (temp_full != null) {
                templateNames.add(getById(i).getName());
            }
            else{
                System.out.println("No default id: " + i + "Current queries are written in /src/main/resources/querys.txt");
            }
        }
        for (Temp_Full temp_full : userTemplates){
            templateNames.add(temp_full.getName());
        }
        return templateNames;
    }
    public boolean checkNameUniqueness(String name, User user, User manager){
        List<String> templateNames = getTemplateNamesList(user, manager);
        for (String tempName : templateNames){
            if (name.equals(tempName)){
                return false;
            }
        }
        return true;
    }
}
