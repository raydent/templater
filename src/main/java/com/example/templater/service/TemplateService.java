package com.example.templater.service;

import com.example.templater.model.Header;
import com.example.templater.model.TempTable;
import com.example.templater.model.Temp_Full;
import com.example.templater.model.TitleHeader;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TemplateService {

    @PersistenceContext
    private EntityManager entityManager;
    public Temp_Full saveTemplate(Temp_Full template) {
        entityManager.persist(template);
        return template;
    }
    public Temp_Full getById(int userId){
        return entityManager.find(Temp_Full.class, userId);
    }
}
