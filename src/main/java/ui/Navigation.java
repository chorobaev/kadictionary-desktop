package ui;

import data.model.Language;

public interface Navigation {

    void showMainView();

    void showAuthDialog();

    void showEditView();

    void showMessage(String msg);

    void showNewWordDialog(Language language, String word);
}
