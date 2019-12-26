package ui;

import data.model.Language;
import data.model.Word;
import data.repositories.AuthRepository;
import data.repositories.WordsRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import ui.edit.EditController;
import ui.edit.NewWordDialog;
import ui.login.AuthenticationDialog;
import ui.main.MainController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavigationManager implements Navigation {
    private final Scene scene;
    private final WordsRepository wordsRepository;
    private final AuthRepository authRepository;

    public NavigationManager(Scene scene, AuthRepository authRepository, WordsRepository wordsRepository) {
        this.scene = scene;
        this.authRepository = authRepository;
        this.wordsRepository = wordsRepository;
    }

    @Override
    public void showAuthDialog() {
        AuthenticationDialog.show(this, authRepository).ifPresent(user -> {
            System.out.println("Authorized user: " + user);
        });
    }

    @Override
    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/main.fxml"));
            scene.setRoot(loader.load());
            MainController controller = loader.getController();
            controller.init(this, wordsRepository);
        } catch (IOException ex) {
            Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void showMessage(String msg) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Билдирүү");
        dialog.setContentText(msg);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();
    }

    @Override
    public void showNewWordDialog(Language language, String word) {
        NewWordDialog.show(this, wordsRepository, language, word).ifPresent(success -> {
            System.out.println("Success saving word");
        });
    }

    @Override
    public void showEditView() {
        AuthenticationDialog.show(this, authRepository).ifPresent(user -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/edit.fxml"));
                scene.setRoot(loader.load());
                EditController controller = loader.getController();
                controller.init(this, wordsRepository);
            } catch (IOException ex) {
                Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}