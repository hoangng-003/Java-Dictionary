package Model;

import Service.DictionaryService;
import javafx.concurrent.Task;

import java.sql.SQLException;

public class LoadFileTask extends Task<Void> {
    private String path;
    DictionaryService dictionaryService = new DictionaryService();

    public LoadFileTask() throws SQLException, ClassNotFoundException {

    }
    public LoadFileTask(String path) throws SQLException, ClassNotFoundException {
        this.path = path;
    }

    @Override
    protected Void call() throws Exception {
        updateMessage("Loading");
        dictionaryService.insertFromFile(path);
        return null;
    }
}
