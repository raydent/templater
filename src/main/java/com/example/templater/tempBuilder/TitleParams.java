package com.example.templater.tempBuilder;

import com.example.templater.model.Temp_Full;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
//заглушка
public class TitleParams {
    private int type;
    private ParagraphParams firstLine;
    private ParagraphParams secondLine;
    private ParagraphParams thirdLine;
    private ParagraphParams dateColomn;
    private ParagraphParams nameField;
    private ParagraphParams dateField;

    public TitleParams() {}


    public TitleParams(int type, ParagraphParams firstLine, ParagraphParams secondLine, ParagraphParams thirdLine,
                       ParagraphParams dateColomn, ParagraphParams nameField, ParagraphParams dateField) {
        this.type = type;
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.thirdLine = thirdLine;
        this.dateColomn = dateColomn;
        this.nameField = nameField;
        this.dateField = dateField;
    }

    public TitleParams(Temp_Full temp){
        this.type = Integer.valueOf(temp.getTitle_type());
        switch (type){
            case(1):
                this.firstLine = new ParagraphParams(temp.getTitle_organization_font(),
                        temp.getTitle_organization_font_size(),
                        temp.getTitle_organization_bold(), temp.getTitle_organization_italic(),
                        temp.getTitle_organization_underline(), "LEFT", temp.getTitle_organization_text_highlight_color().substring(1),
                        temp.getTitle_organization_text_color().substring(1));
                this.secondLine = new ParagraphParams(temp.getTitle_name_font(),
                        temp.getTitle_name_font_size(),
                        temp.getTitle_name_bold(), temp.getTitle_name_italic(),
                        temp.getTitle_name_underline(), "LEFT", temp.getTitle_name_text_highlight_color().substring(1),
                        temp.getTitle_name_text_color().substring(1));
                this.thirdLine = new ParagraphParams(temp.getTitle_description_font(),
                        temp.getTitle_description_font_size(),
                        temp.getTitle_description_bold(), temp.getTitle_description_italic(),
                        temp.getTitle_description_underline(), "LEFT", temp.getTitle_description_text_highlight_color().substring(1),
                        temp.getTitle_description_text_color().substring(1));
                break;
            case (2):
            case (3):
                this.firstLine = new ParagraphParams(temp.getTitle_type_font(),
                        temp.getTitle_type_font_size(),
                        temp.getTitle_type_bold(), temp.getTitle_type_italic(),
                        temp.getTitle_type_underline(), "LEFT", temp.getTitle_type_text_highlight_color().substring(1),
                        temp.getTitle_type_text_color().substring(1));
                this.thirdLine = new ParagraphParams(temp.getTitle_name_font(),
                        temp.getTitle_name_font_size(),
                        temp.getTitle_name_bold(), temp.getTitle_name_italic(),
                        temp.getTitle_name_underline(), "LEFT", temp.getTitle_name_text_highlight_color().substring(1),
                        temp.getTitle_name_text_color().substring(1));
                this.secondLine = new ParagraphParams(temp.getTitle_description_font(),
                        temp.getTitle_description_font_size(),
                        temp.getTitle_description_bold(), temp.getTitle_description_italic(),
                        temp.getTitle_description_underline(), "LEFT", temp.getTitle_description_text_highlight_color().substring(1),
                        temp.getTitle_description_text_color().substring(1));
                break;

        }
        this.dateColomn = new ParagraphParams(temp.getTitle_type_font(),
                temp.getTitle_type_font_size(),
                temp.getTitle_type_bold(), temp.getTitle_type_italic(),
                temp.getTitle_type_underline(), "LEFT", temp.getTitle_type_text_highlight_color().substring(1),
                temp.getTitle_type_text_color().substring(1));
        this.nameField = new ParagraphParams(temp.getTitle_type_font(),
                temp.getTitle_type_font_size(),
                temp.getTitle_type_bold(), temp.getTitle_type_italic(),
                temp.getTitle_type_underline(), "LEFT", temp.getTitle_type_text_highlight_color().substring(1),
                temp.getTitle_type_text_color().substring(1));
        this.dateField = new ParagraphParams(temp.getTitle_type_font(),
                temp.getTitle_type_font_size(),
                temp.getTitle_type_bold(), temp.getTitle_type_italic(),
                temp.getTitle_type_underline(), "LEFT", temp.getTitle_type_text_highlight_color().substring(1),
                temp.getTitle_type_text_color().substring(1));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ParagraphParams getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(ParagraphParams firstLine) {
        this.firstLine = firstLine;
    }

    public ParagraphParams getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(ParagraphParams secondLine) {
        this.secondLine = secondLine;
    }

    public ParagraphParams getThirdLine() {
        return thirdLine;
    }

    public void setThirdLine(ParagraphParams thirdLine) {
        this.thirdLine = thirdLine;
    }

    public ParagraphParams getDateColomn() {
        return dateColomn;
    }

    public void setDateColomn(ParagraphParams dateColomn) {
        this.dateColomn = dateColomn;
    }

    public ParagraphParams getNameField() {
        return nameField;
    }

    public void setNameField(ParagraphParams nameField) {
        this.nameField = nameField;
    }

    public ParagraphParams getDateField() {
        return dateField;
    }

    public void setDateField(ParagraphParams dateField) {
        this.dateField = dateField;
    }

    public static TitleParams getDefaultTemp1TitleParams() {
        ParagraphParams paramsFirstLine = new ParagraphParams(Fonts.Calibri, 12, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams paramsSecondLine = new ParagraphParams(Fonts.Calibri, 44, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams paramsThirdLine = new ParagraphParams(Fonts.Calibri, 12, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams paramsNameField = new ParagraphParams(Fonts.Calibri, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams paramsDateField = new ParagraphParams(Fonts.Calibri, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        return new TitleParams(1, paramsFirstLine, paramsSecondLine, paramsThirdLine, null, paramsNameField, paramsDateField);
    }

    public static TitleParams getDefaultTemp2TitleParams() {
        ParagraphParams paramsFirstLine = new ParagraphParams(Fonts.Calibria, 36, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams paramsSecondLine = new ParagraphParams(Fonts.Calibria, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.aqua_dark), Colors.getColorCode(Colors.aqua_dark));
        ParagraphParams paramsThirdLine = new ParagraphParams(Fonts.Calibria, 12, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.aqua), Colors.getColorCode(Colors.aqua));
        ParagraphParams dateColomn = new ParagraphParams(Fonts.Calibria, 12, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.white), Colors.getColorCode(Colors.white));
        return new TitleParams(2, paramsFirstLine, paramsSecondLine, paramsThirdLine, dateColomn, null, null);
    }
    public static TitleParams getDefaultTemp3TitleParams() {
        ParagraphParams paramsFirstLine = new ParagraphParams(Fonts.Arial, 36, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams paramsSecondLine = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.aqua_dark), Colors.getColorCode(Colors.aqua_dark));
        ParagraphParams paramsThirdLine = new ParagraphParams(Fonts.Arial, 12, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.aqua), Colors.getColorCode(Colors.aqua));

        ParagraphParams dateColomn = new ParagraphParams(Fonts.Arial, 12, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.white), Colors.getColorCode(Colors.white));
        return new TitleParams(3, paramsFirstLine, paramsSecondLine, paramsThirdLine, dateColomn, null, null);
    }

}