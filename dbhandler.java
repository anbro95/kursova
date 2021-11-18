package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class dbhandler  {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "electrical_service";

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, "root", "root");
        return dbConnection;
    }

    public void signupuser(String name, String surname, String login, String users_password ) {
        String insert = "INSERT" + " " + "INTO" + " " + "users" + "(" + "name" + "," + "surname" + "," + "login" + "," + "users_password" + ")" +
                "VALUES(?,?,?,?)";
        try {
        PreparedStatement prst = getDbConnection().prepareStatement(insert);
        prst.setString(1, name);
        prst.setString(2, surname);
        prst.setString(3, login);
        prst.setString(4, users_password);


            prst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 // Сюда не подглядывй это авторизация
    public ResultSet getUser(User user) {
        ResultSet resset = null;

        String select = "SELECT * FROM users WHERE login = ? AND users_password = ?";

        try {
            PreparedStatement prst = getDbConnection().prepareStatement(select);
            prst.setString(1, name);
            prst.setString(2, surname);



            prst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
