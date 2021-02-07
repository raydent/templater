package com.example.templater.documentService.docCombine;

public class CustomException extends Exception {
    private String description;

    public CustomException(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
