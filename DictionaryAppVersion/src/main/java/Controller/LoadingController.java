package Controller;

import Model.LoadFileTask;
import com.example.dictionaryappversion.DictionaryApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoadingController {
    @FXML
    private Label status;

    @FXML
    private ProgressIndicator progress;

    /**
     * open loading modal.
     */
    public static void open(String path) throws Exception {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Loading");
        window.setResizable(false);

        FXMLLoader loader = new FXMLLoader(DictionaryApplication.class.getResource("LoadingDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);

        LoadFileTask task = new LoadFileTask(path);
        task.setOnSucceeded(e -> {
            window.close();
        });
        new Thread(task).start();
        window.show();
    }

}
