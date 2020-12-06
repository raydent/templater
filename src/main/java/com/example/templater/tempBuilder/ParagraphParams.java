package com.example.templater.tempBuilder;

import com.example.templater.model.Temp;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import java.util.ArrayList;
import java.util.List;

public class ParagraphParams {
    private Fonts font = Fonts.Calibri;
    private Integer fontSize = 14;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private ParagraphAlignment alignment = ParagraphAlignment.LEFT;
    private Colors textHighlightColor = Colors.black;
    private Colors textColor = Colors.black;

    public ParagraphParams(Fonts font, Integer fontSize, boolean bold, boolean italic, boolean underline,
                           ParagraphAlignment alignment, Colors textHighlightColor, Colors textColor) {
        this.font = font;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.alignment = alignment;
        this.textHighlightColor = textHighlightColor;
        this.textColor = textColor;
    }

    public ParagraphParams(Temp temp){
        boolean bold = temp.getBold().equals("1");
        boolean italic = temp.getItalic().equals("1");
        boolean underline = temp.getUnderline().equals("1");
        //не хватает textHighlightColor
        this.font = Fonts.valueOf(temp.getFont());
        this.fontSize = Integer.parseInt(temp.getFont_size());
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.alignment = ParagraphAlignment.valueOf(temp.getAlignment());
        this.textHighlightColor = Colors.valueOf(temp.getColor());
        this.textColor = Colors.valueOf(temp.getColor());
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

    public Colors getTextHighlightColor() {
        return textHighlightColor;
    }

    public void setTextHighlightColor(Colors textHighlightColor) {
        this.textHighlightColor = textHighlightColor;
    }

    public Colors getTextColor() {
        return textColor;
    }

    public void setTextColor(Colors textColor) {
        this.textColor = textColor;
    }


    public static List<ParagraphParams> getDefaultTemp1ParParams() {
        ParagraphParams heading1 = new ParagraphParams(Fonts.Calibri, 20, true, false, true,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading2 = new ParagraphParams(Fonts.Calibri, 14, true, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading3 = new ParagraphParams(Fonts.Calibri, 11, true, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading4 = new ParagraphParams(Fonts.Calibri, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading5 = new ParagraphParams(Fonts.Calibri, 11, false, true, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams textField = new ParagraphParams(Fonts.Calibri, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        List<ParagraphParams> list = new ArrayList<ParagraphParams>();
        list.add(heading1);
        list.add(heading2);
        list.add(heading3);
        list.add(heading4);
        list.add(heading5);
        list.add(textField);
        return list;
    }

    public static List<ParagraphParams> getDefaultTemp2ParParams() {
        ParagraphParams heading1 = new ParagraphParams(Fonts.Calibria, 14, true, false, true,
                ParagraphAlignment.LEFT, Colors.blue, Colors.blue);
        ParagraphParams heading2 = new ParagraphParams(Fonts.Calibria, 12, true, false, false,
                ParagraphAlignment.LEFT, Colors.blue, Colors.blue);
        ParagraphParams heading3 = new ParagraphParams(Fonts.Calibria, 11, true, false, false,
                ParagraphAlignment.LEFT, Colors.steel_blue, Colors.steel_blue);
        ParagraphParams heading4 = new ParagraphParams(Fonts.Calibria, 11, true, true, false,
                ParagraphAlignment.LEFT, Colors.steel_blue, Colors.steel_blue);
        ParagraphParams heading5 = new ParagraphParams(Fonts.Calibria, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.blue, Colors.blue);
        ParagraphParams textField = new ParagraphParams(Fonts.Calibria, 11, false, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        List<ParagraphParams> list = new ArrayList<ParagraphParams>();
        list.add(heading1);
        list.add(heading2);
        list.add(heading3);
        list.add(heading4);
        list.add(heading5);
        list.add(textField);
        return list;
    }

    public static List<ParagraphParams> getDefaultTemp3ParParams() {
        ParagraphParams heading1 = new ParagraphParams(Fonts.Arial, 18, true, false, true,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading2 = new ParagraphParams(Fonts.Arial, 16, true, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading3 = new ParagraphParams(Fonts.Arial, 14, true, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading4 = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams heading5 = new ParagraphParams(Fonts.Arial, 14, false, true, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        ParagraphParams textField = new ParagraphParams(Fonts.Arial, 14, false, false, false,
                ParagraphAlignment.LEFT, Colors.black, Colors.black);
        List<ParagraphParams> list = new ArrayList<ParagraphParams>();
        list.add(heading1);
        list.add(heading2);
        list.add(heading3);
        list.add(heading4);
        list.add(heading5);
        list.add(textField);
        return list;
    }

}
