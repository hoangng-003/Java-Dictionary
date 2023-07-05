package Controller;

import Model.Word;
import Service.DictionaryService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {

    private DictionaryService dictionaryService = new DictionaryService();

    @FXML
    private AnchorPane Anchorid;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea definition;

    @FXML
    private Button About;

    @FXML
    private Button searchBtn;

    @FXML
    private Button Add;

    @FXML
    private Button Delete;

    @FXML
    private Button Google;

    @FXML
    private Button Refresh;

    @FXML
    private Button Import;

    public DictionaryController() throws FileNotFoundException, SQLException, ClassNotFoundException {
    }

    private String getSelectedWord() {
        return listView.getSelectionModel().getSelectedItem();
    }

    /**
     * get word in search bar and search.
     */
    private void search() throws SQLException {
        listView.getItems().clear();
        listView.getItems().addAll(dictionaryService.prefixWord(searchBar.getText()));
    }

    /**
     * Route button to function and set default setting.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listView.getItems().addAll(dictionaryService.allWord());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        definition.setEditable(false);
        searchBar.setOnKeyReleased(e -> {
            try {
                search();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        About.setOnAction(openAbout);
        searchBtn.setOnAction(searchClick);
        Add.setOnAction(openAdd);
        Delete.setOnAction(openDelete);
        Google.setOnAction(openGoogle);
        Refresh.setOnAction(setRefresh);
        Import.setOnAction(setImport);
    }

    /**
     * Display definition.
     */
    private void setDefinition() throws SQLException {
        definition.clear();
        String englishWord = searchBar.getText();
        if (dictionaryService.checkExist(englishWord)) {
            Word word = dictionaryService.getWord(englishWord);
            definition.appendText(word.getPhonetic() + '\n');
            definition.appendText(word.getWord_explain());
        } else {
            definition.appendText("Dictionary doesn't have this word" + '\n');
            definition.appendText("Click add button to add word");
        }
    }

    /**
     * Set definition while click word twice.
     */
    public void onMouseClick(MouseEvent mouseEvent) throws SQLException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            searchBar.setText(getSelectedWord());
            if (mouseEvent.getClickCount() == 2) {
                setDefinition();
            }
        }
    }

    /**
     * Set definition while click search button.
     */
    private EventHandler<ActionEvent> searchClick = (e) -> {
        try {
            setDefinition();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    };

    /**
     * open About.
     */
    private EventHandler<ActionEvent> openAbout = (e) -> {
        try {
            AboutController.open();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };

    /**
     * open Add modal.
     */

    private EventHandler<ActionEvent> openAdd = (e) -> {
        try {
            AddController.open();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };

    /**
     * open Delete modal.
     */

    private EventHandler<ActionEvent> openDelete = (e) -> {
        try {
            DeleteController.open();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };

    /**
     * open Google modal.
     */
    private EventHandler<ActionEvent> openGoogle = (e) -> {
        try {
            GoogleController.open();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };

    /**
     * Click to refresh to set new change.
     */
    private EventHandler<ActionEvent> setRefresh = (e) -> {
        listView.getItems().clear();
        try {
            listView.getItems().addAll(dictionaryService.allWord());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        searchBar.clear();
        definition.clear();
    };

    /**
     * Click to import dictionary database.
     */
    private EventHandler<ActionEvent> setImport = (e) -> {
        final FileChooser fileChooser = new FileChooser();

        Stage stage = (Stage) Anchorid.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                LoadingController.open(file.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

}