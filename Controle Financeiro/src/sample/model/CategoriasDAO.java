package sample.model;

import javafx.collections.ObservableList;

public interface CategoriasDAO {
    void addCategoria(Usuario usuario, String titulo) throws Exception;
    void addSubCategoria(Usuario usuario, Categoria categoria, String titulo) throws Exception;
    ObservableList<Categoria> listCategorias(Usuario usuario) throws Exception;
    ObservableList<SubCategoria> listSubCategorias(Categoria categoria);
}
