package ui.edit;

import data.model.Language;
import data.model.Word;
import data.repositories.WordsRepository;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ui.Navigation;
import utility.NodeUtility;

import java.util.List;

public class EditController {
    private Navigation navigation;
    private WordsRepository wordsRepository;

    @FXML private AnchorPane paneLeft;
    @FXML private VBox vBoxCenter;
    @FXML private AnchorPane paneRight;

    public void init(Navigation navigation, WordsRepository wordsRepository) {
        this.navigation = navigation;
        this.wordsRepository = wordsRepository;
        initView();
    }

    private void initView() {
        initLeftPane();
        initRightPane();
    }

    private void initLeftPane() {
        VBox left = NodeUtility.getInstance().getNewWordPane(new ActualWordInteractionListener());
        paneLeft.getChildren().add(left);
        NodeUtility.setAnchorsZero(left);
    }

    private void initRightPane() {
        VBox right = NodeUtility.getInstance().getNewWordPane(new TranslationWordInteractionListener());
        paneRight.getChildren().add(right);
        NodeUtility.setAnchorsZero(right);
    }

    private class ActualWordInteractionListener implements WordInteractionListener {

        @Override public void addWordWithDescription(String word, String desc) {

        }

        @Override public void onWordChosen(Word word) {

        }

        @Override public List<Word> searchForWord(String word) {
            return null;
        }

        @Override public void onLanguageChanged(Language language) {

        }
    }

    private class TranslationWordInteractionListener implements WordInteractionListener {

        @Override public void addWordWithDescription(String word, String desc) {

        }

        @Override public void onWordChosen(Word word) {

        }

        @Override public List<Word> searchForWord(String word) {
            return null;
        }

        @Override public void onLanguageChanged(Language language) {

        }
    }
}
