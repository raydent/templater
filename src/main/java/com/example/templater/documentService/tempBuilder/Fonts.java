package com.example.templater.documentService.tempBuilder;

public enum Fonts {
    Calibri,
    Times_New_Roman,
    Arial,
    Kambria,
    Tahoma,
    Calibria;
    public static String getFontString(Fonts font) {
        switch (font) {
            case Arial: return "Arial";
            case Tahoma: return "Tahoma";
            case Calibri: return "Calibri";
            case Times_New_Roman: return "Times New Roman";
            case Kambria: return "Kambria";
            case Calibria: return "Calibria";
            default: return "Calibri";
        }
    }
    public static Fonts getFontEnum(String str) {
        switch (str) {
            case "Arial": return Arial;
            case "Tahoma": return Tahoma;
            case "Calibri": return Calibri;
            case "Times New Roman": return Times_New_Roman;
            case "Kambria": return Kambria;
            case "Calibria": return Calibria;
            default: return Calibri;
        }
    }
}