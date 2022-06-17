package sample.progalab8;

import javafx.application.Platform;
import java.awt.Desktop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable{
    static Scanner sc;
    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();
    UDPClient udpClient = new UDPClient();
    ObservableList<Ticket> ticketData = FXCollections.observableArrayList();
    static String answer = "";
    static Ticket ticket;
    static String date;
    static LinkedList<Ticket> list;
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());

    String s1 = ""; String s2 = ""; String s3 = ""; String s4 = ""; String s5 = ""; String s6 = "";

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Label login_now;

    @FXML
    private Button add_if_minButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button count_by_typeButton;

    @FXML
    private Button filter_less_than_commentButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button remove_any_by_priceButton;

    @FXML
    private Button remove_by_idButton;

    @FXML
    private Button remove_greaterButton;

    @FXML
    private Label message_label;

    @FXML
    private Button removeButton;

    @FXML
    private Button scriptButton;

    @FXML
    private Label infoLabel;

    @FXML
    private AnchorPane pane;

    @FXML
    private Tab coordinatesTab;

    @FXML
    private Tab tableTab;

    @FXML
    private TableView<Ticket> collectionTable;

    @FXML
    private TableColumn<Ticket, Long> idColumn;

    @FXML
    private TableColumn<Ticket, String> nameColumn;

    @FXML
    private TableColumn<Ticket, Float> xColumn;

    @FXML
    private TableColumn<Ticket, Long> yColumn;

    @FXML
    private TableColumn<Ticket, Date> creationDateColumn;

    @FXML
    private TableColumn<Ticket, Long> priceColumn;

    @FXML
    private TableColumn<Ticket, String> commentColumn;

    @FXML
    private TableColumn<Ticket, Boolean> refundableColumn;

    @FXML
    private TableColumn<Ticket, TicketType> typeColumn;

    @FXML
    private TableColumn<Ticket, Long> id1Column;

    @FXML
    private TableColumn<Ticket, String> name1Column;

    @FXML
    private TableColumn<Ticket, String> descriptionColumn;

    @FXML
    private TableColumn<Ticket, EventType> eventTypeColumn;

    Ticket temp;
    Date lastClickTime;
    @FXML
    void handleRowSelect(MouseEvent event) {
        Ticket row = collectionTable.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if(row != temp){
            temp = row;
            lastClickTime = new Date();
        } else {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300){
                FXMLLoader loader1 = new FXMLLoader();
                loader1.setLocation(getClass().getResource("update.fxml"));
                try {
                    loader1.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader1.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                UpdateController controller = loader1.getController();
                controller.insertValues(row);
                controller.setChoiceBox();
                controller.id = row.getId();
                controller.id1 = row.getId1();
                stage.showAndWait();

                ticketData.clear();
                for (int i = 0;i< list.size();i++){
                    addToTable(list.get(i));
                }
                message_label.setText(answer);
                setHistory("update");
            } else {
                lastClickTime = new Date();
            }
        }
    }

    @FXML
    void add(ActionEvent event) {
        //addButton.setCancelButton(false);
        addButton.setDisable(true);

        //addButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Add.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        AddController controller = loader.getController();
        controller.setChoiceBox();
        stage.showAndWait();
        //addButton.setCancelButton(true);
        addButton.setDisable(false);
        //System.out.println(ticket);
        ticketData.clear();
        for (int i = 0;i< list.size();i++){
            ticketData.add(list.get(i));
            //addToTable(list.get(i));
        }
        message_label.setText(answer);
        setHistory("add");
    }

    @FXML
    void update(ActionEvent event) {
        Ticket ticket = collectionTable.getSelectionModel().getSelectedItem();
        //System.out.println(ticket);
        if (ticket==null){
            message_label.setText("Сначала выберите элемент");
        }else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("update.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            UpdateController controller = loader.getController();
            controller.insertValues(ticket);
            controller.setChoiceBox();
            controller.id = ticket.getId();
            controller.id1 = ticket.getId1();
            stage.showAndWait();


            ticketData.clear();
            for (int i = 0;i< list.size();i++){
                ticketData.add(list.get(i));
                //addToTable(list.get(i));
            }
            message_label.setText(answer);
        }
        setHistory("update");
    }

    @FXML
    void remove(ActionEvent event) {
        Ticket ticket = collectionTable.getSelectionModel().getSelectedItem();
        if (ticket==null) {
            message_label.setText("Сначала выберите элемент");
        }else {
            SendThing sendThing = udpClient.send(ticket, "remove_by_id", ticket.getId().toString(), UDPClient.login);
            Answer answer1 = udpClient.toServer(sendThing);
            answer = answer1.getS();
            list = answer1.getList();
            ticketData.clear();
            for (int i = 0;i< list.size();i++){
                ticketData.add(list.get(i));
               // addToTable(list.get(i));
            }
            message_label.setText(answer);
        }
        setHistory("remove");
    }

    @FXML
    void add_if_min(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("add_if_min.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        Add_If_MinController controller = loader.getController();
        controller.setChoiceBox();
        stage.showAndWait();
        //System.out.println(ticket);
        ticketData.clear();
        for (int i = 0;i< list.size();i++){
            ticketData.add(list.get(i));
            //addToTable(list.get(i));
        }
        message_label.setText(answer);
        setHistory("add_if_min");
    }

    @FXML
    void clear(ActionEvent event) {
        SendThing sendThing = udpClient.send(null, "clear", "", UDPClient.login);
        Answer answer1 = udpClient.toServer(sendThing);
        list = answer1.getList();
        answer = answer1.getS();
        ticketData.clear();
        setHistory("clear");
    }

    @FXML
    void count_by_type(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("count_by_type.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        setHistory("count_by_type");
    }

    @FXML
    void filter_less_than_comment(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("filter_less_than_comment.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        setHistory("filter_less_than_comment");

    }

    @FXML
    void help(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("help.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        HelpController controller = loader.getController();
        controller.setLabel();
        stage.show();
        setHistory("help");
    }

    @FXML
    void history(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("history.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        HistoryController controller = loader.getController();
        controller.setLabel(s1,s2,s3,s4,s5,s6);
        stage.show();
        setHistory("history");
    }

    @FXML
    void info(ActionEvent event) {
//        SendThing sendThing = udpClient.send(null, "info", "", UDPClient.login);
//        Answer answer1 = udpClient.toServer(sendThing);
//        list = answer1.getList();
//        answer = answer1.getS();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("info.fxml"));
//        try {
//            loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Parent root = loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setResizable(false);
//        InfoController controller = loader.getController();
//        controller.setLabel(answer);
//        stage.show();
//        setHistory("info");
    }

    @FXML
    void remove_any_by_price(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("remove_any_by_price.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        ticketData.clear();
        for (int i = 0;i< list.size();i++){
            ticketData.add(list.get(i));
            //addToTable(list.get(i));
        }
        message_label.setText(answer);
        setHistory("remove_any_by_price");
    }

    @FXML
    void remove_by_id(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("remove_by_id.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        ticketData.clear();
        for (int i = 0;i< list.size();i++){
            ticketData.add(list.get(i));
        }
        message_label.setText(answer);
        setHistory("remove_by_id");
    }

    @FXML
    void remove_greater(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("remove_greater.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        Remove_GreaterController controller = loader.getController();
        controller.setChoiceBox();
        stage.showAndWait();
        ticketData.clear();
        for (int i = 0;i< list.size();i++){
            ticketData.add(list.get(i));
            //addToTable(list.get(i));
        }
        message_label.setText(answer);
        setHistory("remove_greater");

    }

    @FXML
    void script(ActionEvent event) {
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        try {
            sc = new Scanner(file);
            while (sc.hasNext()) {
                String s = sc.next();
                SendThing sendThing = Computer.loop(s);
                Answer answer = udpClient.toServer(sendThing);
                list = answer.getList();
                ticketData.clear();
                for (int i = 0; i < list.size(); i++) {
                    ticketData.add(list.get(i));
                }
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        }
    }

    public void addToTable(Ticket ticket){
        //System.out.println(ticket);
        idColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String >("name"));
        xColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Float>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("y"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Date>("creationDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("price"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String >("comment"));
        refundableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Boolean>("refundable"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, TicketType>("type"));
        id1Column.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("id1"));
        name1Column.setCellValueFactory(new PropertyValueFactory<Ticket, String >("name1"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("description"));
        eventTypeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, EventType>("eventType"));
        ticketData.add(ticket);

        //idColumn = new TableColumn<Ticket, Long>("id");
        collectionTable.setItems(ticketData);
        //ticketData.clear();
    }
    void setLogin(String login){
        login_now.setText(res.getString("login_now") + login);
    }

    void setHistory(String s){
        s6 = s5;
        s5 = s4;
        s4 = s3;
        s3 = s2;
        s2 = s1;
        s1 = s;
    }


    void updateElements(){
        pane.getChildren().clear();
        Line line1 = new Line();
        line1.setStartX(594 + (-756));
        line1.setStartY(270 - 0.6);
        line1.setEndX(594 + 600); //432
        line1.setEndY(270 - 0.6);
        Line line = new Line();
        line.setStartX(594 + (-0.2));
        line.setStartY(270 - (-300)); //175
        line.setEndX(594 + (-0.2));
        line.setEndY(270 - 401.2);
        pane.getChildren().add(line);
        pane.getChildren().add(line1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SendThing sendThing = udpClient.send(null, "info", "", UDPClient.login);
            Answer answer = udpClient.toServer(sendThing);
            list = answer.getList();
            ticketData.clear();
            for (int i = 0; i < list.size(); i++) {
                ticketData.add(list.get(i));
                //addToTable(list.get(i));
                Rectangle rectangle = new Rectangle();
                rectangle.setX(594 + list.get(i).getX() - 30);
                rectangle.setY(270 - list.get(i).getY() - 15);
                rectangle.setHeight(30);
                rectangle.setWidth(60);
                if (list.get(i).getLogin().equals(UDPClient.login)){
                    //rectangle.setStroke(Color.BLUE);
                    rectangle.setStyle("-fx-background-color: #114fec;");
                    rectangle.setFill(Color.BLUE);
                } else{
                    //rectangle.setStroke(Color.RED);
                    rectangle.setStyle("-fx-background-color: #ec4f11;");
                    rectangle.setFill(Color.RED);
                }
                pane.getChildren().add(rectangle);
            }
            String s = answer.getS();
            infoLabel.setText(res.getString("info1") + res.getString("info2") + date + res.getString("info3") + list.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println(Locale.getDefault());
        addButton.setText(res.getString("addButton"));
        updateButton.setText(res.getString("updateButton"));
        removeButton.setText(res.getString("removeButton"));
        tableTab.setText(res.getString("tableTab"));
        coordinatesTab.setText(res.getString("coordinatesTab"));
        SendThing sendThing = udpClient.send(null, "getCreationDate", "", UDPClient.login);
        Answer answer = udpClient.toServer(sendThing);

    }
}
