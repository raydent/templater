package com.example.templater.documentService.tempBuilder;


import com.example.templater.model.Temp_Full;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class TableParams {
    private int rows = 2;
    private int coloms = 2;
    private List<BigInteger> width = Arrays.asList(BigInteger.valueOf(5000), BigInteger.valueOf(5000));
    private String headingCellTextColor = Colors.getColorCode(Colors.black);
    private Fonts headingTextFont = Fonts.Calibri;
    private boolean headingTextBold = false;
    private boolean headingTextItalic = false;
    private Integer headingTextFontSize = 11;
    private String headingCellColor = Colors.getColorCode(Colors.gray);
    private String borderColor = Colors.getColorCode(Colors.gray);
    private String CommonCellColor;

    public TableParams() {}

    public TableParams(int rows, int coloms, List<BigInteger> width, String headingCellTextColor, Fonts headingTextFont,
                       boolean headingTextBold, boolean headingTextItalic, Integer headingTextFontSize,
                       String headingCellColor, String borderColor, String commonCellColor) {
        this.rows = rows;
        this.coloms = coloms;
        this.width = width;
        this.headingCellTextColor = headingCellTextColor;
        this.headingTextFont = headingTextFont;
        this.headingTextBold = headingTextBold;
        this.headingTextItalic = headingTextItalic;
        this.headingTextFontSize = headingTextFontSize;
        this.headingCellColor = headingCellColor;
        this.borderColor = borderColor;
        CommonCellColor = commonCellColor;
    }

    public TableParams(Temp_Full temp){
        this.rows = 2;
        this.coloms = 2;
        this.width = Arrays.asList(BigInteger.valueOf(5000), BigInteger.valueOf(5000));
        this.headingCellTextColor = temp.getTable_heading_cell_text_color().substring(1);
        this.headingTextFont = Fonts.valueOf(temp.getTable_font());
        this.headingTextBold = temp.getTable_bold().equals("1");
        this.headingTextItalic = temp.getTable_italic().equals("1");
        this.headingTextFontSize = Integer.valueOf(temp.getTable_font_size());
        this.headingCellColor = temp.getTable_heading_cell_color().substring(1);
        this.borderColor = temp.getTable_cell_border_color().substring(1);
        this.CommonCellColor = temp.getTable_common_cell_color().substring(1);
    }

    public List<BigInteger> getWidth() {
        return width;
    }

    public void setWidth(List<BigInteger> width) {
        this.width = width;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColoms() {
        return coloms;
    }

    public void setColoms(int coloms) {
        this.coloms = coloms;
    }

    public String getHeadingCellTextColor() {
        return headingCellTextColor;
    }

    public void setHeadingCellTextColor(String headingCellTextColor) {
        this.headingCellTextColor = headingCellTextColor;
    }

    public Fonts getHeadingTextFont() {
        return headingTextFont;
    }

    public void setHeadingTextFont(Fonts headingTextFont) {
        this.headingTextFont = headingTextFont;
    }

    public boolean isHeadingTextBold() {
        return headingTextBold;
    }

    public void setHeadingTextBold(boolean headingTextBold) {
        this.headingTextBold = headingTextBold;
    }

    public boolean isHeadingTextItalic() {
        return headingTextItalic;
    }

    public void setHeadingTextItalic(boolean headingTextItalic) {
        this.headingTextItalic = headingTextItalic;
    }

    public Integer getHeadingTextFontSize() {
        return headingTextFontSize;
    }

    public void setHeadingTextFontSize(Integer headingTextFontSize) {
        this.headingTextFontSize = headingTextFontSize;
    }

    public String getHeadingCellColor() {
        return headingCellColor;
    }

    public void setHeadingCellColor(String headingCellColor) {
        this.headingCellColor = headingCellColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getCommonCellColor() {
        return CommonCellColor;
    }

    public void setCommonCellColor(String commonCellColor) {
        CommonCellColor = commonCellColor;
    }

    @Override
    public String toString() {
        return "TableParams{" +
                "rows=" + rows +
                ", coloms=" + coloms +
                /*", width=" + width +*/
                ", headingCellTextColor='" + headingCellTextColor + '\'' +
                ", headingTextFont=" + headingTextFont +
                ", headingTextBold=" + headingTextBold +
                ", headingTextItalic=" + headingTextItalic +
                ", headingTextFontSize=" + headingTextFontSize +
                ", headingCellColor='" + headingCellColor + '\'' +
                ", borderColor='" + borderColor + '\'' +
                ", CommonCellColor='" + CommonCellColor + '\'' +
                '}';
    }

    public static TableParams getDefaultTemplate1TableParams() {
        return new TableParams(2, 2, Arrays.asList(BigInteger.valueOf(5000), BigInteger.valueOf(5000)),
                Colors.getColorCode(Colors.black), Fonts.Calibri, true, false, 11,
                Colors.getColorCode(Colors.gray), Colors.getColorCode(Colors.blue), Colors.getColorCode(Colors.gray));
    }
    public static TableParams getDefaultTemplate2TableParams() {
        return new TableParams(2, 2, Arrays.asList(BigInteger.valueOf(1500), BigInteger.valueOf(7000)),
                Colors.getColorCode(Colors.black), Fonts.Calibri, true, false, 11,
                Colors.getColorCode(Colors.gray), Colors.getColorCode(Colors.blue), Colors.getColorCode(Colors.gray));
    }
    public static TableParams getDefaultTemplate3TableParams() {
        return new TableParams(3, 3, Arrays.asList(BigInteger.valueOf(3500), BigInteger.valueOf(3500), BigInteger.valueOf(3500)),
                Colors.getColorCode(Colors.black), Fonts.Calibri, true, false, 11,
                Colors.getColorCode(Colors.gray), Colors.getColorCode(Colors.blue), Colors.getColorCode(Colors.gray));
    }
}