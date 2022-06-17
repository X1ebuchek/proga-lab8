package sample.progalab8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Remove_Any_By_PriceController implements Initializable {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    UDPClient udpClient = new UDPClient();

    @FXML
    private TextField id_field;

    @FXML
    private Label message_field;

    @FXML
    private Button removeButton;

    @FXML
    private Label price;

    @FXML
    void remove(ActionEvent event) {
        try {
            Double price = Double.parseDouble(id_field.getText());
            SendThing sendThing = udpClient.send(null,"remove_any_by_price",price.toString(),UDPClient.login);
            Answer answer = udpClient.toServer(sendThing);
            MainController.answer = answer.getS();
            MainController.list = answer.getList();
        }catch (Exception e){
            message_field.setText("Некорректный ввод");
            id_field.setStyle("-fx-border-color: #ec4f11;");
        }
        removeButton.getScene().getWindow().hide();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        price.setText(res.getString("price"));
        removeButton.setText(res.getString("removeButton"));
    }
}
