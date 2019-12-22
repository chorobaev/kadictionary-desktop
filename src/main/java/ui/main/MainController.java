package ui.main;

import data.repositories.WordsRepository;
import data.model.Language;
import data.model.Word;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Map;

import static utility.CommonUtility.formatLanguageTranslations;
import static utility.CommonUtility.formatListString;

public class MainController {
    @FXML private TextField textFieldSearch;
    @FXML private RadioButton radioBtnKyrgyz;
    @FXML private ToggleGroup toggleGroupLanguages;
    @FXML private RadioButton radioBtnArabic;
    @FXML private ListView<Word> listViewWords;
    @FXML private Label labelWord;
    @FXML private Label labelDescription;
    @FXML private Label labelArabicTranslation;

    private final ObservableList<Word> words = FXCollections.observableArrayList();
    private WordsRepository wordsRepository;
    private OnWordChosenListener onWordChosenListener = new OnWordChosenListener();

    public void setWordsRepository(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
        listViewWords.setItems(words);
        loadAllWords();
    }

    @FXML void initialize() {
        radioBtnKyrgyz.setUserData(Language.KYRGYZ);
        radioBtnArabic.setUserData(Language.ARABIC);
        listViewWords.getSelectionModel()
            .selectedIndexProperty()
            .addListener(onWordChosenListener);
    }

    @FXML void onLanguageChanged(ActionEvent event) {
        changeLanguage();
        loadAllWords();
    }

    private void changeLanguage() {
        if (wordsRepository != null) {
            Language language = (Language) toggleGroupLanguages.getSelectedToggle().getUserData();
            wordsRepository.setLanguage(language);
        }
    }

    @FXML void onSearchPressed(ActionEvent event) {
        String word = textFieldSearch.getText();
        searchWords(word);
    }

    private void loadAllWords() {
        try {
            this.words.clear();
            if (wordsRepository != null) {
                List<Word> words = wordsRepository.getAllWords();
                if (words != null) this.words.addAll(words);
                System.out.println("Words: " + words);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchWords(String word) {
        try {
            this.words.clear();
            if (wordsRepository != null) {
                List<Word> words = wordsRepository.searchWord(word);
                if (word != null) this.words.addAll(words);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class OnWordChosenListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            try {
                Word word = words.get(newValue.intValue());
                labelWord.setText(word.getWord());
                showDescriptions(word.getId());
                showTranslations(word.getId());
                System.out.println("Word '" + word + "' is selected");
            } catch (ArrayIndexOutOfBoundsException ignored) {
                listViewWords.getSelectionModel().clearSelection();
            }
        }

        private void showDescriptions(int wordId) {
            try {
                if (wordsRepository != null) {
                    List<String> descriptions = wordsRepository.getDescriptionByWordId(wordId);
                    labelDescription.setText(formatListString(descriptions));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void showTranslations(int wordId) {
            try {
                if (wordsRepository != null) {
                    Map<Language, List<String>> translations = wordsRepository.getTranslationsByWordId(wordId);
                    System.out.println(translations.toString());
                    labelArabicTranslation.setText(formatLanguageTranslations(translations));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
