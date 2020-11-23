package com.example.templater.tempBuilder;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

public class ParagraphParams {
    private Fonts font = Fonts.Calibri;
    private Integer fontSize = 14;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private ParagraphAlignment alignment = ParagraphAlignment.LEFT;
    private Colors textHighlightColor = Colors.black;
    private Colors textColor = Colors.black;
    private TableParams tableParams = new TableParams();
}
