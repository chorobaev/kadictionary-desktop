package data.model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Language {
    KYRGYZ("Kyrgyz"),
    ARABIC("Arabic");

    public static List<Language> getAll() {
        return Arrays.asList(KYRGYZ, ARABIC);
    }

    public static List<MenuItem> getMenuItemsInKyrgyz() {
        List<MenuItem> menu = new ArrayList<>();
        getAll().forEach(lan -> {
            MenuItem item = new MenuItem(Translation.of(lan).inKyrgyz());
            item.setUserData(lan);
            menu.add(item);
        });
        return menu;
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
