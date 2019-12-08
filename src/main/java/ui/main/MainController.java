package ui.main;

import data.MySQLAccess;
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
    private MySQLAccess mySQLAccess;
    private OnWordChosenListener onWordChosenListener;

    public void setMySQLAccess(MySQLAccess mySQLAccess) {
        this.mySQLAccess = mySQLAccess;
        listViewWords.setItems(words);
        loadAllWords();
    }

    public void setOnWordChosenListener(ChangeListener<Number> listener) {

    }

    @FXML void initialize() {
        radioBtnKyrgyz.setUserData(Language.KYRGYZ);
        radioBtnArabic.setUserData(Language.ARABIC);
        listViewWords.getSelectionModel().selectedIndexProperty().addListener(new OnWordChosenListener());
    }

    @FXML void onLanguageChanged(ActionEvent event) {
        changeLanguage();
        loadAllWords();
    }

    private void changeLanguage() {
        if (mySQLAccess != null) {
            Language language = (Language) toggleGroupLanguages.getSelectedToggle().getUserData();
            mySQLAccess.setLanguage(language);
        }
    }

    @FXML void onSearchPressed(ActionEvent event) {
        String word = textFieldSearch.getText();
        searchWords(word);
    }

    private void loadAllWords() {
        try {
            this.words.clear();
            if (mySQLAccess != null) {
                List<Word> words = mySQLAccess.getAllWords();
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
            if (mySQLAccess != null) {
                List<Word> words = mySQLAccess.searchWord(word);
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
                System.out.println("Word '" + word + "' is selected");
            } catch (ArrayIndexOutOfBoundsException ignored) {
                listViewWords.getSelectionModel().clearSelection();
            }
        }

        private void showDescriptions(int wordId) {
            try {
                if (mySQLAccess != null) {
                    List<String> descriptions = mySQLAccess.getDescriptionByWordId(wordId);
                    labelDescription.setText(formatListString(descriptions));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void showTranslations(int wordId) {
            try {
                if (mySQLAccess != null) {
                    List<String> translations = mySQLAccess.getTranslationByWordId(wordId, Language.ARABIC);
                    labelArabicTranslation.setText(formatListString(translations));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String formatListString(List<String> words) {
        StringBuilder sb = new StringBuilder();
        for (String st : words) {
            sb.append("- ");
            sb.append(st);
            sb.append('\n');
        }
        return sb.toString();
    }
}
