package data.model;

public class Translation {
    private final Language language;

    private Translation(Language language) {
        this.language = language;
    }

    public String inKyrgyz() {
        if (language == Language.KYRGYZ) {
            return "Кыргызча";
        } else if (language == Language.ARABIC) {
            return "Арапча";
        }
        return language.toString();
    }

    public static Translation of(Language language) {
        return new Translation(language);
    }
}
