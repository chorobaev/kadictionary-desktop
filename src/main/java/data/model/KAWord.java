package data.model;

import javafx.scene.Parent;

public class KAWord implements Word {
    private final int id;
    private final String word;
    private final int correctness;
    private final Language language;

    public KAWord(int id, String word, int correctness, Language language) {
        this.id = id;
        this.word = word;
        this.correctness = correctness;
        this.language = language;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getCorrectness() {
        return correctness;
    }

    @Override
    public String toString() {
        return word;
    }
}
