module com.example.trs2lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.trs2lab4 to javafx.fxml;
    exports com.example.trs2lab4;
}