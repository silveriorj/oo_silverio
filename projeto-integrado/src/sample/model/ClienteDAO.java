package sample.model;

import java.util.List;

public interface ClienteDAO {
    public void create(Cliente c, Endereco e) throws Exception;
    public List<Cliente> list();
    public Cliente search(int id) throws Exception;
    public void update(Cliente c) throws Exception;
    public boolean delete(Cliente c) throws Exception;
}
