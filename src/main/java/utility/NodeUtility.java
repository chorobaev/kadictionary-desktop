package utility;

import data.model.Language;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ui.NavigationManager;
import ui.common.DisplayController;
import ui.common.WordController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NodeUtility {
    private static NodeUtility instance;

    public static NodeUtility getInstance() {
        if (instance == null) {
            instance = new NodeUtility();
        }
        return instance;
    }

    private NodeUtility() {
    }

    public VBox getNewWordPane(WordController.WordInteractionListener listener) {
        VBox parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/words.fxml"));
            parent = loader.load();
            WordController controller = loader.getController();
            controller.init(listener);
        } catch (IOException ex) {
            Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parent;
    }

    public VBox getDisplayPane(DisplayController.DisplayInteractionListener listener) {
        VBox parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/display.fxml"));
            parent = loader.load();
            DisplayController controller = loader.getController();
            controller.init(listener);
        } catch (IOException ex) {
            Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parent;
    }

    public static void initLanguageMenuButton(MenuButton menuButton, CommonUtility.OnLanguageChangedListener listener) {
        ObservableList<MenuItem> menuItems = menuButton.getItems();
        Language.getMenuItemsInKyrgyz().forEach(menuItem -> {
            menuItem.setOnAction(action -> {
                MenuItem menu = (MenuItem) action.getSource();
                listener.onLanguageChanged((Language) menu.getUserData());
                menuButton.setText(menu.getText());
            });
            menuItems.add(menuItem);
        });
        if (!menuItems.isEmpty()) {
            menuItems.get(0).fire();
        }
    }

    public static void setAnchorsZero(Node node) {
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }
}
