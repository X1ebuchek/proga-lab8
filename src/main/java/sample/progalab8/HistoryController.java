package sample.progalab8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HistoryController {

    @FXML
    private Label history_label;

    @FXML
    private Label message_field;

    @FXML
    private Button okButton;

    @FXML
    void ok(ActionEvent event) {
        okButton.getScene().getWindow().hide();
    }

    void setLabel(String s1, String s2, String s3, String s4, String s5, String s6){
        history_label.setText(s1+ "\n" + s2 + "\n" + s3 + "\n" + s4 + "\n" + s5 + "\n" + s6);
    }
}
