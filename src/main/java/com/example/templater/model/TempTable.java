package com.example.templater.model;


import javax.persistence.*;

@Entity
@Table(name = "temp_table")
public class TempTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String table_heading_cell_text_color;
    private String table_heading_cell_color;
    private String table_cell_border_color;
    private String table_common_cell_color;

    private String table_font;
    private String table_font_size;

    private String table_bold;
    private String table_italic;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "template_data_id")
    private Temp_Full temp_full;

    public int getId() {
        return id;
    }

    public Temp_Full getTemp_full() {
        return temp_full;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTemp_full(Temp_Full temp_full) {
        this.temp_full = temp_full;
    }

    public TempTable() {
    }

    public TempTable(String table_heading_cell_text_color, String table_heading_cell_color, String table_cell_border_color, String table_common_cell_color, String table_font, String table_font_size, String table_bold, String table_italic) {
        this.table_heading_cell_text_color = table_heading_cell_text_color;
        this.table_heading_cell_color = table_heading_cell_color;
        this.table_cell_border_color = table_cell_border_color;
        this.table_common_cell_color = table_common_cell_color;
        this.table_font = table_font;
        this.table_font_size = table_font_size;
        this.table_bold = table_bold;
        this.table_italic = table_italic;
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

