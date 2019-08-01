package sample.model.Usuario;

import java.util.List;

public interface ClienteDAO {
    void create(Cliente c, Endereco e) throws Exception;
    List<Cliente> list();
    Cliente search(int id) throws Exception;
    public void update(Cliente c) throws Exception;
    public boolean delete(Cliente c) throws Exception;
}
