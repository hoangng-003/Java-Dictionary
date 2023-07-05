package Service;

import Model.Word;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class TestService {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        DictionaryService dictionaryService = new DictionaryService();
        System.out.print(dictionaryService.getWord("apple").getWord_explain());

    }
}
