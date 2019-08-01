package sample.model;

import java.util.ArrayList;

public interface manutencaoDAO {
    void criarOS(OrdemServico os) throws Exception;
    void alterarOS(int ID) throws Exception;
    ArrayList<OrdemServico> listarOS() throws Exception;
    void cancelarOS(int ID) throws Exception;
    void atribuirOS(Funcionario funcionario) throws Exception;
    void finalizarOS(int ID) throws Exception;
}
