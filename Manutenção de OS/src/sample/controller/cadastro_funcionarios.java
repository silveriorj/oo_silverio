package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Usuario.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class cadastro_funcionarios {

    @FXML
    private TextField tfnome, tfEmail, tfUser;
    @FXML
    private PasswordField pfPass;
    @FXML
    private ComboBox<String> cbFuncao = new ComboBox<>();

    public void initialize(){
        ArrayList<String> cargos = new ArrayList<>();
        cargos.add("ADMINISTRADOR");
        cargos.add("OPERADOR");
        cargos.add("TECNICO");

        cbFuncao.getItems().addAll(cargos);
    }

    public void cadastra() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String nome, email, user, senha;
        nome = tfnome.getText();
        email = tfEmail.getText();
        user = tfUser.getText();
        senha = pfPass.getText();
        if(cbFuncao.getSelectionModel().getSelectedItem() != null){
            switch (cbFuncao.getSelectionModel().getSelectedItem()) {
                case "ADMINISTRADOR": {
                    Encrypt e = new Encrypt();
                    String pwd = e.encrypt(senha);
                    Administrador usuario = new Administrador(nome, email, user, pwd);
                    finaliza_cadastro(usuario);
                    break;
                }
                case "OPERADOR": {
                    Encrypt e = new Encrypt();
                    String pwd = e.encrypt(senha);
                    Operador usuario = new Operador(nome, email, user, pwd);
                    finaliza_cadastro(usuario);
                    break;
                }
                case "TECNICO": {
                    Encrypt e = new Encrypt();
                    String pwd = e.encrypt(senha);
                    Tecnico usuario = new Tecnico(nome, email, user, pwd);
                    finaliza_cadastro(usuario);
                    break;
                }
            }
        }
    }

    private void finaliza_cadastro(Funcionario usuario){
        try{
            JDBCFuncionarioDAO.getInstance().create(usuario);
        }catch (Exception d){
            System.out.println(d.getMessage());
        }
    }

}
