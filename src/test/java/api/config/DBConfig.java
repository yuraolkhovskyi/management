package api.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    private static DBModel dbModel = TestProperty.PROPERTIES.getDb();
    private static String url;
    private static String user;
    private static String password;

    static {
        url = dbModel.getUrl();
        user = dbModel.getUser();
        password = dbModel.getPassword();
    }

    public static Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
