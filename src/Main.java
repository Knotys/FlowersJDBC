//import com.sun.jdi.connect.spi.Connection;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/flowers",
                "flowers_user",
                "localhost2002"
        );

        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();

        System.out.println("Seznam květin:");

        insertNewFlower(statement, "Bledule", "White", "nwm", Boolean.TRUE);
        insertNewFlower(statement, "Kopretina", "White", "Jedná se o víceletou bylinu", Boolean.FALSE);

        updateFlower(statement);

        deleteNonPoisonousFLower(statement);

        printAllFlower(statement);

    }

    private static void insertNewFlower(Statement statement, String name, String color,
                                   String description, Boolean poisonous) throws SQLException {
        statement.executeUpdate(
                "INSERT INTO flower (name, color, description, poisonous) VALUES ('"+name+"'," +
                        " '"+color+"', '"+description+"', "+poisonous+")");
    }

    private static void updateFlower(Statement statement) throws SQLException {
        statement.execute(
                "UPDATE flower SET description = 'Pozor na cibulku - obsahuje největší koncentraci jedu!' WHERE name = 'Bledule'");
    }

    private static void deleteNonPoisonousFLower(Statement statement) throws SQLException {
        statement.execute(
                "DELETE FROM flower WHERE poisonous = FALSE");
    }

    private static void printAllFlower(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM flower");

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
            System.out.print(" - ");
            System.out.println(resultSet.getString("description"));
            System.out.print(" - id: ");
            System.out.println(resultSet.getInt(1));
        }
    }
}