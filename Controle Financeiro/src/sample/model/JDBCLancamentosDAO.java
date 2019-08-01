package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class JDBCLancamentosDAO implements LancamentoDAO {

    private static JDBCLancamentosDAO instance;

    private JDBCLancamentosDAO(){}

    public static JDBCLancamentosDAO getInstance(){
        if(instance==null){
            instance = new JDBCLancamentosDAO();
        }
        return instance;
    }

    @Override
    public void movimentacao(Usuario u, Lancamento l, Categoria cat, SubCategoria sub) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "call add_transacao(?,?,?,?,?,?,?,?)";
        boolean bol;

        bol = l.getStatus().equals("PAGO");

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setInt(1, u.getId());
        pStm.setInt(2, cat.getId());
        pStm.setInt(3,sub.getId());
        pStm.setString(4, cat.getTitulo());
        pStm.setString(5, sub.getTitulo());
        pStm.setDouble(6, l.getValor());
        pStm.setDate(7, Date.valueOf(l.getDataLancamento()));
        pStm.setBoolean(8, bol);

        pStm.execute();

        pStm.close();
        c.close();
    }

    public ObservableList<Lancamento> list(Usuario u) {
        ObservableList<Lancamento> transacoes = FXCollections.observableArrayList();

        try {
            Connection connection = FabricaConexao.getConnection();

            PreparedStatement stm = connection.prepareStatement("call list_transacao(?)");
            stm.setInt(1, u.getId());

            ResultSet rs = stm
                    .executeQuery();
            resultList(transacoes, rs);
            stm.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return transacoes;
    }

    public ObservableList<Lancamento> listBy(LocalDate data_sel, Usuario usuario) {
        ObservableList<Lancamento> transacoes = FXCollections.observableArrayList();

        try {
            Connection connection = FabricaConexao.getConnection();

            PreparedStatement stm = connection.prepareStatement("call list_by(?,?)");
            stm.setDate(1, Date.valueOf(data_sel));
            stm.setInt(2, usuario.getId());

            ResultSet rs = stm
                    .executeQuery();
            resultList(transacoes, rs);
            stm.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return transacoes;
    }

    public ObservableList<Lancamento> listBetween(LocalDate data_ini, LocalDate data_fim, Usuario usuario) {
        ObservableList<Lancamento> transacoes = FXCollections.observableArrayList();

        try {
            Connection connection = FabricaConexao.getConnection();


            PreparedStatement stm = connection.prepareStatement("call list_between(?,?,?)");
            stm.setDate(1, Date.valueOf(data_ini));
            stm.setDate(2,Date.valueOf(data_fim));
            stm.setInt(3, usuario.getId());

            ResultSet rs = stm
                    .executeQuery();
            resultList(transacoes, rs);
            stm.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return transacoes;
    }

    public ObservableList<Lancamento> listGraficoCategoria(Usuario usuario){


        ObservableList<Lancamento> transacoes = FXCollections.observableArrayList();

        try {
            Connection connection = FabricaConexao.getConnection();

            PreparedStatement stm = connection.prepareStatement("select * ,sum(valor),count(titulo) from transacao where id_usuario LIKE ?  group by titulo having Count(titulo) >=1;");
            stm.setInt(1, usuario.getId());
            ResultSet rs = stm
                    .executeQuery();
            while (rs.next()){
                double val = rs.getDouble("sum(valor)");
                int freq = rs.getInt("Count(titulo)");
                String cat = rs.getString("titulo");
                String sub = rs.getString("subtitulo");

                Lancamento l = new Lancamento(val,freq,cat,sub);
                transacoes.add(l);
            }
            stm.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return transacoes;
    }

    public ObservableList<Lancamento> listGraficoSubCategoria(Usuario usuario){


        ObservableList<Lancamento> transacoes = FXCollections.observableArrayList();

        try {
            Connection connection = FabricaConexao.getConnection();

            PreparedStatement stm = connection.prepareStatement("select * ,sum(valor),count(subtitulo) from transacao where id_usuario LIKE ?  group by subtitulo having Count(subtitulo) >= 1;");
            stm.setInt(1, usuario.getId());
            ResultSet rs = stm
                    .executeQuery();
            while (rs.next()){
                double val = rs.getDouble("sum(valor)");
                int freq = rs.getInt("Count(subtitulo)");
                String cat = rs.getString("titulo");
                String sub = rs.getString("subtitulo");

                Lancamento l = new Lancamento(val,freq,cat,sub);
                transacoes.add(l);
            }
            stm.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return transacoes;
    }

    public ObservableList<Lancamento> list_categoria(Usuario usuario, Categoria c){


            ObservableList<Lancamento> transacoes = FXCollections.observableArrayList();

            try {
                Connection connection = FabricaConexao.getConnection();

                PreparedStatement stm = connection.prepareStatement("select * from transacao where id_usuario LIKE ? AND id_categoria LIKE ?;");
                stm.setInt(1, usuario.getId());
                stm.setInt(2,c.getId());
                ResultSet rs = stm
                        .executeQuery();
                resultList(transacoes, rs);

                stm.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return transacoes;

    }

    private void resultList(ObservableList<Lancamento> transacoes, ResultSet rs) throws SQLException {
        SimpleDateFormat df =  new SimpleDateFormat("dd/MM/yyyy");
        String dat;

        while (rs.next()){
            int id = rs.getInt("id");
            double val = rs.getDouble("valor");
            int cat = rs.getInt("id_categoria");
            int subcat = rs.getInt("id_subCategoria");
            String categoria = rs.getString("titulo");
            String subcategoria = rs.getString("subtitulo");
            boolean status = rs.getBoolean("status");
            Date data = rs.getDate("dataLancamento");
            dat = df.format(data);


            Lancamento l = new Lancamento(id, val, cat, subcat, categoria, subcategoria, status, dat);
            transacoes.add(l);
        }
    }

    @Override
    public void update(Lancamento l) throws Exception {
        Connection connection = FabricaConexao.getConnection();

        boolean bol;
        bol = l.getStatus().equals("PAGO");

        PreparedStatement stm = connection.prepareStatement("UPDATE transacao SET status=? WHERE id LIKE ? ");
        stm.setBoolean(1, bol);
        stm.setInt(2, l.getId());

        stm.execute();

        stm.close();
        connection.close();
    }

    @Override
    public void limpa(Usuario u) throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        PreparedStatement stm = connection.prepareStatement("call limpa_transacao(?)");
        stm.setInt(1, u.getId());

        stm.executeQuery();

        stm.close();
        connection.close();
    }
}
