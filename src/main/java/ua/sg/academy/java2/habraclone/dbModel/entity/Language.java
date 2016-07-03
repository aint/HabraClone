package ua.sg.academy.java2.habraclone.dbModel.entity;

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
