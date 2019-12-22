package ui.login;

import base.BaseController;
import data.model.User;
import data.repositories.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SignUpController extends BaseController {
    private AuthRepository authRepository;
    private OnAuthorizedListener authorizedListener;
    private AuthDialogControl authDialogControl;

    @FXML private VBox parentVBox;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldSurname;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPassword;
    @FXML private TextField textFieldPasswordConfirm;

    @FXML void signIn(ActionEvent event) {
        if (authDialogControl != null) {
            authDialogControl.goSignInScreen();
        }
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
                    authorizedListener.onAuthorized(user);
                }
            }
        } catch (Exception ex) {
            // TODO: Handle sign up filed
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void init(AuthRepository authRepository, OnAuthorizedListener listener, AuthDialogControl authDialogControl) {
        this.authRepository = authRepository;
        this.authorizedListener = listener;
        this.authDialogControl = authDialogControl;
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
