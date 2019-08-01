package sample.model.Manutencao;

import sample.model.Usuario.Funcionario;

import java.util.ArrayList;

public interface manutencaoDAO {
    void criarOS(OrdemServico os) throws Exception;
    void alterarOS(OrdemServico ordemServico) throws Exception;
    ArrayList<OrdemServico> listarOS() throws Exception;
    void cancelarOS(int ID) throws Exception;
    void atribuirOS(Funcionario tecnico, OrdemServico os) throws Exception;
    void finalizarOS(OrdemServico os) throws Exception;
}
