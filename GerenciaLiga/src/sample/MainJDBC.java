package sample;

import java.sql.*;

public class MainJDBC {

    public static void main(String[] args) {

        try {
            Connection c = DriverManager
                    .getConnection("jdbc:sqlite:banco.sqlite");

            Statement stm1 = c.createStatement();
            String sql1 = "insert into jogadores(nome,altura,peso)"+
                           "values('Ana Paula',1.67,63)";
            stm1.execute(sql1);


            Statement stm = c.createStatement();

            String sql = "select * from jogadores";

            ResultSet rs = stm.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double altura = rs.getDouble("altura");
                double peso = rs.getDouble("peso");

                System.out.println("id:"+id+
                                   " nome:"+nome+
                                   " altura:"+altura+
                                   " peso:"+peso);

            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
