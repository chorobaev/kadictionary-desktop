package ui.main;

import data.MySQLAccess;
import data.model.Language;
import data.model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.List;

public class MainController {
    @FXML private TextField textFieldSearch;
    @FXML private RadioButton radioBtnKyrgyz;
    @FXML private ToggleGroup toggleGroupLanguages;
    @FXML private RadioButton radioBtnArabic;
    @FXML private ListView<Word> listViewWords;

    private final ObservableList<Word> words = FXCollections.observableArrayList();
    private MySQLAccess mySQLAccess;

    public void setMySQLAccess(MySQLAccess mySQLAccess) {
        this.mySQLAccess = mySQLAccess;
        listViewWords.setItems(words);
        loadWords();
        updateWordList();
    }

    @FXML
    void initialize() {
        radioBtnKyrgyz.setUserData(Language.KYRGYZ);
        radioBtnArabic.setUserData(Language.ARABIC);
    }

    @FXML
    void onLanguageChanged(ActionEvent event) {
        choseLanguage();
        loadWords();
        updateWordList();
    }

    private void choseLanguage() {
        if (mySQLAccess != null) {
            Language language = (Language) toggleGroupLanguages.getSelectedToggle().getUserData();
            mySQLAccess.setLanguage(language);
        }
    }

    private void loadWords() {
        try {
            if (mySQLAccess != null) {
                mySQLAccess.loadAllWords();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onSearchPressed(ActionEvent event) {
        String word = textFieldSearch.getText();
        searchWords(word);
        updateWordList();
    }

    private void searchWords(String word) {
        try {
            if (mySQLAccess != null) {
                mySQLAccess.searchWord(word);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateWordList() {
        words.clear();
        if (mySQLAccess != null) {
            List<Word> words = mySQLAccess.getWords();
            if (words != null) this.words.addAll(mySQLAccess.getWords());
            System.out.println("Words: " + words);
        }
    }
}
