package com.example.templater.model;

public class Temp {

    private long id;
    private String content;


    private String header_style;
    private String font;
    private String font_size;
    private String interval;

    private String bold;
    private String italic;
    private String underline;
    private String striken;

    private String alignment;
    private String color;
    private String fields;

    private String header;
    private String footer;
    private String title_page;
    private String numeration;



    private String heading_cell_text_color;
    private String Heading_cell_color;
    private String cell_border_color;
    private String common_cell_color;

    private String t_font;
    private String t_font_size;

    private String t_bold;
    private String t_italic;
    private String t_underline;
    private String t_striken;

    public Temp() {
    }

    public Temp(int t){
        id = 1;


        font = "Times_New_Roman";
        font_size = "14";
        interval = "1.0";

        bold = "0";
        italic = "0";
        underline = "0";
        striken = "0";

        alignment = "LEFT";
        color = "black";
        fields = "average";

        header = "0";
        footer = "0";
        title_page = "0";
        numeration = "0";



        heading_cell_text_color = "black";
        Heading_cell_color = "white";
        cell_border_color = "black";
        common_cell_color = "white";

        t_font = "Times_New_Roman";
        t_font_size = "14";

        t_bold = "1";
        t_italic = "1";
        t_underline = "1";
        t_striken = "1";
    }

    public String getHeader_style() {
        return header_style;
    }

    public void setHeader_style(String header_style) {
        this.header_style = header_style;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getFont_size() {
        return font_size;
    }

    public void setFont_size(String font_size) {
        this.font_size = font_size;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getBold() {
        return bold;
    }

    public void setBold(String bold) {
        this.bold = bold;
    }

    public String getItalic() {
        return italic;
    }

    public void setItalic(String italic) {
        this.italic = italic;
    }

    public String getUnderline() {
        return underline;
    }

    public void setUnderline(String underline) {
        this.underline = underline;
    }

    public String getStriken() {
        return striken;
    }

    public void setStriken(String striken) {
        this.striken = striken;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
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

    public String getHeading_cell_text_color() {
        return heading_cell_text_color;
    }

    public void setHeading_cell_text_color(String heading_cell_text_color) {
        this.heading_cell_text_color = heading_cell_text_color;
    }

    public String getHeading_cell_color() {
        return Heading_cell_color;
    }

    public void setHeading_cell_color(String heading_cell_color) {
        Heading_cell_color = heading_cell_color;
    }

    public String getCell_border_color() {
        return cell_border_color;
    }

    public void setCell_border_color(String cell_border_color) {
        this.cell_border_color = cell_border_color;
    }

    public String getCommon_cell_color() {
        return common_cell_color;
    }

    public void setCommon_cell_color(String common_cell_color) {
        this.common_cell_color = common_cell_color;
    }

    public String getT_font() {
        return t_font;
    }

    public void setT_font(String t_font) {
        this.t_font = t_font;
    }

    public String getT_font_size() {
        return t_font_size;
    }

    public void setT_font_size(String t_font_size) {
        this.t_font_size = t_font_size;
    }

    public String getT_bold() {
        return t_bold;
    }

    public void setT_bold(String t_bold) {
        this.t_bold = t_bold;
    }

    public String getT_italic() {
        return t_italic;
    }

    public void setT_italic(String t_italic) {
        this.t_italic = t_italic;
    }

    public String getT_underline() {
        return t_underline;
    }

    public void setT_underline(String t_underline) {
        this.t_underline = t_underline;
    }

    public String getT_striken() {
        return t_striken;
    }

    public void setT_striken(String t_striken) {
        this.t_striken = t_striken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}