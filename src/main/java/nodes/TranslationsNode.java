package nodes;

import data.model.Language;
import data.model.Translation;
import data.model.Word;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;

import java.util.List;

public class TranslationsNode extends TitledPane {
    private ObservableList<Word> translations = FXCollections.observableArrayList();
    private ListView<Word> listViewWords = new ListView<>();

    private OnTranslationSelectedListener onTranslationSelectedListener;

    public TranslationsNode(Language language) {
        setText(Translation.of(language).inKyrgyz());
        initView();
    }

    public void setOnTranslationSelectedListener(OnTranslationSelectedListener onTranslationSelectedListener) {
        this.onTranslationSelectedListener = onTranslationSelectedListener;
    }

    public void setTranslations(List<Word> translations) {
        this.translations.clear();
        this.translations.addAll(translations);
    }

    private void initView() {
        listViewWords.setItems(translations);
        setContent(listViewWords);
        listViewWords.getSelectionModel().selectedIndexProperty().addListener(this::onTranslationSelected);
    }

    private void onTranslationSelected(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        try {
            if (onTranslationSelectedListener != null) {
                onTranslationSelectedListener.onSelected(translations.get(newValue.intValue()));
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            listViewWords.getSelectionModel().clearSelection();
        }
    }

    public interface OnTranslationSelectedListener {
        void onSelected(Word word);
    }
}
