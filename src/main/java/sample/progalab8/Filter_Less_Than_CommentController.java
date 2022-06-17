package sample.progalab8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Filter_Less_Than_CommentController implements Initializable {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    UDPClient udpClient = new UDPClient();
    ObservableList<Ticket> ticketData = FXCollections.observableArrayList();
    LinkedList<Ticket> list = new LinkedList<>();


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

    @FXML
    private TextField commentField;

    @FXML
    private Button outputButton;

    @FXML
    private Label comment;

    @FXML
    void output(ActionEvent event) {
        ticketData.clear();
        String comment = commentField.getText();
        SendThing sendThing = udpClient.send(null,"filter_less_than_comment",comment,UDPClient.login);
        Answer answer = udpClient.toServer(sendThing);
        list = answer.getList();
        for (int i = 0;i<list.size();i++){
            if (list.get(i).getComment().length() < comment.length()){
                ticketData.add(list.get(i));
            }
        }
    }

    public void setTable() {
        //System.out.println(ticket);
        idColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));
        xColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Float>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("y"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Date>("creationDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("price"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("comment"));
        refundableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Boolean>("refundable"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, TicketType>("type"));
        id1Column.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("id1"));
        name1Column.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name1"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("description"));
        eventTypeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, EventType>("eventType"));
        collectionTable.setItems(ticketData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTable();
        comment.setText(res.getString("comment"));
        outputButton.setText(res.getString("outputButton"));
    }
}
