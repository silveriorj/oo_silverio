package sample.model;

import sample.control.Encrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUsuarioDAO implements UsuarioDAO {

    private static JDBCUsuarioDAO instance;

    private JDBCUsuarioDAO(){}

    public static JDBCUsuarioDAO getInstance(){
        if(instance==null){
            instance = new JDBCUsuarioDAO();
        }
        return instance;
    }

    public Usuario login(String user, String pass) throws NoSuchAlgorithmException, SQLException {
        Encrypt e = new Encrypt();
        String pwd = e.encrypt(pass);

        Connection c = FabricaConexao.getConnection();

        String sql;

        sql = "SELECT * FROM usuario WHERE user LIKE ? AND pwd LIKE ?";
        PreparedStatement pStm = c.prepareStatement(sql);

        pStm.setString(1, user);
        pStm.setString(2,pwd);

        ResultSet rs = pStm.executeQuery();
        if(rs!=null) {
            Usuario f = null;

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String usuario = rs.getString("user");
                String senha = rs.getString("pwd");
                double saldo = rs.getDouble("saldo");

                f = new Usuario(nome, usuario, senha, id, saldo);
            }
            return f;
        }
        return null;
    }


    @Override
    public void create(Usuario u) throws SQLException {
        Connection c = FabricaConexao.getConnection();

        String sql = "call add_usuario(?,?,?);";

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setString(1,u.getNome());
        pStm.setString(2,u.getUser());
        pStm.setString(3,u.getSenha());

        pStm.execute();

        c.close();
    }

    public List<Usuario> list() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("call list_usuario()");

            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String user = rs.getString("user");
                String pwd = rs.getString("pwd");
                Double saldo = rs.getDouble("saldo");

                Usuario u = new Usuario(nome, user, pwd, id, saldo);
                usuarios.add(u);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public Usuario search(int id) throws Exception {
        Connection c = FabricaConexao.getConnection();
        String sql = "select * from usuario where id=?";

        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        Usuario f = null;
        while(rs.next()){
            String nome = rs.getString("nome");

            f=new Usuario(nome);
        }
        rs.close();
        pstm.close();
        return f;
    }

    @Override
    public void update(Usuario u, double valor) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "call update_user(?,?)";

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setInt(1,u.getId());
        pStm.setDouble(2, valor);

        pStm.execute();

        pStm.close();

    }

    @Override
    public boolean delete(Usuario u) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "call delete_usuario(?)";

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setInt(1,u.getId());

        pStm.execute();

        pStm.close();
        c.close();
        return true;
    }
}
