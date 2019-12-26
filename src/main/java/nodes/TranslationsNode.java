package nodes;

import data.model.Language;
import data.model.Translation;
import data.model.Word;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import ui.common.OnWordSelectedListener;
import utility.CommonUtility;

import java.util.List;

public class TranslationsNode extends TitledPane {
    private Language language;
    private ObservableList<Word> translations = FXCollections.observableArrayList();
    private ListView<Word> listViewWords = new ListView<>();

    private OnWordSelectedListener onWordSelectedListener;

    public TranslationsNode(Language language) {
        this.language = language;
        initView();
    }

    public void setOnWordSelectedListener(OnWordSelectedListener onWordSelectedListener) {
        this.onWordSelectedListener = onWordSelectedListener;
    }

    public void setTranslations(List<Word> translations) {
        this.translations.clear();
        this.translations.addAll(translations);
        this.translations.add(CommonUtility.getAddWord(language));
    }

    private void initView() {
        setText(Translation.of(language).inKyrgyz());
        setExpanded(false);
        listViewWords.setItems(translations);
        setContent(listViewWords);
        listViewWords.getSelectionModel().selectedIndexProperty().addListener(this::onTranslationSelected);
    }

    private void onTranslationSelected(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        try {
            if (onWordSelectedListener != null) {
                onWordSelectedListener.onSelect(translations.get(newValue.intValue()));
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            listViewWords.getSelectionModel().clearSelection();
        }
    }
}
