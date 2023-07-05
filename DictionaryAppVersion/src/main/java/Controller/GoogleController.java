package Controller;

import Service.Speak;
import com.example.dictionaryappversion.DictionaryApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class GoogleController implements Initializable {
    Speak Speaker = new Speak();

    @FXML
    private Button engToVie;

    @FXML
    private Button vieToEng;

    @FXML
    private TextArea input;

    @FXML
    private TextArea output;

    @FXML
    private Button speak1;

    @FXML
    private Button speak2;

    /**
     * open Google modal.
     */
    public static void open() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Google API");
        window.setResizable(false);
        FXMLLoader loader = new FXMLLoader(DictionaryApplication.class.getResource("GoogleDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * route button to function.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        output.setEditable(false);
        engToVie.setOnAction(translateEngToVie);
        vieToEng.setOnAction(translateVieToEng);
        speak1.setOnAction(playSpeak1);
        speak2.setOnAction(playSpeak2);
    }

    /**
     * @param langFrom: text translate.
     * @param langTo:   text in.
     * @param text:     text to translate.
     */
    private String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbxgsmcozx3kWH3VSVzcMnUXcfFUasHLkOKOY0DzWkVAnreEmnubk6sGF7Mk_lG4dh8w/exec"
                +
                "?q=" + URLEncoder.encode(text, "UTF-8")
                + "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    /**
     * Translate English to VietNamese.
     */
    private EventHandler<ActionEvent> translateEngToVie = (e) -> {
        try {
            output.setText(translate("en", "vi", input.getText()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };

    /**
     * translate VietNamese to English
     */
    private EventHandler<ActionEvent> translateVieToEng = (e) -> {
        try {
            String ans = translate("vi", "en", input.getText());
            output.setText(ans);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };

    /**
     * speak input.
     */
    private EventHandler<ActionEvent> playSpeak1 = (e) -> {
        Speaker.speak(input.getText());
    };

    /**
     * speak output
     */
    private EventHandler<ActionEvent> playSpeak2 = (e) -> {
        Speaker.speak(output.getText());
    };
}
