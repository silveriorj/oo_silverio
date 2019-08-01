package sample.model.Manutencao;

import sample.model.FabricaConexao;
import sample.model.Usuario.Funcionario;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class JDBCManutencaoDAO implements manutencaoDAO {
    private static JDBCManutencaoDAO instance;

    private JDBCManutencaoDAO(){}

    public static JDBCManutencaoDAO getInstance(){
        if(instance==null){
            instance = new JDBCManutencaoDAO();
        }
        return instance;
    }


    @Override
    public void criarOS(OrdemServico os) throws Exception {
        Connection c = FabricaConexao.getConnection();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datain = dateFormat.format(os.getDatain());
        String dataprev = dateFormat.format(os.getDataprev());

        String sql = "call create_os(?,?,?,?,?,?)";

        PreparedStatement pstm = c.prepareStatement(sql);

        pstm.setString(1, os.getDescricao());
        pstm.setString(2, datain);
        pstm.setInt(3, os.getId_cliente());
        pstm.setBoolean(4, false);
        pstm.setString(5, dataprev);
        pstm.setString(6, os.getNome_cliente());

        pstm.execute();

        pstm.close();
    }

    @Override
    public void alterarOS(OrdemServico os) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "UPDATE manutencao set id_cliente = ?, nome_cliente = ?, descricao = ? where protocolo like ?";

        PreparedStatement stm = c.prepareStatement(sql);

        stm.setInt(1, os.getId_cliente());
        stm.setString(2, os.getNome_cliente());
        stm.setString(3, os.getDescricao());
        stm.setInt(4, os.getProtocolo());

        stm.execute();
        stm.close();
    }

    @Override
    public ArrayList<OrdemServico> listarOS() throws Exception {
        Connection c = FabricaConexao.getConnection();
        ArrayList<OrdemServico> os = new ArrayList<>();


        String sql = "call list_os()";

        Statement stm = c.createStatement();

        ResultSet rs = stm.executeQuery(sql);
        monta_os(os, rs);
        rs.close();
        stm.close();
        return os;
    }

    public ArrayList<OrdemServico> listar_abertas(int id) throws Exception {
        Connection c = FabricaConexao.getConnection();
        ArrayList<OrdemServico> os = new ArrayList<>();

        String sql = "call list_os_tecnica(?)"; // CALL

        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();

        monta_os(os, rs);

        rs.close();
        pstm.close();
        return os;
    }

    public ArrayList<OrdemServico> listar_encerradas() throws Exception {
        Connection c = FabricaConexao.getConnection();
        ArrayList<OrdemServico> os = new ArrayList<>();

        String sql = "call list_os_ended()";

        Statement stm = c.createStatement();

        ResultSet rs = stm.executeQuery(sql);
        monta_os(os, rs);
        rs.close();
        stm.close();
        return os;
    }


    private void monta_os(ArrayList<OrdemServico> os, ResultSet rs) throws SQLException {
        OrdemServico mnt;
        while(rs.next()){
            int id = rs.getInt("protocolo");
            String desc = rs.getString("descricao");
            Date dataIn = rs.getDate("data_criacao");
            Boolean status = rs.getBoolean("status");
            int idCLiente = rs.getInt("id_cliente");
            int idFunc = rs.getInt("id_funcionario");
            Date dataOut = rs.getDate("data_finalizacao");
            Date prazo = rs.getDate("data_prevista");
            String nome_cliente = rs.getString("nome_cliente");
            String servico = rs.getString("servicos");

            mnt = new OrdemServico(id,idFunc, idCLiente, desc,dataIn, dataOut, prazo, status, nome_cliente, servico);
            os.add(mnt);
        }
    }

    @Override
    public void cancelarOS(int ID) throws Exception {
        Connection connection = FabricaConexao.getConnection();

        String sql = "DELETE FROM manutencao WHERE protocolo LIKE (?)";

        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, ID);

        stm.execute();
        stm.close();
    }

    @Override
    public void atribuirOS(Funcionario tec, OrdemServico os) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "UPDATE manutencao set id_funcionario=? where manutencao.protocolo LIKE ?";

        PreparedStatement pstm = c.prepareStatement(sql);

        pstm.setInt(1, tec.getId());
        pstm.setInt(2, os.getProtocolo());

        pstm.execute();

        pstm.close();
    }

    @Override
    public void finalizarOS(OrdemServico os) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "UPDATE manutencao set servicos=?, status=true WHERE protocolo LIKE ?";
        PreparedStatement pstm = c.prepareStatement(sql);

        pstm.setString(1, os.getServico());
        pstm.setInt(2, os.getProtocolo());

        pstm.execute();
        pstm.close();
    }
}
