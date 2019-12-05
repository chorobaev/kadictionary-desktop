import data.MySQLAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.main.MainController;

import static utility.AppConstant.APP_NAME;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/main.fxml"));

        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setMySQLAccess(new MySQLAccess());

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(new Scene(root, 535, 205));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
//        MySQLAccess access = new MySQLAccess();
//        access.getAllKyrgyzWords();
    }
}
