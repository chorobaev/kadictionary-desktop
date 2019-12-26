package ui.edit;

import data.model.Language;
import data.model.Word;
import data.repositories.WordsRepository;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ui.Navigation;
import ui.common.DisplayController;
import ui.common.WordController;
import utility.NodeUtility;

import java.util.List;
import java.util.Map;

public class EditController {
    private Navigation navigation;
    private WordsRepository wordsRepository;
    private DisplayController.OnDisplayChangedListener onWordSelectedListener;

    @FXML private AnchorPane paneLeft;
    @FXML private AnchorPane paneCenter;
    @FXML private AnchorPane paneRight;

    public void init(Navigation navigation, WordsRepository wordsRepository) {
        this.navigation = navigation;
        this.wordsRepository = wordsRepository;
        initView();
    }

    private void initView() {
        initLeftPane();
        initCenterPane();
        initRightPane();
    }

    private void initLeftPane() {
        VBox left = NodeUtility.getInstance().getNewWordPane(new ActualWordInteractionListener());
        paneLeft.getChildren().add(left);
        NodeUtility.setAnchorsZero(left);
    }

    private void initCenterPane() {
        VBox center = NodeUtility.getInstance().getDisplayPane(new DisplayWordInteractionsListener());
        paneCenter.getChildren().add(center);
        NodeUtility.setAnchorsZero(center);
    }

    private void initRightPane() {
        VBox right = NodeUtility.getInstance().getNewWordPane(new TranslationWordInteractionListener());
        paneRight.getChildren().add(right);
        NodeUtility.setAnchorsZero(right);
    }

    private class ActualWordInteractionListener implements WordController.WordInteractionListener {

        @Override
        public void addWordWithDescription(String word, String desc) {

        }

        @Override
        public void onWordChosen(Word word) {
            onWordSelectedListener.onWordChanged(word);
        }

        @Override
        public List<Word> searchForWord(String word) {
            System.out.println("Searching for word: " + word);
            List<Word> words = null;
            try {
                words = wordsRepository.searchWord(word);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            return words;
        }

        @Override
        public void onLanguageChanged(Language language) {
            wordsRepository.changeLanguage(language);
        }
    }

    private class TranslationWordInteractionListener implements WordController.WordInteractionListener {

        @Override
        public void addWordWithDescription(String word, String desc) {

        }

        @Override
        public void onWordChosen(Word word) {

        }

        @Override
        public List<Word> searchForWord(String word) {
            return null;
        }

        @Override
        public void onLanguageChanged(Language language) {

        }
    }

    private class DisplayWordInteractionsListener implements DisplayController.DisplayInteractionListener {

        @Override
        public void setOnDisplayChangedListener(DisplayController.OnDisplayChangedListener displayChangedListener) {
            EditController.this.onWordSelectedListener = displayChangedListener;
        }

        @Override
        public List<String> getDescriptionsByWordId(int id) {
            return null;
        }

        @Override
        public Map<Language, List<Word>> getTranslationsByWordId(int id) {
            return null;
        }

        @Override
        public void onTranslationSelected(Language language, Word word) {

        }
    }
}
