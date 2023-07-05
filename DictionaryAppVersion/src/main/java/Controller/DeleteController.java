package Controller;

import Service.DictionaryService;
import com.example.dictionaryappversion.DictionaryApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteController implements Initializable {

    DictionaryService dictionaryService;

    {
        try {
            dictionaryService = new DictionaryService();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField deleteWord;
    @FXML
    private Button removeBtn;

    /**
     * Display delete modal.
     */
    public static void open() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete Word");
        window.setResizable(false);

        FXMLLoader loader = new FXMLLoader(DictionaryApplication.class.getResource("DeleteDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removeBtn.setOnAction(delete);
    }

    /**
     * Delete and show error or success.
     */
    private EventHandler<ActionEvent> delete = (e) -> {
        String word = deleteWord.getText();
        try {
            if (dictionaryService.checkExist(word)) {
                try {
                    dictionaryService.delete(word);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setContentText("Successful delete");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Word target doesn't exist");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    };
}
