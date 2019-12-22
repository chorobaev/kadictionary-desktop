package ui.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class LoginDialog {
    private static String[] loginResponse;

    private LoginDialog() {
    }

    public static String[] display() {

        try {
            showDialog();
        } catch (IOException ex) {

        }

        return loginResponse;
    }

    private static void showDialog() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNIFIED);

        FXMLLoader loader = new FXMLLoader(new URL("/layout/login.fxml"));

        Parent root = loader.load();

        window.setScene(new Scene(root, 500, 300));
        window.showAndWait();
    }
}
