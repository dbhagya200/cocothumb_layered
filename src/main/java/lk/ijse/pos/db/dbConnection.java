package lk.ijse.pos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
public static dbConnection dbConnection;

private final Connection connection;

private dbConnection() throws SQLException {
    connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/cocothumb",
            "root",
            "Ijse@1234"
    );

}
public static dbConnection getInstance() throws SQLException {
    if (dbConnection == null) {
        dbConnection = new dbConnection();
    }
    return dbConnection;
}

public Connection getConnection(){
    return connection;
}

}
