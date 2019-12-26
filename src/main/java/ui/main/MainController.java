package ui.main;

import base.BaseController;
import data.model.Language;
import data.model.Word;
import data.repositories.WordsRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import ui.Navigation;
import utility.NodeUtility;

import java.util.List;
import java.util.Map;

import static utility.CommonUtility.formatLanguageTranslations;
import static utility.CommonUtility.formatListString;

public class MainController extends BaseController {
    private Navigation navigation;
    private WordsRepository wordsRepository;
    private Language language = Language.KYRGYZ;

    @FXML private TextField textFieldSearch;
    @FXML private MenuButton menuButtonLanguage;
    @FXML private ListView<Word> listViewWords;
    @FXML private Label labelWord;
    @FXML private Label labelDescription;
    @FXML private Label labelArabicTranslation;

    private final ObservableList<Word> words = FXCollections.observableArrayList();
    private OnWordChosenListener onWordChosenListener = new OnWordChosenListener();

    public void init(Navigation navigation, WordsRepository wordsRepository) {
        this.navigation = navigation;
        this.wordsRepository = wordsRepository;
        listViewWords.setItems(words);
        loadAllWords();
        NodeUtility.initLanguageMenuButton(menuButtonLanguage, this::changeLanguage);
        initWordsListView();
        initTextFieldSearch();
    }

    private void initWordsListView() {
        listViewWords.getSelectionModel()
            .selectedIndexProperty()
            .addListener(onWordChosenListener);
    }

    private void initTextFieldSearch() {
        textFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> searchWords(newValue)));
    }

    private void changeLanguage(Language language) {
        this.language = language;
        if (wordsRepository != null) {
            loadAllWords();
        }
    }

    @FXML
    void edit(ActionEvent event) {
        navigation.showEditView();
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
    }

    private void loadAllWords() {
        try {
            this.words.clear();
            if (wordsRepository != null) {
                List<Word> words = wordsRepository.getAllWords(language);
                if (words != null) {
                    this.words.addAll(words);
                    if (!words.isEmpty()) listViewWords.getSelectionModel().select(0);
                }
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
                List<Word> words = wordsRepository.searchWord(language, word);
                if (word != null) this.words.addAll(words);
                if (!words.isEmpty()) listViewWords.getSelectionModel().select(0);
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
                    List<String> descriptions = wordsRepository.getDescriptionByWordId(language, wordId);
                    labelDescription.setText(formatListString(descriptions));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void showTranslations(int wordId) {
            try {
                if (wordsRepository != null) {
                    Map<Language, List<Word>> translations = wordsRepository.getTranslationsByWordId(language, wordId);
                    System.out.println(translations.toString());
                    labelArabicTranslation.setText(formatLanguageTranslations(translations));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
