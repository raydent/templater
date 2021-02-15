package com.example.templater.documentService.docCombine;

public enum Exceptions {
    TwoSameHeadersInFile (new CustomException("Two same headers in file")),
    FileIsEmptyOrNoMainHeaders (new CustomException("File is empty or no main headers"));

    private CustomException exception;

    Exceptions(CustomException e) {
        exception = e;
    }

    public CustomException getException() {
        return exception;
    }
}
