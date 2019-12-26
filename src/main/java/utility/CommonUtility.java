package utility;

import data.model.Language;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.List;
import java.util.Map;

import static utility.TranslationUtility.kyrgyzTranslation;

public class CommonUtility {

    public static String formatListString(List<String> words) {
        StringBuilder sb = new StringBuilder();
        for (String st : words) {
            sb.append("- ");
            sb.append(st);
            sb.append('\n');
        }
        return sb.toString();
    }

    public static String formatLanguageTranslations(Map<Language, List<String>> translations) {
        StringBuilder sb = new StringBuilder();

        for (Language lang : translations.keySet()) {
            sb.append(kyrgyzTranslation(lang));
            sb.append('\n');

            List<String> trans = translations.get(lang);
            sb.append("    - ");
            for (int i = 0; i < trans.size(); i++) {
                if (i != 0) sb.append(", ");
                sb.append(trans.get(i));
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public interface OnLanguageChangedListener {
        void onLanguageChanged(Language language);
    }
}
