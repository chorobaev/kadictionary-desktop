package data;

import data.model.KAWord;
import data.model.Language;
import data.model.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccess {
    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String connectionString = "jdbc:mysql://localhost/kadictionary?"
        + "user=javauser&password=&useSSL=false&useUnicode=yes&characterEncoding=UTF-8";

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private Language language = Language.KYRGYZ;
    private List<Word> words;

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Word> getWords() {
        return words;
    }

    public void loadAllWords() throws Exception {
        try {
            openConnection();
            String query = "CALL getAll" + language + "Words();";
            resultSet = statement.executeQuery(query);
            parseWords();
        } finally {
            close();
        }
    }

    public void searchWord(String substring) throws Exception {
        try {
            openConnection();
            String query = "CALL search" + language + "Word('" + substring + "');";
            resultSet = statement.executeQuery(query);
            parseWords();
        } finally {
            close();
        }
    }

    private void parseWords() throws SQLException {
        words = new ArrayList<>();
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

    public void readDataBase() throws Exception {
        try {
            Class.forName(jdbcDriver);
            // Setup the connection with the DB
            connect = DriverManager.getConnection(connectionString);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                .executeQuery("select * from kadictionary.kyrgyz_words");

            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                .prepareStatement("insert into  kadictionary.kyrgyz_words values (default, ?, default)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "жаңгак");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                .prepareStatement("SELECT * from kadictionary.kyrgyz_words");
            resultSet = preparedStatement.executeQuery();

//            // Remove again the insert comment
//            preparedStatement = connect
//            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
//            preparedStatement.setString(1, "Test");
//            preparedStatement.executeUpdate();
//
//            resultSet = statement
//            .executeQuery("select * from feedback.comments");
//            writeMetaData(resultSet);

        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
        }
    }
}