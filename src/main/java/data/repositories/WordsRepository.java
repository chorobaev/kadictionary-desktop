package data.repositories;

import base.BaseRepository;
import data.model.KAWord;
import data.model.Language;
import data.model.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsRepository extends BaseRepository {
    private static WordsRepository instance;

    public static WordsRepository getInstance() {
        if (instance == null) {
            instance = new WordsRepository();
        }
        return instance;
    }

    private WordsRepository() {
    }

    public List<Word> getAllWords(Language language) throws Exception {
        List<Word> words = new ArrayList<>();
        try {
            openConnection();
            String query = "CALL getAll" + language + "Words();";
            resultSet = statement.executeQuery(query);
            parseWords(language, words);
        } finally {
            close();
        }
        return words;
    }

    public List<Word> searchWord(Language language, String substring) throws Exception {
        List<Word> words = new ArrayList<>();
        try {
            openConnection();
            String query = "CALL search" + language + "Word('" + substring + "');";
            resultSet = statement.executeQuery(query);
            parseWords(language, words);
        } finally {
            close();
        }
        return words;
    }

    public Map<Language, List<Word>> getTranslationsByWordId(Language language, int wordId) throws Exception {
        Map<Language, List<Word>> translations = new HashMap<>();
        System.out.println("Translate to:  " + language.getLanguagesExceptSelf());
        for (Language lan : language.getLanguagesExceptSelf()) {
            translations.put(lan, getTranslationsByWordId(language, wordId, lan));
        }
        return translations;
    }

    public List<String> getDescriptionByWordId(Language language, int wordId) throws Exception {
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

    private List<Word> getTranslationsByWordId(Language language, int wordId, Language preferedLang) throws Exception {
        List<Word> result = new ArrayList<>();
        try {
            openConnection();
            String query = "CALL get" + language + "Word" + preferedLang + "TranslationsByWordId(" + wordId + ");";
            System.out.println("Translation query: " + query);
            resultSet = statement.executeQuery(query);
            parseWords(preferedLang, result);
        } finally {
            close();
        }
        return result;
    }

    private void parseWords(Language language, List<Word> words) throws SQLException {
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

    public void addWordTranslations(Word first, Word second) throws Exception {
        String firstLang = first.getLanguage().toString();
        String secondLang = second.getLanguage().toString();

        if (firstLang.compareTo(secondLang) > 0) {
            String temp = firstLang;
            firstLang = secondLang;
            secondLang = temp;

            Word word = first;
            first = second;
            second = word;
        }
        try {
            openConnection();
            String query = "CALL add" + firstLang + secondLang + "Matching(" + first.getId() + ", " + second.getId() + ");";
            statement.executeQuery(query);
        } finally {
            close();
        }
    }

    public void addWordWithDescription(Language language, String word, String desc) throws Exception {
        try {
            openConnection();
            statement.executeQuery("CALL add" + language + "Word('" + word + "');");
            if (!desc.isEmpty()) {
                resultSet = statement.executeQuery("CALL get" + language + "WordIdByWord('" + word + "');");
                int wordId = parseWordId();
                statement.executeQuery("CALL add" + language + "Description('" + desc + "', " + wordId + ");");
            }
        } finally {
            close();
        }
    }

    private int parseWordId() throws SQLException {
        int id = -1;
        if (resultSet != null && resultSet.next()) {
            id = resultSet.getInt("wordID");
        }
        return id;
    }
}