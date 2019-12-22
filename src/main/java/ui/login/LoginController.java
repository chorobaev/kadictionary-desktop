package ui.login;

import data.repositories.AuthRepository;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import base.BaseController;

public class LoginController extends BaseController {
    private AuthRepository authRepository;

    @FXML private VBox parentVBox;

    public void init(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @FXML void initialize() {
        StackPane.setAlignment(parentVBox, Pos.CENTER);
    }
}
