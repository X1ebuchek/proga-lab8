package sample.progalab8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Remove_GreaterController implements Initializable {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    UDPClient udpClient = new UDPClient();
    //MainController mainController = new MainController();

    @FXML
    private Button removeButton;

    @FXML
    private TextField comment_field;

    @FXML
    private TextField description_field;

    @FXML
    private ChoiceBox<String> eventType_field;

    @FXML
    private TextField name1_field;

    @FXML
    private TextField name_field;

    @FXML
    private Label error_label;

    @FXML
    private TextField price_field;

    @FXML
    private ChoiceBox<String> refundable_field;

    @FXML
    private ChoiceBox<String> type_field;

    @FXML
    private TextField x_field;

    @FXML
    private TextField y_field;

    @FXML
    private Label comment;

    @FXML
    private Label description;

    @FXML
    private Label eventType;

    @FXML
    private Label name;

    @FXML
    private Label name1;

    @FXML
    private Label price;

    @FXML
    private Label refundable;

    @FXML
    private Label type;

    @FXML
    void remove(ActionEvent event) {
        boolean error = false;
        Long id = System.currentTimeMillis();

        String name = name_field.getText();
        name_field.setStyle("-fx-border-color: #c0c0c0;");
        if (name.isEmpty()){
            name_field.setStyle("-fx-border-color: #ec4f11;");
            error = true;
        }
        float x = 0;
        try {
            x = Float.parseFloat(x_field.getText());
            if (x<=-536){
                x_field.setStyle("-fx-border-color: #ec4f11;");
                error = true;
            }else x_field.setStyle("-fx-border-color: #c0c0c0;");
        }catch (Exception e){
            x_field.setStyle("-fx-border-color: #ec4f11;");
            error = true;
        }
        long y = 0;
        try {
            y = Long.parseLong(y_field.getText());
            y_field.setStyle("-fx-border-color: #c0c0c0;");
        }catch (Exception e){
            y_field.setStyle("-fx-border-color: #ec4f11;");
            error = true;
        }
        Double price = 0d;
        try {
            price = Double.parseDouble(price_field.getText());
            if (price<=0){
                price_field.setStyle("-fx-border-color: #ec4f11;");
                error = true;
            }else price_field.setStyle("-fx-border-color: #c0c0c0;");
        }catch (Exception e){
            price_field.setStyle("-fx-border-color: #ec4f11;");
            error = true;
        }

        String comment = comment_field.getText();
        if (comment.isEmpty()) comment = null;

        String s = refundable_field.getSelectionModel().getSelectedItem();
        boolean refundable = Boolean.parseBoolean(s);
//        s = s.toLowerCase();
//        if (!(s.equals("true") || s.equals("false"))){
//            refundable_field.setStyle("-fx-border-color: #ec4f11;");
//            error = true;
//        }else {
//            refundable = Boolean.parseBoolean(s);
//            refundable_field.setStyle("-fx-border-color: #c0c0c0;");
//        }

        String type = type_field.getSelectionModel().getSelectedItem();
//        type = type.toUpperCase();
//        type_field.setStyle("-fx-border-color: #c0c0c0;");
//        if (type.isEmpty() || !(type.equals("VIP") || type.equals("USUAL") || type.equals("CHEAP"))){
//            type_field.setStyle("-fx-border-color: #ec4f11;");
//            error = true;
//        }

        String name1 = name1_field.getText();
        name1_field.setStyle("-fx-border-color: #c0c0c0;");
        if (name1.isEmpty()){
            name1_field.setStyle("-fx-border-color: #ec4f11;");
            error = true;
        }
        String description = description_field.getText();
        if (description.isEmpty()) description = null;

        String eventType = eventType_field.getSelectionModel().getSelectedItem();
//        eventType = eventType.toUpperCase();
//        eventType_field.setStyle("-fx-border-color: #c0c0c0;");
//        if (!(eventType.equals("BASEBALL") || eventType.equals("BASKETBALL") || eventType.equals("THEATRE_PERFORMANCE") || eventType.equals("EXPOSITION") || eventType.isEmpty())){
//            eventType_field.setStyle("-fx-border-color: #ec4f11;");
//            error = true;
//        }
        Long id1 = System.currentTimeMillis()+1;
        if (error){
            error_label.setText("Некорректный ввод");
        }else {
            Coordinates c = new Coordinates(x,y);
            Event e;
            if (eventType.isEmpty()) e = new Event(id1, name1, description, null);
            else e = new Event(id1, name1, description, EventType.valueOf(eventType));

            Ticket ticket = new Ticket(id, name, c, new Date(), price, comment, refundable, TicketType.valueOf(type), e, UDPClient.login);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            try {
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MainController controller = loader.getController();
            SendThing sendThing = udpClient.send(ticket,"remove_greater","",UDPClient.login);
            Answer answer = udpClient.toServer(sendThing);
            controller.answer = answer.getS();
            controller.ticket = ticket;
            MainController.list = answer.getList();

            //mainController.addToTable(ticket);
            removeButton.getScene().getWindow().hide();
            //System.out.println(ticket.toString());

        }

    }
    void setChoiceBox(){
        type_field.getItems().setAll("VIP","USUAL","CHEAP");
        type_field.setValue("VIP");
        eventType_field.getItems().setAll("BASEBALL","BASKETBALL","THEATRE_PERFORMANCE","EXPOSITION");
        eventType_field.setValue("BASEBALL");
        refundable_field.getItems().setAll("true","false");
        refundable_field.setValue("true");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(res.getString("name"));
        price.setText(res.getString("price"));
        comment.setText(res.getString("comment"));
        refundable.setText(res.getString("refundable"));
        type.setText(res.getString("type"));
        name1.setText(res.getString("name1"));
        description.setText(res.getString("description"));
        eventType.setText(res.getString("eventType"));
        removeButton.setText(res.getString("removeButton"));
    }
}
