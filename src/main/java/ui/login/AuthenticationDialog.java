package ui.login;

import data.model.User;
import data.repositories.AuthRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;

import java.util.Optional;

public class AuthenticationDialog extends Dialog<User> implements AuthDialogControl {
    private AuthRepository authRepository;
    private User user;
    private OnAuthorizedListener authorizedListener = newUser -> {
        this.user = newUser;
        setResult(user);
    };

    public static Optional<User> show(AuthRepository authRepository) {
        return new AuthenticationDialog(authRepository).showAndWait();
    }

    private AuthenticationDialog(AuthRepository authRepository) {
        this.authRepository = authRepository;
        initView();
    }

    private void initView() {
        setWidth(400);
        setHeight(260);
        goSignInScreen();
        setResultConverter(buttonType -> user);
        initModality(Modality.APPLICATION_MODAL);

        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
    }

    @Override
    public void goSignInScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/login.fxml"));
            Parent parent = loader.load();
            LoginController controller = loader.getController();
            controller.init(authRepository, authorizedListener, this);
            getDialogPane().setContent(parent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void goSignUpScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/signup.fxml"));
            Parent parent = loader.load();
            SignUpController controller = loader.getController();
            controller.init(authRepository, authorizedListener, this);
            getDialogPane().setContent(parent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
