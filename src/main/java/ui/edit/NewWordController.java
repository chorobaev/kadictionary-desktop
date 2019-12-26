package ui.edit;

import base.BaseController;
import data.model.KAWord;
import data.model.Language;
import data.model.Translation;
import data.model.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ui.common.OnWordSelectedListener;

public class NewWordController extends BaseController {
    private NewWordInteractionListener newWordInteractionListener;

    @FXML private Label labelLanguage;
    @FXML private TextField textFieldWord;
    @FXML private TextArea textFieldDescription;

    @FXML
    void saveWord(ActionEvent event) {
        if (newWordInteractionListener != null) {
            newWordInteractionListener.addWordAndDescription(
                textFieldWord.getText(),
                textFieldDescription.getText()
            );
        }
    }

    public void init(Language language, String word) {
        labelLanguage.setText(Translation.of(language).inKyrgyz());
        textFieldWord.setText(word);
    }

    public void setNewWordInteractionListener(NewWordInteractionListener newWordInteractionListener) {
        this.newWordInteractionListener = newWordInteractionListener;
    }

    public interface NewWordInteractionListener {
        void addWordAndDescription(String word, String description);
    }
}
