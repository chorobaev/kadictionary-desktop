package ui.login;

import data.model.User;
import data.repositories.AuthRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import ui.Navigation;

import java.util.Optional;

public class AuthenticationDialog extends Dialog<User> implements AuthDialogControl {
    private final AuthRepository authRepository;
    private final Navigation navigation;
    private User user;
    private OnAuthorizedListener authorizedListener = newUser -> {
        this.user = newUser;
        setResult(user);
    };

    public static Optional<User> show(Navigation navigation, AuthRepository authRepository) {
        return new AuthenticationDialog(navigation, authRepository).showAndWait();
    }

    private AuthenticationDialog(Navigation navigation, AuthRepository authRepository) {
        this.navigation = navigation;
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
        setTitle("Кирүү");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/login.fxml"));
            Parent parent = loader.load();
            SingInController controller = loader.getController();
            controller.init(navigation, authRepository, authorizedListener, this);
            getDialogPane().setContent(parent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void goSignUpScreen() {
        setTitle("Катталуу");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/signup.fxml"));
            Parent parent = loader.load();
            SignUpController controller = loader.getController();
            controller.init(navigation, authRepository, authorizedListener, this);
            getDialogPane().setContent(parent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
