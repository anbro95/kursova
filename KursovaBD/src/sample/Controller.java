package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginfield;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button signinbutton;

    @FXML
    private Hyperlink signuplink;

    @FXML

    void initialize() {


        signinbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String logintext = loginfield.getText();
                String passwordtext = passwordfield.getText();
                loginuser(logintext, passwordtext);
            }
        });

        signuplink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Parent root = null;
                try {
                    root = FXMLLoader.load(Controller.this.getClass().getResource("signup.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Реєстрація");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
                stage.setResizable(false);

                signuplink.getScene().getWindow().hide();
            }
        });
    }
    private void loginuser(String logintext, String passwordtext) {
        dbhandler handler = new dbhandler();
        User user = new User();
        user.setLogin(logintext);
        user.setPassword(passwordtext);

       ResultSet result =  handler.getUser(user);

        int counter = 0;

        while(true) {
            try {
                if (!result.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            counter++;
        }

        if(counter >= 1) {
            System.out.println("Success!");
            Parent root = null;
            try {
                root = FXMLLoader.load(Controller.this.getClass().getResource("abonents.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Абоненти");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
            stage.setResizable(false);

            signuplink.getScene().getWindow().hide();

        } else {
            System.out.println("Not success");
        }
    }



}
