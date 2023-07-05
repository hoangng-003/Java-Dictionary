package Service;

import Connectivity.ConnectionClass;
import Model.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryService implements IDictionaryService {
    private Connection connection;

    private String formatWord(String word) {
        return '"' + word + '"';
    }

    /**
     * Set connection to database
     */
    public DictionaryService() throws SQLException, ClassNotFoundException {
        ConnectionClass connectionClass = new ConnectionClass();
        connection = connectionClass.getconnection();
    }

    /**
     * Check exist of word.
     *
     * @param word:word.
     */
    @Override
    public boolean checkExist(String word) throws SQLException {
        String query = "SELECT * FROM `word` WHERE wordtarget = " + formatWord(word);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next();
    }

    /**
     * Check exist and insert new word.
     */
    @Override
    public boolean insert(Word word) throws SQLException {
        if (checkExist(word.getWord_target())) return false;
        String query = "INSERT INTO `word` (`id`, `wordtarget`, `phonetic`, `wordexplain`) VALUES (NULL," +
                " " + formatWord(word.getWord_target()) + ", "
                + formatWord(word.getPhonetic())
                + ", "
                + formatWord(word.getWord_explain())
                + ")";
//        System.out.print(query);
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        return true;
    }

    /**
     * Check exist and delete word.
     */
    @Override
    public boolean delete(String word) throws SQLException {
        if (!checkExist(word)) return false;
        String query = "DELETE FROM `word` WHERE wordtarget = " + formatWord(word);
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        return true;
    }

    /**
     * Show all word in dictionary.
     */
    @Override
    public List<String> allWord() throws SQLException {
        List<String> ans = new ArrayList<String>();
        String query = "SELECT * FROM `word`";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            ans.add(resultSet.getString("wordtarget"));
        }
        return ans;
    }

    /**
     * Get list of word that have prefix equal input word.
     *
     * @param word: word.
     */
    @Override
    public List<String> prefixWord(String word) throws SQLException {
        String query = "SELECT * FROM `word` WHERE wordtarget LIKE '" + word + "%'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<String> ans = new ArrayList<String>();
        while (resultSet.next()) {
            ans.add(resultSet.getString("wordtarget"));
        }
        return ans;
    }

    /**
     * Get word information.
     *
     * @param word: word.
     */
    @Override
    public Word getWord(String word) throws SQLException {
        if (!checkExist(word)) return null;
        String query = "SELECT * FROM `word` WHERE wordtarget = " + formatWord(word);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Word ans = null;
        while (resultSet.next()) {
            ans = new Word(resultSet.getString("wordtarget"),
                    resultSet.getString("phonetic"),
                    resultSet.getString("wordexplain"));
        }
        return ans;
    }

    /**
     * Insert database function.
     *
     * @param path: database path.
     */
    @Override
    public void insertFromFile(String path) throws SQLException, FileNotFoundException {
        Scanner readFromFile = new Scanner(new File(path));
        while (readFromFile.hasNext()) {
            String curLine = readFromFile.nextLine();
            if (curLine.startsWith("@")) {
                Word word = new Word();
                String[] part = curLine.split("/", 2);
                String englishWord = part[0].substring(1).trim();
                if (englishWord.startsWith("'") || englishWord.startsWith("-") || englishWord.startsWith("(")) {
                    englishWord = englishWord.substring(1);
                }
                word.setWord_target(englishWord);
                if (part.length < 2) {
                    word.setPhonetic("");
                } else word.setPhonetic("/" + part[1]);
                StringBuilder wordExplain = new StringBuilder();
                while (readFromFile.hasNext()) {
                    String description = readFromFile.nextLine();
                    description = description.replaceAll("\"", "");
                    if (description.equals("")) {
                        word.setWord_explain(String.valueOf(wordExplain));
                        insert(word);
                        break;
                    } else {
                        wordExplain.append(description).append('\n');
                    }
                }
            }
        }
    }

    /**
     * Clear dictionary.
     */
    @Override
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM `word`";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}
