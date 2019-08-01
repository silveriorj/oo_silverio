package sample.model.Usuario;

import java.util.List;

public interface FuncionarioDAO {
    public void create(Funcionario f) throws Exception;
    public List<Funcionario> list();
    public Funcionario search(int id) throws Exception;
    public void update(Funcionario f) throws Exception;
    public boolean delete(Funcionario f) throws Exception;
}
