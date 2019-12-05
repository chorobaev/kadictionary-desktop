package data.model;

public interface Word {
    int getId();
    String getWord();
    int getCorrectness();
    Language getLanguage();
}
