package ui.login;

import data.model.User;
import data.repositories.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import base.BaseController;
import ui.Main;

public class LoginController extends BaseController {
    private AuthRepository authRepository;

    @FXML private VBox parentVBox;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPassword;

    @FXML void signIn(ActionEvent event) {
        if (authRepository != null) {
            try {
                User user = authRepository.login(textFieldEmail.getText(), textFieldPassword.getText());
                if (user != null) {
                    // TODO: start moderator screen
                    Main.getNavigationManager().showMessage("Signed in!");
                }
            } catch (Exception ex) {
                // TODO: show incorrect password msg
                System.out.println("Incorrect email or password!");
                System.err.println(ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
    }

    @FXML void signUp(ActionEvent event) {
        Main.getNavigationManager().showSignUp();
    }

    @FXML void initialize() {
        StackPane.setAlignment(parentVBox, Pos.CENTER);
    }

    public void init(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


}
