package ui.login;

import data.repositories.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import base.BaseController;

public class LoginController extends BaseController {
    private AuthRepository authRepository;

    @FXML private VBox parentVBox;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPassword;

    @FXML void signIn(ActionEvent event) {
        if (authRepository != null) {
            try {
                authRepository.login(textFieldEmail.getText(), textFieldPassword.getText());
            } catch (Exception ex) {
                // TODO: show incorrect password msg
                System.out.println("Incorrect email or password!");
            }
        }
    }

    @FXML void signUp(ActionEvent event) {

    }

    @FXML void initialize() {
        StackPane.setAlignment(parentVBox, Pos.CENTER);
    }

    public void init(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


}
