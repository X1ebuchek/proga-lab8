module sample.progalab8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires slf4j.api;
    requires java.desktop;


    opens sample.progalab8 to javafx.fxml;
    exports sample.progalab8;
}