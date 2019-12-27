package utility;

import data.model.Language;

public class TranslationUtility {

    public static String kyrgyzTranslation(Language language) {
        StringBuilder sb = new StringBuilder();
        switch (language) {
            case KYRGYZ:
                sb.append("Кыргызча ");
                break;
            case ARABIC:
                sb.append("Арапча ");
                break;
            default:
                throw new IllegalArgumentException("No such language: " + language);
        }
        sb.append("котормосу: ");
        return sb.toString();
    }

}
