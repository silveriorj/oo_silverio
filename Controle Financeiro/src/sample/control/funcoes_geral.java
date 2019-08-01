package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.model.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class funcoes_geral {

    @FXML
    private ComboBox<Categoria> cbCategory;

    @FXML
    private TextField tfTitulo;

    // INICIALIZA AS CATEGORIAS DO USUARIO ATUAL NO COMBOBOX PARA CADASTRAR UMA SUBCATEGORIA
    void initialize(Usuario user){
        if(JDBCCategoriasDAO.getInstance().listCategorias(user) != null){
            cbCategory.setItems(JDBCCategoriasDAO.getInstance().listCategorias(user));
        }
    }

    // ADICIONA UMA NOVA CATEGORIA
    boolean addCategoria(String titulo, Usuario u) throws Exception {
        for(Categoria c : u.getCategorias()){
            if(c.getTitulo().equals(titulo)){
                alerta();
                return false;
            }
        }
        try {
            if(titulo.equals(" ") || titulo.equals("")){       // REMOVE VALORES EM BRANCO
                return false;
            }else {
                Categoria c = new Categoria(titulo);
                JDBCCategoriasDAO.getInstance().addCategoria(u, titulo);
                u.addCategoria(c);
                return true;
            }
        }catch (NullPointerException e){                       // REMOVE VALORES NULOS
            alerta();
            return false;
        }
    }

    // ADICIONA UMA NOVA SUBCATEGORIA
    void addSubCategoria(Usuario u) throws Exception {
        String titulo;
        Categoria c = cbCategory.getSelectionModel().getSelectedItem();


        titulo = tfTitulo.getText();
        try{
            if(titulo!=null || c != null) {
                SubCategoria sub = new SubCategoria(titulo, c.getId());
                JDBCCategoriasDAO.getInstance().addSubCategoria(u, c, titulo);
                c.addSubCategorias(sub);
            }
        }catch(NullPointerException e){
                alerta();
        }
    }
    // ADICIONA UMA NOVA TRANSAÇÃO
    boolean addConta(double valor, Usuario user, Categoria c, SubCategoria sub, LocalDate data) throws Exception {
        int id_categoria, id_sub;
        id_categoria = c.getId();
        id_sub = sub.getId();
        boolean teste;
        if(data.isAfter(LocalDate.now())){
            teste = false;
        }else if(data.isBefore(LocalDate.now())){
            teste = true;
        }else {
            teste=true;
        }
        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd"); // PADRÃO SQL
        String dat;
        dat = df.format(Date.valueOf(data));

        Lancamento e = new Lancamento(valor, id_categoria, id_sub, dat, teste);

        e.setSubcategoria(sub.getTitulo());
        e.setCategoria(c.getTitulo());
        JDBCLancamentosDAO.getInstance().movimentacao(user, e, c, sub);
        JDBCUsuarioDAO.getInstance().update(user, user.getSaldo()+valor);

        user.addTransacao(e);
        return true;
    }

    // ALERTA DE ERRO PADRÃO!
    void alerta(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("ERRO");
        alerta.setHeaderText("Não foi possível realizar operação!");
        alerta.setContentText("Tente novamente!");
        alerta.showAndWait();
    }
}
