package sample.model.Usuario;

import sample.controller.Encrypt;
import sample.model.FabricaConexao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCFuncionarioDAO implements FuncionarioDAO {

    private static JDBCFuncionarioDAO instance;

    private JDBCFuncionarioDAO(){}

    public static JDBCFuncionarioDAO getInstance(){
        if(instance==null){
            instance = new JDBCFuncionarioDAO();
        }
        return instance;
    }

    public Funcionario login(String user, String pass) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        Connection c = FabricaConexao.getConnection();

        Encrypt e = new Encrypt();
        String pwd = e.encrypt(pass);

        String sql = "call login(?,?)";

        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setString(1, user);
        pstm.setString(2, pwd);
        pstm.executeQuery();


        ResultSet rs = pstm.executeQuery();
        if(rs!=null) {
            Funcionario f = null;

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String usuario = rs.getString("user");
                String senha = rs.getString("pwd");
                int tipo = rs.getInt("tipo");

                if(tipo == 0){
                    f = new Administrador(id,nome,email,usuario,senha,tipo);
                }else if(tipo == 1){
                    f = new Operador(id,nome,email,usuario,senha,tipo);
                }else if(tipo == 2){
                    f = new Tecnico(id,nome,email,usuario,senha,tipo);
                }
            }
            pstm.close();
            return f;
        }
        pstm.close();
        return null;
    }


    @Override
    public void create(Funcionario f) throws SQLException {
        Connection c = FabricaConexao.getConnection();

        String sql = "call add_usuario(?,?,?,?,?)";

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setString(1,f.getNome());
        pStm.setString(2,f.getEmail());
        pStm.setString(3,f.getUser());
        pStm.setString(4,f.getSenha());
        pStm.setInt(5, f.getTipo());

        pStm.execute();

        pStm.close();
        c.close();
    }

    public List<Funcionario> list() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("call list_usuario()");

            monta_funcionario(funcionarios, rs);

        }catch (SQLException e){
            e.printStackTrace();
        }

        return funcionarios;
    }

    @Override
    public Funcionario search(int id) throws Exception {
        Connection c = FabricaConexao.getConnection();
        String sql = "call search_employee(?)"; // CALL

        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        Funcionario f = null;
        while(rs.next()){
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String user = rs.getString("user");
            String pwd = rs.getString("pwd");


            f=new Funcionario(nome,email, user, pwd);
        }
        rs.close();
        pstm.close();
        return f;
    }

    public List<Funcionario> list_tecnico(){
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("call list_tecnico()");

            monta_funcionario(funcionarios, rs);

        }catch (SQLException e){
            e.printStackTrace();
        }

        return funcionarios;
    }

    private void monta_funcionario(ArrayList<Funcionario> funcionarios, ResultSet rs) throws SQLException {
        while (rs.next()){
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String user = rs.getString("user");
            String pwd = rs.getString("pwd");
            int tipo = rs.getInt("tipo");

            if(tipo==0){
                Administrador usuario = new Administrador(id, nome, email, user, pwd, tipo);
                funcionarios.add(usuario);
            }else if(tipo == 1){
                Operador usuario = new Operador(id, nome, email, user, pwd, tipo);
                funcionarios.add(usuario);
            }else if(tipo == 2){
                Tecnico usuario = new Tecnico(id, nome, email, user, pwd, tipo);
                funcionarios.add(usuario);
            }
        }
    }

    @Override
    public void update(Funcionario f) throws Exception {
        Connection c = FabricaConexao.getConnection();
    }

    @Override
    public boolean delete(Funcionario f) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "DELETE FROM funcionario WHERE id LIKE ?";

        PreparedStatement stm = c.prepareStatement(sql);
        stm.setInt(1, f.getId());

        stm.execute();

        stm.close();
        return true;
    }
}
