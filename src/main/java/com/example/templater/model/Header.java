package com.example.templater.model;

import javax.persistence.*;

@Entity
@Table(name = "headers")
public class Header {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private int header_num;
    private String font;
    private String font_size;
    private String bold;
    private String italic;
    private String underline;
    private String alignment;
    private String text_color;
    private String text_highlight_color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "template_data_id")
    private Temp_Full temp_full;

    public Temp_Full getTemp_full() {
        return temp_full;
    }

    public void setTemp_full(Temp_Full temp_full) {
        this.temp_full = temp_full;
    }

    public Header() {

    }

    public Header(int num, String font, String font_size, String bold, String italic, String underline, String alignment, String text_color, String text_highlight_color) {
        this.header_num = num;
        this.font = font;
        this.font_size = font_size;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.alignment = alignment;
        this.text_color = text_color;
        this.text_highlight_color = text_highlight_color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeader_num() {
        return header_num;
    }

    public void setHeader_num(int num) {
        this.header_num = num;
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

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public String getText_highlight_color() {
        return text_highlight_color;
    }

    public void setText_highlight_color(String text_highlight_color) {
        this.text_highlight_color = text_highlight_color;
    }
}
