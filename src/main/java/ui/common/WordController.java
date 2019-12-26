package ui.common;

import base.BaseController;
import data.model.Language;
import data.model.Word;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import utility.CommonUtility;
import utility.NodeUtility;

import java.util.List;

public class WordController extends BaseController {
    private WordInteractionListener interactionListener;
    private final ObservableList<Word> words = FXCollections.observableArrayList();
    private Language language;

    @FXML private MenuButton menuButtonLanguage;
    @FXML private TextField textFieldWord;
    @FXML private ListView<Word> listViewSuggestions;

    public void init(WordInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
        NodeUtility.initLanguageMenuButton(menuButtonLanguage, this::changeLanguage);
        initListView();
        initTextFieldSearch();
        initListView();
        initInputs();
    }

    private void changeLanguage(Language language) {
        this.language = language;
        if (interactionListener != null) {
            interactionListener.onLanguageChanged(language);
            loadWords(interactionListener.getAllWords());
        }
    }

    private void initListView() {
        listViewSuggestions.setItems(words);
        if (interactionListener != null) {
            loadWords(interactionListener.getAllWords());
        }
        listViewSuggestions.getSelectionModel().selectedIndexProperty().addListener(this::onWordSelected);
    }

    private void initTextFieldSearch() {
        textFieldWord.textProperty().addListener(((observable, oldValue, newValue) -> searchForSuggestion(newValue)));
    }

    private void searchForSuggestion(String word) {
        this.words.clear();
        if (interactionListener != null) {
            List<Word> words = interactionListener.searchForWord(word);
            loadWords(words);
            if (words.isEmpty()) {
                this.words.add(CommonUtility.getAddWord(language));
            }
        }
    }

    private void loadWords(List<Word> words) {
        this.words.clear();
        if (words != null) {
            this.words.addAll(words);
        }
    }

    private void initInputs() {
        interactionListener.setOnWordSelectedListener(word -> {
            for (int i = 0; i < menuButtonLanguage.getItems().size(); i++) {
                MenuItem menuItem = menuButtonLanguage.getItems().get(i);
                Language language = (Language) menuItem.getUserData();
                if (language == word.getLanguage()) {
                    menuButtonLanguage.getItems().get(i).fire();
                }
            }

            if (word.getId() != -1) {
                textFieldWord.setText(word.getWord());
            }
        });
    }

    private void onWordSelected(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        try {
            Word word = words.get(newValue.intValue());
            if (interactionListener != null) {
                if (word.getId() == -1) {
                    interactionListener.addNewWord(textFieldWord.getText());
                    this.words.clear();
                    loadWords(interactionListener.getAllWords());
                } else {
                    interactionListener.onWordChosen(word);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            listViewSuggestions.getSelectionModel().clearSelection();
        }
    }

    public interface WordInteractionListener {

        void setOnWordSelectedListener(OnWordSelectedListener onWordSelectedListener);

        void addNewWord(String word);

        void onWordChosen(Word word);

        List<Word> searchForWord(String word);

        List<Word> getAllWords();

        void onLanguageChanged(Language language);
    }
}
