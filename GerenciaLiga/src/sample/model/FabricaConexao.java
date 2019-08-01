package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static Connection c;

    public static Connection getConnection() throws SQLException{

        if((c==null) || (c.isClosed())){
            c = DriverManager
                    .getConnection("jdbc:sqlite:banco.sqlite");
        }
        return c;
    }

}
