package ui;

import java.io.IOException;
import java.util.logging.*;

import data.repositories.AuthRepository;
import data.repositories.WordsRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import ui.login.LoginController;
import ui.main.MainController;

public class NavigationManager {
    private Scene scene;

    public NavigationManager(Scene scene) {
        this.scene = scene;
    }

    public void showLoginScreen() {
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
}