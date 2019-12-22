package ui;

import java.io.IOException;
import java.util.logging.*;

import data.repositories.AuthRepository;
import data.repositories.WordsRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import ui.login.LoginController;
import ui.main.MainController;
import ui.signup.SignUpController;

public class NavigationManager {
    private Scene scene;

    public NavigationManager(Scene scene) {
        this.scene = scene;
    }

    public void showSignInScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/login.fxml"));
            scene.setRoot(loader.load());
            LoginController controller = loader.getController();
            controller.init(AuthRepository.getInstance());
        } catch (IOException ex) {
            Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/main.fxml"));
            scene.setRoot(loader.load());
            MainController controller = loader.getController();
            controller.init(WordsRepository.getInstance());
        } catch (IOException ex) {
            Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/signup.fxml"));
            scene.setRoot(loader.load());
            SignUpController controller = loader.getController();
            controller.init(AuthRepository.getInstance());
        } catch (IOException ex) {
            Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMessage(String msg) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setContentText(msg);
        dialog.showAndWait()
            .filter(response -> response == ButtonType.OK)
            .ifPresent(response -> {
            });
    }
}