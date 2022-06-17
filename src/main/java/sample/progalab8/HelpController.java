package sample.progalab8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Locale;
import java.util.ResourceBundle;

public class HelpController {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());

    @FXML
    private Label help_label;

    @FXML
    private Label message_field;

    @FXML
    private Button okButton;

    @FXML
    void ok(ActionEvent event) {
        okButton.getScene().getWindow().hide();
    }

    void setLabel(){
        help_label.setText(res.getString("help"));
    }

}
