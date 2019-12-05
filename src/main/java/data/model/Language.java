package data.model;

public enum Language {
    KYRGYZ("Kyrgyz"),
    ARABIC("Arabic");

    private final String name;

    Language(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
