package ui;

import data.repositories.AuthRepository;
import data.repositories.WordsRepository;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static utility.AppConstant.APP_NAME;

public class Main extends Application {

    private static NavigationManager navigationManager;

    public static NavigationManager getNavigationManager() {
        return navigationManager;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(new StackPane(), 535, 205);

        navigationManager = new NavigationManager(scene, AuthRepository.getInstance(), WordsRepository.getInstance());
        navigationManager.showMainView();

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
