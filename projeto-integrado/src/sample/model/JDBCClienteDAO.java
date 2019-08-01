package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCClienteDAO implements ClienteDAO {

    private static JDBCClienteDAO instance;

    private JDBCClienteDAO(){}

    public static JDBCClienteDAO getInstance(){
        if(instance==null){
            instance = new JDBCClienteDAO();
        }
        return instance;
    }

    @Override
    public void create(Cliente d, Endereco e) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "insert into"+
                " cliente(nome,email, garra)"+
                "values(?,?,?);";

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setString(1,d.getNome());
        pStm.setString(2,d.getEmail());
        pStm.setInt(3,d.getGarra());

        pStm.execute();

        Statement stm = c.createStatement();
        ResultSet rs = stm
                .executeQuery("select garra from cliente order by garra desc limit 1;");
        int garra = 0;
        while (rs.next()){
            garra = rs.getInt("garra");
        }

        String sqlE = "insert into"+" endereco_cliente(rua, numero, complemento, bairro, cidade, id_cliente)"+" values(?,?,?,?,?,?);";
        PreparedStatement pStm2 = c.prepareStatement(sqlE);
        pStm2.setString(1,e.getRua());
        pStm2.setString(2, e.getNumero());
        pStm2.setString(3, e.getComplemento());
        pStm2.setString(4, e.getBairro());
        pStm2.setString(5, e.getCidade());
        pStm2.setInt(6, garra);

        pStm2.execute();

        String script = "insert into telefone_cliente(id_cliente, telefone) values (?,?)";
        PreparedStatement ps = c.prepareStatement(script);

        for (Telefone t : d.getTelefone()) {
            ps.setInt(1,garra);
            ps.setString(2,t.getNumero());
            ps.addBatch();
        }
        ps.executeBatch();

        pStm.close();
        pStm2.close();
        ps.close();
        c.close();
    }

    @Override
    public List<Cliente> list() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select * from cliente");
            Cliente d = null;
            while (rs.next()){
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                int garra = rs.getInt("garra");


                String sql = "select * from endereco_cliente where id_cliente=?";

                PreparedStatement pstm = c.prepareStatement(sql);
                pstm.setInt(1, garra);
                ResultSet rsE = pstm.executeQuery();
                Endereco e = null;
                while(rsE.next()){
                    String rua = rsE.getString("rua");
                    String  numero = rsE.getString("numero");
                    String bairro = rsE.getString("bairro");
                    String complemento = rsE.getString("complemento");
                    String cidade = rsE.getString("cidade");

                    e = new Endereco(rua, bairro, cidade,complemento, numero);
                    
                }

                String sqlT = "select * from telefone_cliente where id_cliente=?";

                PreparedStatement pstmT = c.prepareStatement(sqlT);
                pstmT.setInt(1, garra);
                ResultSet rsT = pstmT.executeQuery();

                ArrayList<Telefone> tel = new ArrayList<>();
                while(rsT.next()){
                    String numero = rsT.getString("telefone");
                    Telefone t = new Telefone(numero);
                    tel.add(t);
                }

                d = new Cliente(nome, email, garra, e);
                try {
                    d.getTelefone().addAll(tel);
                }catch (Exception ignore){
                }
                clientes.add(d);

                rsE.close();
                rsT.close();
            }
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public Cliente search(int id) throws Exception {
        return null;
    }

    @Override
    public void update(Cliente c) throws Exception {

    }

    @Override
    public boolean delete(Cliente c) throws Exception {
        return false;
    }
}
