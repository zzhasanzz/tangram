module com.example.tangram {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.locationtech.jts;
    requires java.sql;


    opens com.example.tangram to javafx.fxml;
    exports com.example.tangram;
}