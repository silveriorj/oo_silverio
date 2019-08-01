package sample.model;

import java.util.List;

public interface UsuarioDAO {
    void create(Usuario u) throws Exception;
    List<Usuario> list();
    Usuario search(int id) throws Exception;
    void update(Usuario u, double valor) throws Exception;
    boolean delete(Usuario u) throws Exception;
}
