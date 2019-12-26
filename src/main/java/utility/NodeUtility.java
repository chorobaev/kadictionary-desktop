package utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ui.NavigationManager;
import ui.edit.WordController;
import ui.edit.WordInteractionListener;

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

    public VBox getNewWordPane(WordInteractionListener listener) {
        VBox parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/new_word.fxml"));
            parent = loader.load();
            WordController controller = loader.getController();
            controller.init(listener);
        } catch (IOException ex) {
            Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parent;
    }

    public static void setAnchorsZero(Node node) {
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }
}
