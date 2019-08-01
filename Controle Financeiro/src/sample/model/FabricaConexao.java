package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class FabricaConexao {
    private static Connection c;

    static Connection getConnection() throws SQLException {

        if((c==null) || (c.isClosed())){
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "root");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");

            c=DriverManager
                    .getConnection("jdbc:mysql://localhost/gestorFinanceiro", properties);
        }
        return c;
    }
}
