package com.example.templater.documentService.tempBuilder;


import com.example.templater.model.Temp_Full;

public class TempParams {
    private boolean title_page = true;
    private boolean numeration = true;
    private boolean footer = false;
    private boolean header = false;
    private double interval_between_lines = 1.15;
    private Fields field = Fields.average;

    public TempParams() {}

    public TempParams(boolean title_page, boolean numeration, boolean footer, boolean header, double interval_between_lines, Fields field) {
        this.title_page = title_page;
        this.numeration = numeration;
        this.footer = footer;
        this.header = header;
        this.interval_between_lines = interval_between_lines;
        this.field = field;
    }

    public TempParams(Temp_Full temp){
        this.title_page = temp.getTitle_page().equals("1");
        this.numeration = temp.getNumeration().equals("1");
        this.footer = temp.getFooter().equals("1");
        this.header = temp.getHeader().equals("1");
        this.interval_between_lines = Double.valueOf(temp.getInterval());
        this.field = Fields.valueOf(temp.getFields());
    }

    public boolean isTitle_page() {
        return title_page;
    }

    public void setTitle_page(boolean title_page) {
        this.title_page = title_page;
    }

    public boolean isNumeration() {
        return numeration;
    }

    public void setNumeration(boolean numeration) {
        this.numeration = numeration;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public double getInterval_between_lines() {
        return interval_between_lines;
    }

    public void setInterval_between_lines(double interval_between_lines) {
        this.interval_between_lines = interval_between_lines;
    }

    public Fields getField() {
        return field;
    }

    public void setField(Fields field) {
        this.field = field;
    }

    public static TempParams getDefaultTemp1Params() {
        return new TempParams(true, true, false, false, 1.15, Fields.narrow);
    }

    public static TempParams getDefaultTemp2Params() {
        return new TempParams(true, true, true, false, 1.15, Fields.average);
    }
    public static TempParams getDefaultTemp3Params() {
        return new TempParams(true, true, false, false, 1.15, Fields.average);
    }

    @Override
    public String toString() {
        return "TempParams{" +
                "title_page=" + title_page +
                ", numeration=" + numeration +
                ", footer=" + footer +
                ", header=" + header +
                ", interval_between_lines=" + interval_between_lines +
                ", field=" + field +
                '}';
    }
}