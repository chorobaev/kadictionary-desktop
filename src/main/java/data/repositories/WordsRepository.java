package data.repositories;

import data.model.KAWord;
import data.model.Language;
import data.model.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsRepository {
    private static WordsRepository instance;
    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String connectionString = "jdbc:mysql://localhost/kadictionary?"
        + "user=javauser&password=&useSSL=false&useUnicode=yes&characterEncoding=UTF-8";

    public static WordsRepository getInstance() {
        if (instance == null) {
            instance = new WordsRepository();
        }
        return instance;
    }

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private Language language = Language.KYRGYZ;

    private WordsRepository() {
    }

    public void setLanguage(Language language) {
        System.out.println("New language: " + language);
        this.language = language;
    }

    public List<Word> getAllWords() throws Exception {
        List<Word> words = new ArrayList<>();
        try {
            openConnection();
            String query = "CALL getAll" + language + "Words();";
            resultSet = statement.executeQuery(query);
            parseWords(words);
        } finally {
            close();
        }
        return words;
    }

    public List<Word> searchWord(String substring) throws Exception {
        List<Word> words = new ArrayList<>();
        try {
            openConnection();
            String query = "CALL search" + language + "Word('" + substring + "');";
            resultSet = statement.executeQuery(query);
            parseWords(words);
        } finally {
            close();
        }
        return words;
    }

    public Map<Language, List<String>> getTranslationsByWordId(int wordId) throws Exception {
        Map<Language, List<String>> translations = new HashMap<>();
        System.out.println("Translate to:  " + language.getLanguagesExceptSelf());
        for (Language lan : language.getLanguagesExceptSelf()) {
            translations.put(lan, getTranslationsByWordId(wordId, lan));
        }
        return translations;
    }

    public List<String> getDescriptionByWordId(int wordId) throws Exception {
        List<String> result = new ArrayList<>();
        try {
            openConnection();
            String query = "CALL get" + language + "WordDescriptionsByWordId(" + wordId + ");";
            resultSet = statement.executeQuery(query);
            parseDescriptions(result);
        } finally {
            close();
        }
        return result;
    }

    private List<String> getTranslationsByWordId(int wordId, Language preferedLang) throws Exception {
        List<String> result = new ArrayList<>();
        try {
            openConnection();
            String query = "CALL get" + language + "Word" + preferedLang + "TranslationsByWordId(" + wordId + ");";
            System.out.println("Translation query: " + query);
            resultSet = statement.executeQuery(query);
            parseTranslations(result);
        } finally {
            close();
        }
        return result;
    }

    private void parseWords(List<Word> words) throws SQLException {
        if (resultSet != null) {
            while (resultSet.next()) {
                int wordId = resultSet.getInt("wordID");
                String word = resultSet.getString("word");
                int correctness = resultSet.getInt("correctness");
                Word kaWord = new KAWord(wordId, word, correctness, language);
                words.add(kaWord);
            }
        }
    }

    private void parseDescriptions(List<String> list) throws SQLException {
        if (resultSet != null) {
            while (resultSet.next()) {
                String string = resultSet.getString("description");
                list.add(string);
            }
        }
    }

    private void parseTranslations(List<String> list) throws SQLException {
        if (resultSet != null) {
            while (resultSet.next()) {
                String translation = resultSet.getString("translation");
                list.add(translation);
            }
        }
    }

    private void openConnection() throws Exception {
        Class.forName(jdbcDriver);
        connect = DriverManager.getConnection(connectionString);
        statement = connect.createStatement();
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception ignored) {
        }
    }
}