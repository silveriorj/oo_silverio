package sample.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Main;
import sample.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;


public class login_controller {

    @FXML
    private JFXTextField tfUser;
    @FXML
    private JFXPasswordField pfPass;

    @FXML
    private void efetuar_login() throws IOException, NoSuchAlgorithmException, SQLException {
        String login = tfUser.getText();
        String senha = pfPass.getText();
        if(login  != null || login != "" && senha != null || senha != ""){
            Funcionario usuario = JDBCFuncionarioDAO.getInstance().login(login, senha);
            if(usuario!=null) {
                if (usuario instanceof Administrador) {
                    Alerta.erro();
                    logar_administrador(usuario);
                } else if (usuario instanceof Operador) {
                    Alerta.aviso();
                    logar_operador(usuario);
                } else if (usuario instanceof Tecnico) {
                    Alerta.informacao();
                    logar_tecnico(usuario);
                }
            }else {
                Alerta.user_not_found();
            }
        }else {
            Alerta.user_not_filled();
        }
    }

    @FXML
    private void cadastrar_funcionario() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Cadastrar Cliente");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("view/cadastro_funcionarios_view.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            cadastro_funcionarios controller = fxmlLoader.getController();
            controller.cadastra();
        }
    }

    private void logar_administrador(Funcionario admin) throws IOException {
        Main.setUser(admin);
        Main.changeScreen("ADMINISTRADOR");
    }

    private void logar_operador(Funcionario operador) throws IOException {
        Main.setUser(operador);
        Main.changeScreen("OPERADOR");
    }

    private void logar_tecnico(Funcionario tecnico) throws IOException {
        Main.setUser(tecnico);
        Main.changeScreen("TECNICO");
    }

    @FXML
    public void getEnter(KeyEvent e) throws Exception{
        if(e.getCode() == KeyCode.ENTER){
            efetuar_login();
        }
    }
}
