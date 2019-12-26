package ui.edit;

import base.BaseController;
import data.model.Language;
import data.model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ui.NavigationManager;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utility.CommonUtility.initLanguageMenuButton;

public class WordController extends BaseController {
    private WordInteractionListener interactionListener;
    private final ObservableList<Word> words = FXCollections.observableArrayList();

    @FXML private MenuButton menuButtonLanguage;
    @FXML private TextField textFieldWord;
    @FXML private ListView<?> listViewSuggestions;
    @FXML private TextArea textAreaDescription;

    @FXML void addWord(ActionEvent event) {
        if (interactionListener != null) {
            interactionListener.addWordWithDescription(textFieldWord.getText(), textAreaDescription.getText());
        }
    }

    @FXML void initialize() {
        initLanguageMenuButton(menuButtonLanguage, this::changeLanguage);
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
        if (interactionListener != null) {
            List<Word> words = interactionListener.searchForWord(word);
            if (words != null) {
                this.words.addAll(words);
                if (!words.isEmpty()) listViewSuggestions.getSelectionModel().select(0);
            }
        }
    }

    public void init(WordInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }
}
