package com.example.templater.tempBuilder;


import com.example.templater.model.Temp_Full;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParagraphParams {
    private Fonts font = Fonts.Calibri;
    private Integer fontSize = 14;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private ParagraphAlignment alignment = ParagraphAlignment.LEFT;
    private String textHighlightColor;
    private String textColor = Colors.getColorCode(Colors.black);

    public ParagraphParams() {}

    public ParagraphParams(Fonts font, Integer fontSize, boolean bold, boolean italic, boolean underline,
                           ParagraphAlignment alignment, String textHighlightColor, String textColor) {
        this.font = font;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.alignment = alignment;
        this.textHighlightColor = textHighlightColor;
        this.textColor = textColor;
    }

    public ParagraphParams(String font, String fontSize, String bold, String italic, String underline,
                           String alignment, String textHighlightColor, String textColor) {
        this.font = Fonts.valueOf(font);
        this.fontSize = Integer.valueOf(fontSize);
        this.bold = bold.equals("1");
        this.italic = italic.equals("1");
        this.underline = underline.equals("1");
        this.alignment = ParagraphAlignment.valueOf(alignment);
        this.textHighlightColor = textHighlightColor;
        this.textColor = textColor;
    }

    public ParagraphParams(Temp_Full temp, int headerNum){
        switch (headerNum){
            case(1):
                this.font = Fonts.valueOf(temp.getH1_font());
                this.fontSize = Integer.valueOf(temp.getH1_font_size());
                this.bold = temp.getH1_bold().equals("1");
                this.italic = temp.getH1_italic().equals("1");
                this.underline = temp.getH1_underline().equals("1");
                this.alignment = ParagraphAlignment.valueOf(temp.getH1_alignment());
                this.textHighlightColor = temp.getH1_text_highlight_color().substring(1);
                this.textColor = temp.getH1_text_color().substring(1);
                break;
            case(2):
                this.font = Fonts.valueOf(temp.getH2_font());
                this.fontSize = Integer.valueOf(temp.getH2_font_size());
                this.bold = temp.getH2_bold().equals("1");
                this.italic = temp.getH2_italic().equals("1");
                this.underline = temp.getH2_underline().equals("1");
                this.alignment = ParagraphAlignment.valueOf(temp.getH2_alignment());
                this.textHighlightColor = temp.getH2_text_highlight_color().substring(1);
                this.textColor = temp.getH2_text_color().substring(1);
                break;
            case(3):
                this.font = Fonts.valueOf(temp.getH3_font());
                this.fontSize = Integer.valueOf(temp.getH3_font_size());
                this.bold = temp.getH3_bold().equals("1");
                this.italic = temp.getH3_italic().equals("1");
                this.underline = temp.getH3_underline().equals("1");
                this.alignment = ParagraphAlignment.valueOf(temp.getH3_alignment());
                this.textHighlightColor = temp.getH3_text_highlight_color().substring(1);
                this.textColor = temp.getH3_text_color().substring(1);
                break;
            case(4):
                this.font = Fonts.valueOf(temp.getH4_font());
                this.fontSize = Integer.valueOf(temp.getH4_font_size());
                this.bold = temp.getH4_bold().equals("1");
                this.italic = temp.getH4_italic().equals("1");
                this.underline = temp.getH4_underline().equals("1");
                this.alignment = ParagraphAlignment.valueOf(temp.getH4_alignment());
                this.textHighlightColor = temp.getH4_text_highlight_color().substring(1);
                this.textColor = temp.getH4_text_color().substring(1);
                break;
            default:
                this.font = Fonts.valueOf(temp.getH5_font());
                this.fontSize = Integer.valueOf(temp.getH5_font_size());
                this.bold = temp.getH5_bold().equals("1");
                this.italic = temp.getH5_italic().equals("1");
                this.underline = temp.getH5_underline().equals("1");
                this.alignment = ParagraphAlignment.valueOf(temp.getH5_alignment());
                this.textHighlightColor = temp.getH5_text_highlight_color().substring(1);
                this.textColor = temp.getH5_text_color().substring(1);
        }
    }

    public Fonts getFont() {
        return font;
    }

    public void setFont(Fonts font) {
        this.font = font;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public ParagraphAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(ParagraphAlignment alignment) {
        this.alignment = alignment;
    }

    public String getTextHighlightColor() {
        return textHighlightColor;
    }

    public void setTextHighlightColor(String textHighlightColor) {
        this.textHighlightColor = textHighlightColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


    public static List<ParagraphParams> getDefaultTemp1ParParams() {
        ParagraphParams heading1 = new ParagraphParams(Fonts.Calibri, 20, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading2 = new ParagraphParams(Fonts.Calibri, 14, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading3 = new ParagraphParams(Fonts.Calibri, 11, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading4 = new ParagraphParams(Fonts.Calibri, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading5 = new ParagraphParams(Fonts.Calibri, 11, false, true, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams textField = new ParagraphParams(Fonts.Calibri, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        List<ParagraphParams> list = new ArrayList<ParagraphParams>();
        list.add(heading1);
        list.add(heading2);
        list.add(heading3);
        list.add(heading4);
        list.add(heading5);
        list.add(null); //header
        list.add(null); //footer
        list.add(textField);
        return list;
    }

    public static List<ParagraphParams> getDefaultTemp2ParParams() {
        ParagraphParams heading1 = new ParagraphParams(Fonts.Calibria, 14, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.blue), Colors.getColorCode(Colors.blue));
        ParagraphParams heading2 = new ParagraphParams(Fonts.Calibria, 12, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.blue), Colors.getColorCode(Colors.blue));
        ParagraphParams heading3 = new ParagraphParams(Fonts.Calibria, 11, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams heading4 = new ParagraphParams(Fonts.Calibria, 11, true, true, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.steel_blue), Colors.getColorCode(Colors.steel_blue));
        ParagraphParams heading5 = new ParagraphParams(Fonts.Calibria, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.blue), Colors.getColorCode(Colors.blue));
        ParagraphParams footer = new ParagraphParams(Fonts.Calibria, 11, false, false, false,
                ParagraphAlignment.CENTER, Colors.getColorCode(Colors.gray), Colors.getColorCode(Colors.gray));
        ParagraphParams textField = new ParagraphParams(Fonts.Calibria, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        List<ParagraphParams> list = new ArrayList<ParagraphParams>();
        list.add(heading1);
        list.add(heading2);
        list.add(heading3);
        list.add(heading4);
        list.add(heading5);
        list.add(null);
        list.add(null);
        list.add(textField);
        return list;
    }

    public static List<ParagraphParams> getDefaultTemp3ParParams() {
        ParagraphParams heading1 = new ParagraphParams(Fonts.Arial, 18, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading2 = new ParagraphParams(Fonts.Arial, 16, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading3 = new ParagraphParams(Fonts.Arial, 14, true, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading4 = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams heading5 = new ParagraphParams(Fonts.Arial, 14, false, true, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        ParagraphParams textField = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.getColorCode(Colors.black), Colors.getColorCode(Colors.black));
        List<ParagraphParams> list = new ArrayList<ParagraphParams>();
        list.add(heading1);
        list.add(heading2);
        list.add(heading3);
        list.add(heading4);
        list.add(heading5);
        list.add(null); //header
        list.add(null); //footer
        list.add(textField);
        return list;
    }

}