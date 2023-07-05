package Controller;

import com.example.dictionaryappversion.DictionaryApplication;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    /**
     * Display contributor.
     */
    public static void open() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("About us");
        window.setResizable(false);
        FXMLLoader loader = new FXMLLoader(DictionaryApplication.class.getResource("AboutDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
