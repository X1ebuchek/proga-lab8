package sample.progalab8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Count_By_TypeController implements Initializable {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    UDPClient udpClient = new UDPClient();

    @FXML
    private Button loginButton;

    @FXML
    private ChoiceBox<String> login_field;

    @FXML
    private Label message_field;

    @FXML
    void login(ActionEvent event) {
        String type = login_field.getSelectionModel().getSelectedItem();
        type = type.toUpperCase();
        if (type.equals("VIP") || type.equals("USUAL") || type.equals("CHEAP")){
            SendThing sendThing = udpClient.send(null,"count_by_type",type,UDPClient.login);
            Answer answer = udpClient.toServer(sendThing);
            System.out.println(answer);
            //System.out.println(answer.toString());
            message_field.setText(answer.getS());
        }
        else {
            login_field.setStyle("-fx-border-color: #ec4f11;");
            message_field.setText("Некорректный ввод");
        }
        //loginButton.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_field.getItems().setAll("VIP","USUAL","CHEAP");
        login_field.setValue("VIP");
        loginButton.setText(res.getString("countButton"));
    }
}
