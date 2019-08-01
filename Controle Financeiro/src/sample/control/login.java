package sample.control;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.model.JDBCUsuarioDAO;
import sample.model.Usuario;

import java.io.IOException;
import java.util.Optional;


public class login extends Application {
    @FXML
    public static Stage myStage;

    @FXML
    private Pane telaLogin;

    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField tfSenha;


    public void Entrar() throws Exception {
        String login = tfUser.getText();
        String senha = tfSenha.getText();

        if(login  != null  && senha != null){
            Usuario p = JDBCUsuarioDAO.getInstance().login(login, senha);
            if(p!=null) {
                    showUserWindow(p);
            }else {
                Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
                dialogoAviso.setTitle("Usuário não encontrado!");
                dialogoAviso.setHeaderText("Usuário ou Senha Inválida!");
                dialogoAviso.setContentText("Por favor, digite um Usuário e Senha Válido!");
                dialogoAviso.showAndWait();
            }
        }else {
            Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
            dialogoAviso.setTitle("Não Encontrado!");
            dialogoAviso.setHeaderText("CAMPOS NÃO PREENCHIDOS!");
            dialogoAviso.setContentText("Por favor, preencha todos os campos de Login!");
            dialogoAviso.showAndWait();
        }
    }

    private void showUserWindow(Usuario item) throws Exception {
        Alert welcome = new Alert(Alert.AlertType.INFORMATION);
        welcome.setTitle("Login");
        welcome.setHeaderText("Autenticado!");
        welcome.setContentText("Bem vindo "+item.getNome()+"!");
        welcome.showAndWait();

        Home home = new Home();
        home.setUser(item);
        home.start(new Stage());
        login.myStage.close();
    }

    @FXML
    public void Registrar() {
        showJanelaCriarConta();
    }
    private void showJanelaCriarConta() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Criar conta");
        dialog.initOwner(telaLogin.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/cadastro_dialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("Deu ruim!!!" +e.getMessage());
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cadastro_dialog controller = fxmlLoader.getController();
            controller.processResult();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader
                .load(getClass().getResource("vision/login.fxml"));
        stage.setScene(new Scene(root,450,250));
        stage.setTitle("Autenticação");
        login.myStage = stage;
        stage.show();
    }

    @FXML
    public void pegaTecla(KeyEvent e) throws Exception{
        if(e.getCode() == KeyCode.ENTER){
            Entrar();
        }
    }
}
