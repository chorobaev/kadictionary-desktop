package ui.login;

import base.BaseController;
import data.model.User;
import data.repositories.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ui.Navigation;

import java.sql.SQLException;

public class SignUpController extends BaseController {
    private Navigation navigation;
    private AuthRepository authRepository;
    private OnAuthorizedListener authorizedListener;
    private AuthDialogControl authDialogControl;

    @FXML private VBox parentVBox;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldSurname;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPassword;
    @FXML private TextField textFieldPasswordConfirm;

    @FXML
    void signIn(ActionEvent event) {
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
            checkIfEmailExists(ex);
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void checkIfEmailExists(Exception ex) {
        if (ex instanceof SQLException && ((SQLException) ex).getErrorCode() == 1062) {
            System.out.println("Error code: " + ((SQLException) ex).getErrorCode());
            if (navigation != null) {
                String msg = "Эл. почта '" + textFieldEmail.getText() + "' системада катталган!";
                navigation.showMessage(msg);
            }
        }
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

    private boolean validate() {
        boolean result = true;
        StringBuilder sb = new StringBuilder();
        int c = 0;

        if (textFieldEmail.getText().length() <= 5) {
            sb.append(++c);
            sb.append(". ");
            sb.append("Эл. почта 5 белгиден узак болуш керкек!\n");
            result = false;
        }

        if (textFieldName.getText().length() <= 0) {
            sb.append(++c);
            sb.append(". ");
            sb.append("Аты бош болбошу керкек!\n");
            result = false;
        }

        if (textFieldSurname.getText().length() <= 0) {
            sb.append(++c);
            sb.append(". ");
            sb.append("Атасынын аты бош болбошу керкек!\n");
            result = false;
        }

        if (textFieldPassword.getText().length() <= 5) {
            sb.append(++c);
            sb.append(". ");
            sb.append("Сыр сөз 5 белгиден узак болуш керкек!\n");
            result = false;
        }

        if (!textFieldPassword.getText().equals(textFieldPasswordConfirm.getText())) {
            sb.append(++c);
            sb.append(". ");
            sb.append("Окшош келбеген сыр сөздөр.\n");
            result = false;
        }

        if (!result && navigation != null) {
            navigation.showMessage(sb.toString());
        }

        return result;
    }
}
