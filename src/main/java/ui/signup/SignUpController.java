package ui.signup;

import base.BaseController;
import data.model.User;
import data.repositories.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ui.Main;

public class SignUpController extends BaseController {
    private AuthRepository authRepository;

    @FXML private VBox parentVBox;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldSurname;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPassword;
    @FXML private TextField textFieldPasswordConfirm;

    @FXML void signIn(ActionEvent event) {
        Main.getNavigationManager().showSignInScreen();
    }

    @FXML
    void signUp(ActionEvent event) {
        try {
            if (validate()) {
                User user = authRepository.signUp(
                    textFieldName.getText(),
                    textFieldSurname.getText(),
                    textFieldEmail.getText(),
                    textFieldPassword.getText()
                );

                System.out.println("User: " + user);

                if (user != null) {
                    // TODO: start moderator view
                    Main.getNavigationManager().showMessage("Катталдыңыз!");
                }
            }
        } catch (Exception ex) {
            // TODO: Handle sign up filed
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void init(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    private boolean validate() {
        boolean result = true;

        if (!textFieldPassword.getText().equals(textFieldPasswordConfirm.getText())) {
            // TODO: show error message and handle all validations
            result = false;
        }

        return result;
    }
}
