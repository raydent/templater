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

@Repository
@Transactional
public class TemplateService {

    @PersistenceContext
    private EntityManager entityManager;
    public Temp_Full saveTemplate(Temp_Full template) {
        template.fillHeaders();
        template.fillTable();
        template.fillTitleHeaders();
        template.replaceCheckboxNulls();
        template.getTable().setTemp_full(template);
        for (TitleHeader titleHeader : template.getTitle_headers()) {
            titleHeader.setTemp_full(template);
        }
        for (Header header : template.getHeaders()) {
            header.setTemp_full(template);
        }
        entityManager.persist(template);
        return template;
    }
}
