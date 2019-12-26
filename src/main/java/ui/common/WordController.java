package ui.common;

import base.BaseController;
import data.model.Language;
import data.model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utility.NodeUtility;

import java.util.List;

public class WordController extends BaseController {
    private WordInteractionListener interactionListener;
    private final ObservableList<Word> words = FXCollections.observableArrayList();

    @FXML private MenuButton menuButtonLanguage;
    @FXML private TextField textFieldWord;
    @FXML private ListView<Word> listViewSuggestions;
    @FXML private TextArea textAreaDescription;

    @FXML
    void addWord(ActionEvent event) {
        if (interactionListener != null) {
            interactionListener.addWordWithDescription(textFieldWord.getText(), textAreaDescription.getText());
        }
    }

    public void init(WordInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
        listViewSuggestions.setItems(words);
        NodeUtility.initLanguageMenuButton(menuButtonLanguage, this::changeLanguage);
        initTextFieldSearch();
    }

    private void changeLanguage(Language language) {
        if (interactionListener != null) {
            interactionListener.onLanguageChanged(language);
        }
    }

    private void initTextFieldSearch() {
        textFieldWord.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchForSuggestion(newValue);
        }));
    }

    private void searchForSuggestion(String word) {
        this.words.clear();
        if (interactionListener != null) {
            List<Word> words = interactionListener.searchForWord(word);
            if (words != null) {
                this.words.addAll(words);
                if (!words.isEmpty()) listViewSuggestions.getSelectionModel().select(0);
            }
        }
    }

    public static interface WordInteractionListener {

        void addWordWithDescription(String word, String desc);

        void onWordChosen(Word word);

        List<Word> searchForWord(String word);

        void onLanguageChanged(Language language);
    }
}
