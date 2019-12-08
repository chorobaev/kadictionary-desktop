package data.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Language {
    KYRGYZ("Kyrgyz"),
    ARABIC("Arabic");

    public static List<Language> getAll() {
        return Arrays.asList(KYRGYZ, ARABIC);
    }
    private final String name;

    Language(String name) {
        this.name = name;
    }

    public List<Language> getLanguagesExceptSelf() {
        List<Language> languages = new ArrayList<>();
        for (Language lang : getAll()) {
            if (lang != this) {
                languages.add(lang);
            }
        }
        return languages;
    }

    @Override
    public String toString() {
        return name;
    }
}
