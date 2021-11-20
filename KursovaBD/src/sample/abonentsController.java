package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class abonentsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button contracts;

    @FXML
    private Button robotas;

    @FXML
    private TextField searchabonent;

    @FXML
    private Label resultlabel;

    @FXML
    private Button searchbutton;

    @FXML
    private TextField addname;

    @FXML
    private TextField addsurname;

    @FXML
    private TextField addthird;

    @FXML
    private Label answerlabel;

    @FXML
    private Button deletebutton;

    @FXML
    private TextField deletefield;

    @FXML
    private Label deleteanswer;

    @FXML
    private Button addbutton;

    @FXML
    void initialize() throws SQLException {
        robotas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(abonentsController.this.getClass().getResource("works.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Роботи та аварії");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
                stage.setResizable(false);

                robotas.getScene().getWindow().hide();
            }
        });

        contracts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(abonentsController.this.getClass().getResource("contracts.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Контракти");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
                stage.setResizable(false);

                contracts.getScene().getWindow().hide();
            }
        });

        final dbhandler hand = new dbhandler();

        searchbutton.setOnAction(new EventHandler<ActionEvent>() {

//

            @Override
            public void handle(ActionEvent actionEvent) {

            final  String surname = searchabonent.getText().trim();

            final String res = hand.findabonent(surname);
                System.out.println(surname);
                resultlabel.setText(res);
            }
        });

        addbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String name = addname.getText().trim();
                String surname = addsurname.getText().trim();
                String thirdname = addthird.getText().trim();

                try {
                    if(name != "" && surname != "" && thirdname != "") {
                        hand.addabonent(name, surname, thirdname);
                        answerlabel.setText("Новий абонент створений");

                    } else {
                        System.out.println("Fields are empty");
                        answerlabel.setText("Заповніть усі поля");
                    }

                    addname.setText("");
                    addsurname.setText("");
                    addthird.setText("");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        deletebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String surname = deletefield.getText().trim();
                try {
                    if(surname != ""){
                        hand.deleteabonent(surname);
                        deleteanswer.setText("Абонент видалений");
                    } else {
                        deleteanswer.setText("Заповніть поле!");
                    }

                    deletefield.setText("");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}