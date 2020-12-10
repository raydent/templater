package com.example.templater.model;

import com.example.templater.tempBuilder.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import javax.swing.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Temp_Full {

    //For the title page
    private String title_type;
    private String title_name;
    private String title_organization;
    private String title_description;
    private String title_alignment;

    //For the name
    private String title_name_font;
    private String title_name_font_size;
    private String title_name_bold;
    private String title_name_italic;
    private String title_name_underline;
    private String title_name_text_color;
    private String title_name_text_highlight_color;

    //For the organization
    private String title_organization_font;
    private String title_organization_font_size;
    private String title_organization_bold;
    private String title_organization_italic;
    private String title_organization_underline;
    private String title_organization_text_color;
    private String title_organization_text_highlight_color;

    //For the description
    private String title_description_font;
    private String title_description_font_size;
    private String title_description_bold;
    private String title_description_italic;
    private String title_description_underline;
    private String title_description_text_color;
    private String title_description_text_highlight_color;

    //For both types (date)/(date+name)
    private String title_type_font;
    private String title_type_font_size;
    private String title_type_bold;
    private String title_type_italic;
    private String title_type_underline;
    private String title_type_text_color;
    private String title_type_text_highlight_color;
    private String title_type_alignment;



    //For headers
    private String header;
    private String footer;
    private String title_page;
    private String numeration;
    private String interval;
    private String fields;

    //Header 1
    private String h1_font;
    private String h1_font_size;
    private String h1_bold;
    private String h1_italic;
    private String h1_underline;
    private String h1_alignment;
    private String h1_text_color;
    private String h1_text_highlight_color;

    //Header 2
    private String h2_font;
    private String h2_font_size;
    private String h2_bold;
    private String h2_italic;
    private String h2_underline;
    private String h2_alignment;
    private String h2_text_color;
    private String h2_text_highlight_color;

    //Header 3
    private String h3_font;
    private String h3_font_size;
    private String h3_bold;
    private String h3_italic;
    private String h3_underline;
    private String h3_alignment;
    private String h3_text_color;
    private String h3_text_highlight_color;

    //Header 4
    private String h4_font;
    private String h4_font_size;
    private String h4_bold;
    private String h4_italic;
    private String h4_underline;
    private String h4_alignment;
    private String h4_text_color;
    private String h4_text_highlight_color;

    //Header 5
    private String h5_font;
    private String h5_font_size;
    private String h5_bold;
    private String h5_italic;
    private String h5_underline;
    private String h5_alignment;
    private String h5_text_color;
    private String h5_text_highlight_color;

    //For the table
    private String table_heading_cell_text_color;
    private String table_heading_cell_color;
    private String table_cell_border_color;
    private String table_common_cell_color;

    private String table_font;
    private String table_font_size;

    private String table_bold;
    private String table_italic;

    public void replaceCheckboxNulls(){
        if (title_page == null) title_page = "0";
        if (numeration == null) numeration = "0";
        if (footer == null) footer = "0";
        if (header == null) header = "0";
        if (title_name_bold == null) title_name_bold = "0";
        if (title_name_italic == null) title_name_italic = "0";
        if (title_name_underline == null) title_name_underline = "0";
        if (title_organization_bold == null) title_organization_bold = "0";
        if (title_organization_italic == null) title_organization_italic = "0";
        if (title_organization_underline == null) title_organization_underline = "0";
        if (title_description_bold == null) title_description_bold = "0";
        if (title_description_italic == null) title_description_italic = "0";
        if (title_description_underline == null) title_description_underline = "0";
        if (title_type_bold == null) title_type_bold = "0";
        if (title_type_italic == null) title_type_italic = "0";
        if (title_type_underline == null) title_type_underline = "0";
        if (h1_bold == null) h1_bold = "0";
        if (h1_italic == null) h1_italic = "0";
        if (h1_underline == null) h1_underline = "0";
        if (h2_bold == null) h2_bold = "0";
        if (h2_italic == null) h2_italic = "0";
        if (h2_underline == null) h2_underline = "0";
        if (h3_bold == null) h3_bold = "0";
        if (h3_italic == null) h3_italic = "0";
        if (h3_underline == null) h3_underline = "0";
        if (h4_bold == null) h4_bold = "0";
        if (h4_italic == null) h4_italic = "0";
        if (h4_underline == null) h4_underline = "0";
        if (h5_bold == null) h5_bold = "0";
        if (h5_italic == null) h5_italic = "0";
        if (h5_underline == null) h5_underline = "0";
        if (table_bold == null) table_bold = "0";
        if (table_italic == null) table_italic = "0";
    }
    public String getTitle_type() {
        return title_type;
    }

    public void setTitle_type(String title_type) {
        this.title_type = title_type;
    }

    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public String getTitle_organization() {
        return title_organization;
    }

    public void setTitle_organization(String title_organization) {
        this.title_organization = title_organization;
    }

    public String getTitle_description() {
        return title_description;
    }

    public void setTitle_description(String title_description) {
        this.title_description = title_description;
    }

    public String getTitle_alignment() {
        return title_alignment;
    }

    public void setTitle_alignment(String title_alignment) {
        this.title_alignment = title_alignment;
    }

    public String getTitle_name_font() {
        return title_name_font;
    }

    public void setTitle_name_font(String title_name_font) {
        this.title_name_font = title_name_font;
    }

    public String getTitle_name_font_size() {
        return title_name_font_size;
    }

    public void setTitle_name_font_size(String title_name_font_size) {
        this.title_name_font_size = title_name_font_size;
    }

    public String getTitle_name_bold() {
        return title_name_bold;
    }

    public void setTitle_name_bold(String title_name_bold) {
        this.title_name_bold = title_name_bold;
    }

    public String getTitle_name_italic() {
        return title_name_italic;
    }

    public void setTitle_name_italic(String title_name_italic) {
        this.title_name_italic = title_name_italic;
    }

    public String getTitle_name_underline() {
        return title_name_underline;
    }

    public void setTitle_name_underline(String title_name_underline) {
        this.title_name_underline = title_name_underline;
    }

    public String getTitle_name_text_color() {
        return title_name_text_color;
    }

    public void setTitle_name_text_color(String title_name_text_color) {
        this.title_name_text_color = title_name_text_color;
    }

    public String getTitle_name_text_highlight_color() {
        return title_name_text_highlight_color;
    }

    public void setTitle_name_text_highlight_color(String title_name_text_highlight_color) {
        this.title_name_text_highlight_color = title_name_text_highlight_color;
    }

    public String getTitle_organization_font() {
        return title_organization_font;
    }

    public void setTitle_organization_font(String title_organization_font) {
        this.title_organization_font = title_organization_font;
    }

    public String getTitle_organization_font_size() {
        return title_organization_font_size;
    }

    public void setTitle_organization_font_size(String title_organization_font_size) {
        this.title_organization_font_size = title_organization_font_size;
    }

    public String getTitle_organization_bold() {
        return title_organization_bold;
    }

    public void setTitle_organization_bold(String title_organization_bold) {
        this.title_organization_bold = title_organization_bold;
    }

    public String getTitle_organization_italic() {
        return title_organization_italic;
    }

    public void setTitle_organization_italic(String title_organization_italic) {
        this.title_organization_italic = title_organization_italic;
    }

    public String getTitle_organization_underline() {
        return title_organization_underline;
    }

    public void setTitle_organization_underline(String title_organization_underline) {
        this.title_organization_underline = title_organization_underline;
    }

    public String getTitle_organization_text_color() {
        return title_organization_text_color;
    }

    public void setTitle_organization_text_color(String title_organization_text_color) {
        this.title_organization_text_color = title_organization_text_color;
    }

    public String getTitle_organization_text_highlight_color() {
        return title_organization_text_highlight_color;
    }

    public void setTitle_organization_text_highlight_color(String title_organization_text_highlight_color) {
        this.title_organization_text_highlight_color = title_organization_text_highlight_color;
    }

    public String getTitle_description_font() {
        return title_description_font;
    }

    public void setTitle_description_font(String title_description_font) {
        this.title_description_font = title_description_font;
    }

    public String getTitle_description_font_size() {
        return title_description_font_size;
    }

    public void setTitle_description_font_size(String title_description_font_size) {
        this.title_description_font_size = title_description_font_size;
    }

    public String getTitle_description_bold() {
        return title_description_bold;
    }

    public void setTitle_description_bold(String title_description_bold) {
        this.title_description_bold = title_description_bold;
    }

    public String getTitle_description_italic() {
        return title_description_italic;
    }

    public void setTitle_description_italic(String title_description_italic) {
        this.title_description_italic = title_description_italic;
    }

    public String getTitle_description_underline() {
        return title_description_underline;
    }

    public void setTitle_description_underline(String title_description_underline) {
        this.title_description_underline = title_description_underline;
    }

    public String getTitle_description_text_color() {
        return title_description_text_color;
    }

    public void setTitle_description_text_color(String title_description_text_color) {
        this.title_description_text_color = title_description_text_color;
    }

    public String getTitle_description_text_highlight_color() {
        return title_description_text_highlight_color;
    }

    public void setTitle_description_text_highlight_color(String title_description_text_highlight_color) {
        this.title_description_text_highlight_color = title_description_text_highlight_color;
    }

    public String getTitle_type_font() {
        return title_type_font;
    }

    public void setTitle_type_font(String title_type_font) {
        this.title_type_font = title_type_font;
    }

    public String getTitle_type_font_size() {
        return title_type_font_size;
    }

    public void setTitle_type_font_size(String title_type_font_size) {
        this.title_type_font_size = title_type_font_size;
    }

    public String getTitle_type_bold() {
        return title_type_bold;
    }

    public void setTitle_type_bold(String title_type_bold) {
        this.title_type_bold = title_type_bold;
    }

    public String getTitle_type_italic() {
        return title_type_italic;
    }

    public void setTitle_type_italic(String title_type_italic) {
        this.title_type_italic = title_type_italic;
    }

    public String getTitle_type_underline() {
        return title_type_underline;
    }

    public void setTitle_type_underline(String title_type_underline) {
        this.title_type_underline = title_type_underline;
    }

    public String getTitle_type_text_color() {
        return title_type_text_color;
    }

    public void setTitle_type_text_color(String title_type_text_color) {
        this.title_type_text_color = title_type_text_color;
    }

    public String getTitle_type_text_highlight_color() {
        return title_type_text_highlight_color;
    }

    public void setTitle_type_text_highlight_color(String title_type_text_highlight_color) {
        this.title_type_text_highlight_color = title_type_text_highlight_color;
    }

    public String getTitle_type_alignment() {
        return title_type_alignment;
    }

    public void setTitle_type_alignment(String title_type_alignment) {
        this.title_type_alignment = title_type_alignment;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getTitle_page() {
        return title_page;
    }

    public void setTitle_page(String title_page) {
        this.title_page = title_page;
    }

    public String getNumeration() {
        return numeration;
    }

    public void setNumeration(String numeration) {
        this.numeration = numeration;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getH1_font() {
        return h1_font;
    }

    public void setH1_font(String h1_font) {
        this.h1_font = h1_font;
    }

    public String getH1_font_size() {
        return h1_font_size;
    }

    public void setH1_font_size(String h1_font_size) {
        this.h1_font_size = h1_font_size;
    }

    public String getH1_bold() {
        return h1_bold;
    }

    public void setH1_bold(String h1_bold) {
        this.h1_bold = h1_bold;
    }

    public String getH1_italic() {
        return h1_italic;
    }

    public void setH1_italic(String h1_italic) {
        this.h1_italic = h1_italic;
    }

    public String getH1_underline() {
        return h1_underline;
    }

    public void setH1_underline(String h1_underline) {
        this.h1_underline = h1_underline;
    }

    public String getH1_alignment() {
        return h1_alignment;
    }

    public void setH1_alignment(String h1_alignment) {
        this.h1_alignment = h1_alignment;
    }

    public String getH1_text_color() {
        return h1_text_color;
    }

    public void setH1_text_color(String h1_text_color) {
        this.h1_text_color = h1_text_color;
    }

    public String getH1_text_highlight_color() {
        return h1_text_highlight_color;
    }

    public void setH1_text_highlight_color(String h1_text_highlight_color) {
        this.h1_text_highlight_color = h1_text_highlight_color;
    }

    public String getH2_font() {
        return h2_font;
    }

    public void setH2_font(String h2_font) {
        this.h2_font = h2_font;
    }

    public String getH2_font_size() {
        return h2_font_size;
    }

    public void setH2_font_size(String h2_font_size) {
        this.h2_font_size = h2_font_size;
    }

    public String getH2_bold() {
        return h2_bold;
    }

    public void setH2_bold(String h2_bold) {
        this.h2_bold = h2_bold;
    }

    public String getH2_italic() {
        return h2_italic;
    }

    public void setH2_italic(String h2_italic) {
        this.h2_italic = h2_italic;
    }

    public String getH2_underline() {
        return h2_underline;
    }

    public void setH2_underline(String h2_underline) {
        this.h2_underline = h2_underline;
    }

    public String getH2_alignment() {
        return h2_alignment;
    }

    public void setH2_alignment(String h2_alignment) {
        this.h2_alignment = h2_alignment;
    }

    public String getH2_text_color() {
        return h2_text_color;
    }

    public void setH2_text_color(String h2_text_color) {
        this.h2_text_color = h2_text_color;
    }

    public String getH2_text_highlight_color() {
        return h2_text_highlight_color;
    }

    public void setH2_text_highlight_color(String h2_text_highlight_color) {
        this.h2_text_highlight_color = h2_text_highlight_color;
    }

    public String getH3_font() {
        return h3_font;
    }

    public void setH3_font(String h3_font) {
        this.h3_font = h3_font;
    }

    public String getH3_font_size() {
        return h3_font_size;
    }

    public void setH3_font_size(String h3_font_size) {
        this.h3_font_size = h3_font_size;
    }

    public String getH3_bold() {
        return h3_bold;
    }

    public void setH3_bold(String h3_bold) {
        this.h3_bold = h3_bold;
    }

    public String getH3_italic() {
        return h3_italic;
    }

    public void setH3_italic(String h3_italic) {
        this.h3_italic = h3_italic;
    }

    public String getH3_underline() {
        return h3_underline;
    }

    public void setH3_underline(String h3_underline) {
        this.h3_underline = h3_underline;
    }

    public String getH3_alignment() {
        return h3_alignment;
    }

    public void setH3_alignment(String h3_alignment) {
        this.h3_alignment = h3_alignment;
    }

    public String getH3_text_color() {
        return h3_text_color;
    }

    public void setH3_text_color(String h3_text_color) {
        this.h3_text_color = h3_text_color;
    }

    public String getH3_text_highlight_color() {
        return h3_text_highlight_color;
    }

    public void setH3_text_highlight_color(String h3_text_highlight_color) {
        this.h3_text_highlight_color = h3_text_highlight_color;
    }

    public String getH4_font() {
        return h4_font;
    }

    public void setH4_font(String h4_font) {
        this.h4_font = h4_font;
    }

    public String getH4_font_size() {
        return h4_font_size;
    }

    public void setH4_font_size(String h4_font_size) {
        this.h4_font_size = h4_font_size;
    }

    public String getH4_bold() {
        return h4_bold;
    }

    public void setH4_bold(String h4_bold) {
        this.h4_bold = h4_bold;
    }

    public String getH4_italic() {
        return h4_italic;
    }

    public void setH4_italic(String h4_italic) {
        this.h4_italic = h4_italic;
    }

    public String getH4_underline() {
        return h4_underline;
    }

    public void setH4_underline(String h4_underline) {
        this.h4_underline = h4_underline;
    }

    public String getH4_alignment() {
        return h4_alignment;
    }

    public void setH4_alignment(String h4_alignment) {
        this.h4_alignment = h4_alignment;
    }

    public String getH4_text_color() {
        return h4_text_color;
    }

    public void setH4_text_color(String h4_text_color) {
        this.h4_text_color = h4_text_color;
    }

    public String getH4_text_highlight_color() {
        return h4_text_highlight_color;
    }

    public void setH4_text_highlight_color(String h4_text_highlight_color) {
        this.h4_text_highlight_color = h4_text_highlight_color;
    }

    public String getH5_font() {
        return h5_font;
    }

    public void setH5_font(String h5_font) {
        this.h5_font = h5_font;
    }

    public String getH5_font_size() {
        return h5_font_size;
    }

    public void setH5_font_size(String h5_font_size) {
        this.h5_font_size = h5_font_size;
    }

    public String getH5_bold() {
        return h5_bold;
    }

    public void setH5_bold(String h5_bold) {
        this.h5_bold = h5_bold;
    }

    public String getH5_italic() {
        return h5_italic;
    }

    public void setH5_italic(String h5_italic) {
        this.h5_italic = h5_italic;
    }

    public String getH5_underline() {
        return h5_underline;
    }

    public void setH5_underline(String h5_underline) {
        this.h5_underline = h5_underline;
    }

    public String getH5_alignment() {
        return h5_alignment;
    }

    public void setH5_alignment(String h5_alignment) {
        this.h5_alignment = h5_alignment;
    }

    public String getH5_text_color() {
        return h5_text_color;
    }

    public void setH5_text_color(String h5_text_color) {
        this.h5_text_color = h5_text_color;
    }

    public String getH5_text_highlight_color() {
        return h5_text_highlight_color;
    }

    public void setH5_text_highlight_color(String h5_text_highlight_color) {
        this.h5_text_highlight_color = h5_text_highlight_color;
    }

    public String getTable_heading_cell_text_color() {
        return table_heading_cell_text_color;
    }

    public void setTable_heading_cell_text_color(String table_heading_cell_text_color) {
        this.table_heading_cell_text_color = table_heading_cell_text_color;
    }

    public String getTable_heading_cell_color() {
        return table_heading_cell_color;
    }

    public void setTable_heading_cell_color(String table_heading_cell_color) {
        this.table_heading_cell_color = table_heading_cell_color;
    }

    public String getTable_cell_border_color() {
        return table_cell_border_color;
    }

    public void setTable_cell_border_color(String table_cell_border_color) {
        this.table_cell_border_color = table_cell_border_color;
    }

    public String getTable_common_cell_color() {
        return table_common_cell_color;
    }

    public void setTable_common_cell_color(String table_common_cell_color) {
        this.table_common_cell_color = table_common_cell_color;
    }

    public String getTable_font() {
        return table_font;
    }

    public void setTable_font(String table_font) {
        this.table_font = table_font;
    }

    public String getTable_font_size() {
        return table_font_size;
    }

    public void setTable_font_size(String table_font_size) {
        this.table_font_size = table_font_size;
    }

    public String getTable_bold() {
        return table_bold;
    }

    public void setTable_bold(String table_bold) {
        this.table_bold = table_bold;
    }

    public String getTable_italic() {
        return table_italic;
    }

    public void setTable_italic(String table_italic) {
        this.table_italic = table_italic;
    }
}