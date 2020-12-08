package com.example.templater.tempBuilder;
//заглушка
public enum Colors {
    yellow,
    turquoise,
    steel_blue,
    blue,
    purple,
    red,
    green,
    gray,
    black,
    white,
    transparent,
    aqua_dark,
    aqua;

    public static String getColorCode(Colors color) {
        switch (color) {
            case black: return "000000";
            case steel_blue: return "4682B4";
            case gray: return "708090";
            case red: return "FF0000";
            case green: return "008000";
            case white: return "FFFFFF";
            case purple: return "800080";
            case yellow: return "FFFF00";
            case turquoise: return "40E0D0";
            case aqua_dark: return "215868";
            case aqua: return "4BACC6";
            case blue: return "365F91";
            default: return "transparent";
        }
    }
}