module com.example.dictionaryappversion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires freetts;


    opens com.example.dictionaryappversion to javafx.fxml;
    exports com.example.dictionaryappversion;
    exports Controller;
    opens Controller to javafx.fxml;
}