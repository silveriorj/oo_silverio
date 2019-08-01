package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class FabricaConexao {
    private static Connection c;

    public static Connection getConnection() throws SQLException {

        if((c==null) || (c.isClosed())){
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "root");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            c=DriverManager
                    .getConnection("jdbc:mysql://localhost/sniperbd",properties);
        }
        return c;
    }
}
