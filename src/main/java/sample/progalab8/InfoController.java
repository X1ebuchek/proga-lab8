package sample.progalab8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InfoController {

    @FXML
    private Label info_label;

    @FXML
    private Label message_field;

    @FXML
    private Button okButton;

    @FXML
    void ok(ActionEvent event) {
        okButton.getScene().getWindow().hide();
    }
    void setLabel(String s){
        info_label.setText(s);
    }

}
