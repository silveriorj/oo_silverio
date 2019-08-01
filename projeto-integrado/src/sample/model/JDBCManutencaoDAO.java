package sample.model;

import javafx.scene.control.ListView;

import java.sql.*;
import java.util.ArrayList;

public class JDBCManutencaoDAO implements manutencaoDAO{
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

        String sql = "INSERT INTO manutencao(descricao, data_criacao, id_cliente, status, data_prevista) VALUES (?,?,?,?,?)";

        PreparedStatement pstm = c.prepareStatement(sql);

        pstm.setString(1, os.getDescricao());
        pstm.setDate(2, (Date) os.getDatain());
        pstm.setInt(3, os.getCliente());
        pstm.setBoolean(4, false);
        pstm.setDate(5, (Date) os.getDataprev());

        pstm.execute();

        pstm.close();
    }

    @Override
    public void alterarOS(int ID) throws Exception {

    }

    @Override
    public ArrayList<OrdemServico> listarOS() throws Exception {
        Connection c = FabricaConexao.getConnection();
        ArrayList<OrdemServico> os = new ArrayList<>();


        String sql = "SELECT * FROM manutencao WHERE manutencao.status LIKE false";

        Statement stm = c.createStatement();

        ResultSet rs = stm.executeQuery(sql);
        OrdemServico mnt = null;
        while(rs.next()){
            int id = rs.getInt("protocolo");
            String desc = rs.getString("descricao");
            Date dataIn = rs.getDate("data_criacao");
            Boolean status = rs.getBoolean("status");
            int idCLiente = rs.getInt("id_cliente");
            int idFunc = rs.getInt("id_funcionario");
            Date dataOut = rs.getDate("data_finalizacao");
            Date prazo = rs.getDate("data_prevista");

            mnt = new OrdemServico(id,idFunc, idCLiente, desc,dataIn, dataOut, prazo, status);
            os.add(mnt);
        }
        rs.close();
        stm.close();
        return os;
    }

    @Override
    public void cancelarOS(int ID) throws Exception {

    }

    @Override
    public void atribuirOS(Funcionario funcionario) throws Exception {

    }

    @Override
    public void finalizarOS(int ID) throws Exception {

    }
}
