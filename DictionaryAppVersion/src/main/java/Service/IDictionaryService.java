package Service;

import Model.Word;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface IDictionaryService {
    boolean checkExist(String word) throws SQLException;
    boolean insert(Word word) throws SQLException;
    boolean delete(String word) throws SQLException;
    List<String> allWord() throws SQLException;
    List<String> prefixWord(String word) throws SQLException;
    Word getWord(String word) throws SQLException;
    void deleteAll() throws SQLException;
    void insertFromFile(String path) throws SQLException, FileNotFoundException;
}
