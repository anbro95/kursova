package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class contractsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addbutton;

    @FXML
    private Label answerlabel;

    @FXML
    private Button abonents;

    @FXML
    private Label deleteanswer;

    @FXML
    private Button deletebutton;

    @FXML
    private TextField deletefield;

    @FXML
    private Button findbutton;

    @FXML
    private Label resultlabel;

    @FXML
    private TextField addabonent;

    @FXML
    private TextField addadress;

    @FXML
    private TextField addprice;

    @FXML
    private TextField addtarif;

    @FXML
    private Button robotas;

    @FXML
    private TextField searchcontract;

    @FXML
    void initialize() {
        final dbhandler handler = new dbhandler();

        robotas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(contractsController.this.getClass().getResource("works.fxml"));
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
        deletebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String surname = deletefield.getText().trim();
                try {
                    if(surname != "") {
                        handler.deletecontract(surname);
                        deleteanswer.setText("Контракт видалений");
                    } else  {
                        deleteanswer.setText("Заповніть дане поле");
                    }

                    deletefield.setText("");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        addbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String abonent = addabonent.getText().trim();
                String adress = addadress.getText();
                String tarif = addtarif.getText().trim();
                String price = addprice.getText().trim();

                try {
                    if(abonent != "" && adress != "" && tarif != "" && price != "") {
                        handler.addcontract(abonent, adress, tarif, price);
                        answerlabel.setText("Контракт створений");
                    } else {
                        answerlabel.setText("Заповніть всі поля");
                    }

                    addabonent.setText("");
                    addadress.setText("");
                    addtarif.setText("");
                    addprice.setText("");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        abonents.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(contractsController.this.getClass().getResource("abonents.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Абоненти");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
                stage.setResizable(false);

                abonents.getScene().getWindow().hide();
            }
        });

        findbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String surname = searchcontract.getText().trim();
                try {
                   String res =  handler.findcontract(surname);
                   resultlabel.setText(res);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}