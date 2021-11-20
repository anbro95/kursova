package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class dbhandler {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "electrical_service";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, "root", "root");
        return dbConnection;
    }

    public void signupuser(User user) {
        String insert = "INSERT" + " " + "INTO" + " " + "users" + "(" + "name" + "," + "surname" + "," + "login" + "," + "users_password" + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prst = getDbConnection().prepareStatement(insert);
            prst.setString(1, user.getName());
            prst.setString(2, user.getSurname());
            prst.setString(3, user.getLogin());
            prst.setString(4, user.getPassword());


            prst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resset = null;

        String select = "SELECT * FROM users WHERE login = ? AND users_password = ?";

        try {
            PreparedStatement prst = getDbConnection().prepareStatement(select);
            prst.setString(1, user.getLogin());
            prst.setString(2, user.getPassword());


            resset = prst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resset;
    }

    public String findabonent(String surname) {

        ResultSet resset = null;
        String query = "SELECT * FROM abonent WHERE abonent.surname = " + "'" + surname + "'" + ";";
        String result = "nothing";

        PreparedStatement p = null;
        try {
            p = getDbConnection().prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            resset = p.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String id = "Нема інформаціїa";
        String name = "";
        String sur = "Нема інформації";
        String third = "";
        String energy = "Нема інформації";
        while (true) {
            try {
                if (!resset.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                id = resset.getString(1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                name = resset.getString(2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                sur = resset.getString(3);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                third = resset.getString(4);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                energy = resset.getString(5);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        String resstr = "ПІБ: " + sur + " " + name + " " + third + "\n" +
                "Витрачена енергія: " + energy;

        return resstr;
    }

    public void addabonent(String name, String surname, String third) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO abonent(name, surname, third_name) VALUES (?,?,?)";

        PreparedStatement p = getDbConnection().prepareStatement(insert);

        p.setString(1, name);
        p.setString(2, surname);
        p.setString(3, third);

        p.executeUpdate();

    }

    public void deleteabonent(String surname) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM abonent WHERE abonent.surname = " + "'" + surname + "'";

        PreparedStatement p = getDbConnection().prepareStatement(delete);

        p.executeUpdate();
    }

    public String findcontract(String surname) throws SQLException, ClassNotFoundException {
        String find = "SELECT * from contract \n" +
                "JOIN abonent ON contract.abonent = abonent.id\n" +
                "WHERE abonent.surname = " + "'" + surname + "'";
        ResultSet r = null;

        PreparedStatement p = getDbConnection().prepareStatement(find);
        r = p.executeQuery();

        String adress = "Нема інформації";
        String tarif = "Нема інформації";
        String price = "Нема інформації";


        while (r.next()) {
            adress = r.getString(3);
            tarif = r.getString(4);
            price = r.getString(5);
        }

        String result = "Адреса: " + adress + "\n" +
                "Тариф: " + tarif + "\n" +
                "Ціна: " + price;

        return result;
    }

    public void addcontract(String abonent, String adress, String tarif, String price) throws SQLException, ClassNotFoundException {
        ResultSet r = null;

        String findid = "SELECT abonent.id FROM abonent \n" +
                "WHERE abonent.surname = " + "'" + abonent + "'";
        PreparedStatement id = getDbConnection().prepareStatement(findid);
        r = id.executeQuery();

        String idresult = "052";
        while (r.next()) {
            idresult = r.getString(1);
        }

        String add = "INSERT INTO contract(abonent, adress, tarif, price) VALUES(?,?,?,?)";

        PreparedStatement p = getDbConnection().prepareStatement(add);

        p.setString(1, idresult);
        p.setString(2, adress);
        p.setString(3, tarif);
        p.setString(4, price);

        p.executeUpdate();
    }

    public void deletecontract(String surname) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM contract\n" +
                "WHERE contract.abonent IN (\n" +
                "\tSELECT abonent.id FROM abonent \n" +
                "    WHERE abonent.surname = " + "'" + surname + "'" +
                ")";

        PreparedStatement p = getDbConnection().prepareStatement(delete);

        p.executeUpdate();
    }

    public void addcrash(String type, String adress) throws SQLException, ClassNotFoundException {
        String add = "INSERT INTO crash(type, adress) VALUES(?,?)";

        PreparedStatement p = getDbConnection().prepareStatement(add);

        p.setString(1, type);
        p.setString(2, adress);

        p.executeUpdate();
    }

    public void addwork(String type, String date) throws SQLException, ClassNotFoundException {
        String add = "INSERT INTO robota(type, robota_date) VALUES(?,?)";

        PreparedStatement p = getDbConnection().prepareStatement(add);

        p.setString(1, type);
        p.setString(2, date);

        p.executeUpdate();
    }

    public String findbrigadebyadress(String adress) throws SQLException, ClassNotFoundException {
        ResultSet resset = null;

        String find = "SELECT brigade.id FROM brigade\n" +
                "JOIN robota_brigade ON robota_brigade.brigade_id = brigade.id\n" +
                "JOIN robota ON robota_brigade.robota_id = robota.id\n" +
                "JOIN crash ON robota.crash_id = crash.id\n" +
                "WHERE crash.adress = " + "'" + adress + "'";

        PreparedStatement p = getDbConnection().prepareStatement(find);

        resset = p.executeQuery();

        String brigade = "nothing";

        while (resset.next()) {
            brigade = resset.getString(1);
        }

        String answer = brigade + " бригада";

        return answer;
    }

    public String findbrigadebydate(String date) throws SQLException, ClassNotFoundException {
        ResultSet resset = null;

        String find = "SELECT brigade.id FROM brigade\n" +
                "JOIN robota_brigade ON robota_brigade.brigade_id = brigade.id\n" +
                "JOIN robota ON robota_brigade.robota_id = robota.id\n" +
                "JOIN crash ON robota.crash_id = crash.id\n" +
                "WHERE robota.robota_date = " + "'" + date + "'";

        PreparedStatement p = getDbConnection().prepareStatement(find);

        resset = p.executeQuery();

        String brigade = "nothing";

        while (resset.next()) {
            brigade = resset.getString(1);
        }

        String answer = brigade + " бригада";

        return answer;
    }
}


