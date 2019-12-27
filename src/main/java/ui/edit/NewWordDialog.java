package ui.edit;

import data.model.Language;
import data.repositories.WordsRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import ui.Navigation;

import java.util.Optional;

public class NewWordDialog extends Dialog<Boolean> {
    private Navigation navigation;
    private WordsRepository wordsRepository;
    private Language language;
    private boolean saved = false;

    public static Optional<Boolean> show(
        Navigation navigation, WordsRepository wordsRepository, Language language, String word
    ) {
        return new NewWordDialog(navigation, wordsRepository, language, word).showAndWait();
    }

    private NewWordDialog(Navigation navigation, WordsRepository wordsRepository, Language language, String word) {
        this.navigation = navigation;
        this.wordsRepository = wordsRepository;
        this.language = language;
        loadScene(word);
        initView();
    }

    private void initView() {

        setResultConverter(buttonType -> saved);
        initModality(Modality.APPLICATION_MODAL);

        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
    }

    private void loadScene(String word) {
        setTitle("Жаңы сөз");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/add_word.fxml"));
            Parent parent = loader.load();
            NewWordController controller = loader.getController();
            controller.init(language, word);
            controller.setNewWordInteractionListener(this::addWordAndDesc);
            getDialogPane().setContent(parent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addWordAndDesc(String word, String description) {
        try {
            wordsRepository.addWordWithDescription(language, word, description);
            saved = true;
            setResult(true);
            close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            navigation.showMessage("Бул сөз базада бар!");
        }
    }
}
