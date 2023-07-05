package Controller;

import Model.Word;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    DictionaryService dictionaryService;
    {
        try {
            dictionaryService = new DictionaryService();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display modal to add word.
     */
    public static void open() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Word");
        window.setResizable(false);

        FXMLLoader loader = new FXMLLoader(DictionaryApplication.class.getResource("AddDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }

    @FXML
    private TextField wordTarget;

    @FXML
    private TextField Phonetics;

    @FXML
    private TextArea wordExplain;

    @FXML
    private Button addWord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addWord.setOnAction(addNewWord);
    }

    /**
     * Add word and display error or success.
     */
    private EventHandler<ActionEvent> addNewWord = (e) -> {
        String word_Target = wordTarget.getText().trim();
        String phonetic = Phonetics.getText();
        String word_Explain = wordExplain.getText();
        if (word_Target.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to fill word target");
            alert.showAndWait();
        } else {
            try {
                if (dictionaryService.checkExist(word_Target)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Word target has already existed");
                    alert.showAndWait();
                } else {
                    dictionaryService.insert(new Word(word_Target, phonetic, word_Explain));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setContentText("Successful add");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    };
}
