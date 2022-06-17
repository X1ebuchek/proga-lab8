package sample.progalab8;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartController implements Initializable {
    UDPClient udpClient = new UDPClient();
    LinkedList<Ticket> list;
    ExecutorService service = Executors.newFixedThreadPool(5);
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());

    @FXML
    private Button loginButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Label message_field;

    @FXML
    private Button openRegistrationWindowButton;

    @FXML
    private Label login;

    @FXML
    private Label password;

    @FXML
    private AnchorPane pane;

    @FXML
    void login(ActionEvent event) {
        String login = login_field.getText().trim();
        String password = password_field.getText().trim();
        message_field.setText(" ");
        password = getHash(password);
        SendThing sendThing = udpClient.send(null,"auth",password,login);
        Answer answer = udpClient.toServer(sendThing);
        String s = answer.getS();
        list = answer.getList();
        if (s.equals(res.getString("logSuccessfully"))){
            loginButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Main.fxml"));
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
            MainController controller = loader.getController();
            controller.setLogin(login);
            for (int i = 0;i< list.size();i++){
                controller.addToTable(list.get(i));
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        update(controller);
                    }
                }
            });
            thread.start();

        }else {
            message_field.setText(s);
        }
    }
    @FXML
    void openRegistrationWindow(ActionEvent event) {
        openRegistrationWindowButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Registration.fxml"));
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
    }
    private String getHash(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-384");
            byte[] bytes = digest.digest(s.getBytes(StandardCharsets.UTF_8));
            BigInteger bigInteger = new BigInteger(1, bytes);
            String hash = bigInteger.toString(16);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    void update(MainController controller) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.updateElements();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.setText(res.getString("login"));
        password.setText(res.getString("password"));
        loginButton.setText(res.getString("loginButton"));
        openRegistrationWindowButton.setText(res.getString("registrationButton"));
    }
}
