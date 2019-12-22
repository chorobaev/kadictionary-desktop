package ui.login;

import data.model.User;
import data.repositories.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import base.BaseController;
import ui.Navigation;

public class SingInController extends BaseController {
    private Navigation navigation;
    private AuthRepository authRepository;
    private OnAuthorizedListener authorizedListener;
    private AuthDialogControl authDialogControl;

    @FXML private VBox parentVBox;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPassword;

    @FXML void signIn(ActionEvent event) {
        if (authRepository != null) {
            try {
                User user = authRepository.singIn(textFieldEmail.getText(), textFieldPassword.getText());
                if (user != null) {
                    authorizedListener.onAuthorized(user);
                } else {
                    showIncorrectMsg();
                }
            } catch (Exception ex) {
                showIncorrectMsg();
                System.err.println(ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
    }

    private void showIncorrectMsg() {
        if (navigation != null) {
            navigation.showMessage("Incorrect email or password!");
        }
    }

    @FXML void signUp(ActionEvent event) {
        if (authDialogControl != null) {
            authDialogControl.goSignUpScreen();
        }
    }

    @FXML void initialize() {
        StackPane.setAlignment(parentVBox, Pos.CENTER);
    }

    public void init(
        Navigation navigation,
        AuthRepository authRepository,
        OnAuthorizedListener listener,
        AuthDialogControl authDialogControl
    ) {
        this.navigation = navigation;
        this.authRepository = authRepository;
        this.authorizedListener = listener;
        this.authDialogControl = authDialogControl;
    }
}
