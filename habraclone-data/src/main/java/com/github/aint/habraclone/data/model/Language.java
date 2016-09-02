package com.github.aint.habraclone.data.model;

public enum Language {
    ENGLISH("en"),
    RUSSIAN("ru"),
    UKRAINIAN("uk");

    private String languageCode;

    Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

}
