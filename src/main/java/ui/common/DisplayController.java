package ui.common;

import base.BaseController;
import data.model.Language;
import data.model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nodes.TranslationsNode;

import java.util.List;
import java.util.Map;

public class DisplayController extends BaseController {
    private DisplayInteractionListener displayInteractionListener;
    private ObservableList<String> descriptions = FXCollections.observableArrayList();
    private Word currentWord;

    @FXML private Label labelWord;
    @FXML private ListView<String> listViewDescriptions;
    @FXML private Accordion accordionTranslations;

    public void init(DisplayInteractionListener displayInteractionListener) {
        this.displayInteractionListener = displayInteractionListener;
        listViewDescriptions.setItems(descriptions);
        initView();
    }

    private void initView() {
        displayInteractionListener.setOnDisplayChangedListener(word -> {
            currentWord = word;
            labelWord.setText(word.getWord());
            loadDescriptions();
            loadTranslations();
        });
    }

    private void loadDescriptions() {
        descriptions.clear();
        List<String> descs = displayInteractionListener.getDescriptionsByWord(currentWord);
        if (descs != null) {
            descriptions.addAll(descs);
        }
    }

    private void loadTranslations() {
        accordionTranslations.getPanes().clear();
        Map<Language, List<Word>> translations = displayInteractionListener.getTranslationsByWord(currentWord);
        if (translations != null) {
            translations.keySet().forEach(language -> {
                TranslationsNode node = new TranslationsNode(language);
                node.setOnWordSelectedListener(word ->
                    displayInteractionListener.onSelectTranslation(word)
                );
                node.setTranslations(translations.get(language));
                accordionTranslations.getPanes().add(node);
            });
        }
    }

    public interface DisplayInteractionListener {

        void setOnDisplayChangedListener(OnWordSelectedListener onWordSelectedListener);

        List<String> getDescriptionsByWord(Word word);

        Map<Language, List<Word>> getTranslationsByWord(Word word);

        void onSelectTranslation(Word word);
    }
}
