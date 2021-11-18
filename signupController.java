package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class signupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginfield;

    @FXML
    private TextField namefield;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button signupbutton;

    @FXML
    private TextField surnamefield;

    @FXML
    void initialize() {

        signupbutton.setOnAction(new EventHandler<ActionEvent>() {
            dbhandler handler = new dbhandler();

            @Override
            public void handle(ActionEvent actionEvent) {

                String name = namefield.getText();
                String surname = surnamefield.getText();
                String login = loginfield.getText();
                String password = passwordfield.getText();
                handler.signupuser(name, surname, login, password);

                Parent root = null;
                try {
                    root = FXMLLoader.load(signupController.this.getClass().getResource("sample.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Авторизація");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
                stage.setResizable(false);

                signupbutton.getScene().getWindow().hide();


            }
        });

    }

}