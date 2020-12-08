package com.example.templater.tempBuilder;

import com.example.templater.model.Temp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
//заглушка
public class TableParams {
    private int rows = 2;
    private int coloms = 2;
    private List<BigInteger> width = Arrays.asList(BigInteger.valueOf(5000), BigInteger.valueOf(5000));
    private Colors headingCellTextColor = Colors.black;
    private Fonts headingTextFont = Fonts.Calibri;
    private boolean headingTextBold = false;
    private boolean headingTextItalic = false;
    private Integer headingTextFontSize = 11;
    private Colors headingCellColor = Colors.gray;
    private Colors borderColor = Colors.gray;
    private Colors CommonCellColor = Colors.transparent;

    public TableParams() {}

    public TableParams(int rows, int coloms, List<BigInteger> width, Colors headingCellTextColor, Fonts headingTextFont,
                       boolean headingTextBold, boolean headingTextItalic, Integer headingTextFontSize,
                       Colors headingCellColor, Colors borderColor, Colors commonCellColor) {
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

    public TableParams(Temp temp){
        this.rows = 2;//Заплатка
        this.coloms = 2; // заплатка
        this.width = Arrays.asList(BigInteger.valueOf(5000), BigInteger.valueOf(5000)); // заплатка
        this.headingCellTextColor = Colors.valueOf(temp.getHeading_cell_text_color());
        this.headingTextFont = Fonts.valueOf(temp.getT_font());
        this.headingTextBold = temp.getT_bold().equals("1");
        this.headingTextItalic = temp.getT_italic().equals("1");
        this.headingTextFontSize = Integer.valueOf(temp.getT_font_size());
        this.headingCellColor = Colors.valueOf(temp.getHeading_cell_color());
        this.borderColor = Colors.valueOf(temp.getCell_border_color());
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

    public Colors getHeadingCellTextColor() {
        return headingCellTextColor;
    }

    public void setHeadingCellTextColor(Colors headingCellTextColor) {
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

    public Colors getHeadingCellColor() {
        return headingCellColor;
    }

    public void setHeadingCellColor(Colors headingCellColor) {
        this.headingCellColor = headingCellColor;
    }

    public Colors getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Colors borderColor) {
        this.borderColor = borderColor;
    }

    public Colors getCommonCellColor() {
        return CommonCellColor;
    }

    public void setCommonCellColor(Colors commonCellColor) {
        CommonCellColor = commonCellColor;
    }

    public static TableParams getDefaultTemplate1TableParams() {
        return new TableParams(2, 2, Arrays.asList(BigInteger.valueOf(5000), BigInteger.valueOf(5000)),
                Colors.black, Fonts.Calibri, true, false, 11,
                Colors.gray, Colors.blue, Colors.transparent);
    }
    public static TableParams getDefaultTemplate2TableParams() {
        return new TableParams(2, 2, Arrays.asList(BigInteger.valueOf(1500), BigInteger.valueOf(7000)),
                Colors.black, Fonts.Calibri, true, false, 11,
                Colors.gray, Colors.blue, Colors.transparent);
    }
    public static TableParams getDefaultTemplate3TableParams() {
        return new TableParams(3, 3, Arrays.asList(BigInteger.valueOf(3500), BigInteger.valueOf(3500), BigInteger.valueOf(3500)),
                Colors.black, Fonts.Calibri, true, false, 11,
                Colors.gray, Colors.blue, Colors.transparent);
    }
}