package ui.edit;

import data.model.KAWord;
import data.model.Language;
import data.model.Word;
import data.repositories.WordsRepository;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ui.Navigation;
import ui.common.DisplayController;
import ui.common.OnWordSelectedListener;
import ui.common.WordController;
import utility.NodeUtility;

import java.util.List;
import java.util.Map;

public class EditController {
    private Navigation navigation;
    private WordsRepository wordsRepository;
    private OnWordSelectedListener onActualWordSelectedListener;
    private OnWordSelectedListener onTranslationSelectedListener;

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

    private List<Word> getAllWords(Language language) {
        List<Word> words = null;
        try {
            words = wordsRepository.getAllWords(language);
        } catch (Exception ignored) {
        }
        return words;
    }

    private List<Word> searchForWord(Language language, String word) {
        List<Word> words = null;
        try {
            words = wordsRepository.searchWord(language, word);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return words;
    }

    private List<String> getDescriptionsByWordId(Language language, int wordId) {
        List<String> descs = null;
        try {
            descs = wordsRepository.getDescriptionByWordId(language, wordId);
        } catch (Exception ignored) {

        }
        return descs;
    }

    private Map<Language, List<Word>> getTranslationsByWordId(Language language, int wordId) {
        Map<Language, List<Word>> translations = null;
        try {
            translations = wordsRepository.getTranslationsByWordId(language, wordId);
        } catch (Exception ignored) {
        }
        return translations;
    }

    private class ActualWordInteractionListener implements WordController.WordInteractionListener {

        private Language language;

        @Override
        public void setOnWordSelectedListener(OnWordSelectedListener onWordSelectedListener) {

        }

        @Override
        public void addNewWord(String word) {
            navigation.showNewWordDialog(language, word);
        }

        @Override
        public void onWordChosen(Word word) {
            if (onActualWordSelectedListener != null) {
                onActualWordSelectedListener.onSelect(word);
            }
        }

        @Override
        public List<Word> searchForWord(String word) {
            return EditController.this.searchForWord(language, word);
        }

        @Override
        public List<Word> getAllWords() {
            return EditController.this.getAllWords(language);
        }

        @Override
        public void onLanguageChanged(Language language) {
            this.language = language;
        }
    }

    private class TranslationWordInteractionListener implements WordController.WordInteractionListener {
        private Language language;

        @Override
        public void setOnWordSelectedListener(OnWordSelectedListener onWordSelectedListener) {
            onTranslationSelectedListener = onWordSelectedListener;
        }

        @Override
        public void addNewWord(String word) {
            navigation.showNewWordDialog(language, word);
        }

        @Override
        public void onWordChosen(Word word) {

        }

        @Override
        public List<Word> searchForWord(String word) {
            return EditController.this.searchForWord(language, word);
        }

        @Override
        public List<Word> getAllWords() {
            return EditController.this.getAllWords(language);
        }

        @Override
        public void onLanguageChanged(Language language) {
            this.language = language;
        }
    }

    private class DisplayWordInteractionsListener implements DisplayController.DisplayInteractionListener {

        @Override
        public void setOnDisplayChangedListener(OnWordSelectedListener onWordSelectedListener) {
            EditController.this.onActualWordSelectedListener = onWordSelectedListener;
        }

        @Override
        public List<String> getDescriptionsByWord(Word word) {
            return EditController.this.getDescriptionsByWordId(word.getLanguage(), word.getId());
        }

        @Override
        public Map<Language, List<Word>> getTranslationsByWord(Word word) {
            return getTranslationsByWordId(word.getLanguage(), word.getId());
        }

        @Override
        public void onSelectTranslation(Word word) {
            System.out.println("Selected translation: " + word);
            onTranslationSelectedListener.onSelect(word);
        }
    }
}
