package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCCategoriasDAO implements CategoriasDAO{

    private static JDBCCategoriasDAO instance;

    private JDBCCategoriasDAO(){}

    public static JDBCCategoriasDAO getInstance(){
        if(instance==null){
            instance = new JDBCCategoriasDAO();
        }
        return instance;
    }


    @Override
    public void addCategoria(Usuario usuario, String titulo) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "insert into categoria(id_usuario, titulo)" +
                "  values(?,?)";

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setInt(1, usuario.getId());
        pStm.setString(2, titulo);

        pStm.execute();

        pStm.close();
        c.close();

        listCategorias(usuario);
    }

    @Override
    public void addSubCategoria(Usuario usuario, Categoria categoria, String titulo) throws Exception {
        Connection c = FabricaConexao.getConnection();

        String sql = "insert into subcategoria(id_categoria, titulo)" +
                "  values(?,?)";

        PreparedStatement pStm = c.prepareStatement(sql);
        pStm.setInt(1, categoria.getId());
        pStm.setString(2, titulo);

        pStm.execute();

        pStm.close();
        c.close();

        listSubCategorias(categoria);
    }

    @Override
    public ObservableList<Categoria> listCategorias(Usuario usuario){
        ObservableList<Categoria> categorias = FXCollections.observableArrayList();

        try {
            Connection connection = FabricaConexao.getConnection();

            PreparedStatement stm = connection.prepareStatement("select * from categoria where id_usuario like (?)");
            stm.setInt(1, usuario.getId());

            ResultSet rs = stm
                    .executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");

                Categoria l = new Categoria(titulo, id);
                categorias.add(l);
            }
            stm.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categorias;
    }

    @Override
    public ObservableList<SubCategoria> listSubCategorias(Categoria categoria){
        ObservableList<SubCategoria> subCategorias = FXCollections.observableArrayList();

        try {
            Connection connection = FabricaConexao.getConnection();

            PreparedStatement stm = connection.prepareStatement("select * from subcategoria where id_categoria like (?)");
            stm.setInt(1, categoria.getId());

            ResultSet rs = stm
                    .executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                int id_categoria = rs.getInt("id_categoria");
                String titulo = rs.getString("titulo");

                SubCategoria l = new SubCategoria(titulo, id_categoria, id);
                subCategorias.add(l);
            }
            stm.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return subCategorias;
    }
}
