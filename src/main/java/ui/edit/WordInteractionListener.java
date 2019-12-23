package ui.edit;

import data.model.Language;
import data.model.Word;

import java.util.List;

public interface WordInteractionListener {

    void addWordWithDescription(String word, String desc);

    void onWordChosen(Word word);

    List<Word> searchForWord(String word);

    void onLanguageChanged(Language language);
}
