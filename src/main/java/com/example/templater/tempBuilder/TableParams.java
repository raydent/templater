package com.example.templater.tempBuilder;

public class TableParams {
    private Colors headingCellTextColor = Colors.black;
    private Fonts headingTextFont = Fonts.Calibri;
    private boolean headingTextBold = false;
    private boolean headingTextItalic = false;
    private Integer headingTextFontSize = 11;
    private Colors headingCellColor = Colors.gray;
    private Colors borderColor = Colors.gray;
    private Colors CommonCellColor = Colors.transparent;

    public TableParams() {}

    public TableParams(Colors headingCellTextColor, Fonts headingTextFont, boolean headingTextBold,
                       boolean headingTextItalic, Integer headingTextFontSize, Colors headingCellColor,
                       Colors borderColor, Colors commonCellColor) {
        this.headingCellTextColor = headingCellTextColor;
        this.headingTextFont = headingTextFont;
        this.headingTextBold = headingTextBold;
        this.headingTextItalic = headingTextItalic;
        this.headingTextFontSize = headingTextFontSize;
        this.headingCellColor = headingCellColor;
        this.borderColor = borderColor;
        CommonCellColor = commonCellColor;
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
}
