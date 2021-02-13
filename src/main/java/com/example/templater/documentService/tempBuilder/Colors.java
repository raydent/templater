package com.example.templater.documentService.tempBuilder;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;

//заглушка
public enum Colors {
    ;
    public static STHighlightColor.Enum getColorEnum(String color) {
        color = color.toLowerCase();
        switch (color) {
            case "none" : return STHighlightColor.NONE;
            case "black": return STHighlightColor.BLACK;
            case "blue" : return STHighlightColor.BLUE;
            case "cyan" : return STHighlightColor.CYAN;
            case "dark_blue" : return STHighlightColor.DARK_BLUE;
            case "dark_cyan" : return STHighlightColor.DARK_CYAN;
            case "dark_gray" : return STHighlightColor.DARK_GRAY;
            case "dark_magenta" : return STHighlightColor.DARK_MAGENTA;
            case "dark_red" : return STHighlightColor.DARK_RED;
            case "dark_yellow" : return STHighlightColor.DARK_YELLOW;
            case "dark_green" : return STHighlightColor.DARK_GREEN;
            case "green" : return STHighlightColor.GREEN;
            case "light_gray" : return STHighlightColor.LIGHT_GRAY;
            case "magenta" : return STHighlightColor.MAGENTA;
            case "red" : return STHighlightColor.RED;
            case "yellow" : return STHighlightColor.YELLOW;
            case "white" : return STHighlightColor.WHITE;
            default : return STHighlightColor.NONE;
        }
    }

    public static String getColorName(String color) {
        if (color.equals(STHighlightColor.NONE)) {
            return STHighlightColor.NONE.toString();
        } else if (color.equals(STHighlightColor.BLACK)) {
            return STHighlightColor.BLACK.toString();
        }
        else if (color.equals(STHighlightColor.BLUE)) {
            return STHighlightColor.BLUE.toString();
        }
        else if (color.equals(STHighlightColor.CYAN)) {
            return STHighlightColor.CYAN.toString();
        }
        else if (color.equals(STHighlightColor.DARK_BLUE)) {
            return STHighlightColor.DARK_BLUE.toString();
        }else if (color.equals(STHighlightColor.DARK_CYAN)) {
            return STHighlightColor.DARK_CYAN.toString();
        }
        else if (color.equals(STHighlightColor.DARK_GRAY)) {
            return STHighlightColor.DARK_GRAY.toString();
        }
        else if (color.equals(STHighlightColor.DARK_MAGENTA)) {
            return STHighlightColor.DARK_MAGENTA.toString();
        }
        else if (color.equals(STHighlightColor.DARK_RED)) {
            return STHighlightColor.DARK_RED.toString();
        }
        else if (color.equals(STHighlightColor.DARK_YELLOW)) {
            return STHighlightColor.DARK_YELLOW.toString();
        }
        else if (color.equals(STHighlightColor.DARK_GREEN)) {
            return STHighlightColor.DARK_GREEN.toString();
        }
        else if (color.equals(STHighlightColor.GREEN)) {
            return STHighlightColor.GREEN.toString();
        }
        else if (color.equals(STHighlightColor.LIGHT_GRAY)) {
            return STHighlightColor.LIGHT_GRAY.toString();
        }
        else if (color.equals(STHighlightColor.MAGENTA)) {
            return STHighlightColor.MAGENTA.toString();
        }
        else if (color.equals(STHighlightColor.RED)) {
            return STHighlightColor.RED.toString();
        }
        else if (color.equals(STHighlightColor.YELLOW)) {
            return STHighlightColor.YELLOW.toString();
        }
        else if (color.equals(STHighlightColor.WHITE)) {
            return STHighlightColor.WHITE.toString();
        }
        else {
            return STHighlightColor.NONE.toString();
        }
    }

    public static String getColorName(STHighlightColor.Enum color) {
        if (color.equals(STHighlightColor.NONE)) {
            return STHighlightColor.NONE.toString();
        } else if (color.equals(STHighlightColor.BLACK)) {
            return STHighlightColor.BLACK.toString();
        }
        else if (color.equals(STHighlightColor.BLUE)) {
            return STHighlightColor.BLUE.toString();
        }
        else if (color.equals(STHighlightColor.CYAN)) {
            return STHighlightColor.CYAN.toString();
        }
        else if (color.equals(STHighlightColor.DARK_BLUE)) {
            return STHighlightColor.DARK_BLUE.toString();
        }else if (color.equals(STHighlightColor.DARK_CYAN)) {
            return STHighlightColor.DARK_CYAN.toString();
        }
        else if (color.equals(STHighlightColor.DARK_GRAY)) {
            return STHighlightColor.DARK_GRAY.toString();
        }
        else if (color.equals(STHighlightColor.DARK_MAGENTA)) {
            return STHighlightColor.DARK_MAGENTA.toString();
        }
        else if (color.equals(STHighlightColor.DARK_RED)) {
            return STHighlightColor.DARK_RED.toString();
        }
        else if (color.equals(STHighlightColor.DARK_YELLOW)) {
            return STHighlightColor.DARK_YELLOW.toString();
        }
        else if (color.equals(STHighlightColor.DARK_GREEN)) {
            return STHighlightColor.DARK_GREEN.toString();
        }
        else if (color.equals(STHighlightColor.GREEN)) {
            return STHighlightColor.GREEN.toString();
        }
        else if (color.equals(STHighlightColor.LIGHT_GRAY)) {
            return STHighlightColor.LIGHT_GRAY.toString();
        }
        else if (color.equals(STHighlightColor.MAGENTA)) {
            return STHighlightColor.MAGENTA.toString();
        }
        else if (color.equals(STHighlightColor.RED)) {
            return STHighlightColor.RED.toString();
        }
        else if (color.equals(STHighlightColor.YELLOW)) {
            return STHighlightColor.YELLOW.toString();
        }
        else if (color.equals(STHighlightColor.WHITE)) {
            return STHighlightColor.WHITE.toString();
        }
        else {
            return STHighlightColor.NONE.toString();
        }
    }

    public static String getColorCode(String color) {
        color = color.toLowerCase();
        switch (color) {
            case "none" : return "none";
            case "black": return "000000";
            case "blue" : return "0000FF";
            case "cyan" : return "00FFFF";
            case "dark_blue" : return "00008B";
            case "dark_cyan" : return "008B8B";
            case "dark_gray" : return "A9A9A9";
            case "dark_magenta" : return "8B008B";
            case "dark_red" : return "8B0000";
            case "dark_yellow" : return "CCCC00";
            case "dark_green" : return "006400";
            case "green" : return "00FF00";
            case "light_gray" : return "D3D3D3";
            case "magenta" : return "FF00FF";
            case "red" : return "FF0000";
            case "yellow" : return "FFFF00";
            case "white" : return "FFFFFF";
            default : return "none";
        }
    }
}