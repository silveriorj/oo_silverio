package sample.controle;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.modelo.Cadastro;
import sample.modelo.Usuario;

import java.io.IOException;
import java.util.Optional;

public class login extends Application{

    @FXML
    public static Stage myStage;

    @FXML
    private Pane telaLogin;

    @FXML
    private TextField tfLogin;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private Text erro01;

    @FXML
    private BorderPane telaJogador;

    private static Cadastro cadastro;

    private static String NOME_ARQ_BIN= "Gerenciador de Apostas/user.bin";

    private ObservableList<Usuario> usuarios;

    private static Cadastro instance;

    public void criarConta() throws IOException {
        showJanelaCriarConta();
    }
    @FXML
    private javafx.scene.input.KeyEvent e;

    @FXML
    public void pegaTecla(KeyEvent e) throws Exception {
        if(e.getCode() == KeyCode.ENTER){
            logar();
        }
    }
    private void showJanelaCriarConta() throws IOException {

        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Criar conta");
        dialog.initOwner(telaLogin.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../visao/cadastro_dialog.fxml"));

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
            Usuario t = controller.processResult();

            Cadastro.getInstance().addUser(t);
        }


    }

    private void showJanelaAdmin(Usuario item) throws Exception {
        Alert welcome = new Alert(Alert.AlertType.INFORMATION);
        welcome.setTitle("Login");
        welcome.setHeaderText("Autenticado!");
        welcome.setContentText("Bem vindo Administrador!");
        welcome.showAndWait();

        admin_controller admin = new admin_controller();
        admin.setUser(item);
        admin.start(new Stage());
        login.myStage.close();


    }

    private void showJanelaJogador(Usuario item) throws Exception {
        Alert welcome = new Alert(Alert.AlertType.INFORMATION);
        welcome.setTitle("Login");
        welcome.setHeaderText("Autenticado!");
        welcome.setContentText("Bem vindo "+item.getNome()+"!");
        welcome.showAndWait();
        user_controller jUser = new user_controller();
        jUser.setAtualUser(item);
        jUser.start(new Stage());
        login.myStage.close();
    }


    public void logar() throws Exception {
        String login = tfLogin.getText();
        String senha = tfSenha.getText();

        if(autenticar(login, senha) == true) {
            Usuario p =  Cadastro.getInstance().login(login, senha);

            if(p.getTipo() == 0) {
                showJanelaAdmin(p);
            } else if (p.getTipo() == 1) {
                showJanelaJogador(p);
            }

        } else {
            Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
            dialogoAviso.setTitle("InvalidUser_Error_404");
            dialogoAviso.setHeaderText("Usuário ou Senha Inválido!");
            dialogoAviso.setContentText("Por favor, digite um Usuário e Senha Válido!");
            dialogoAviso.showAndWait();

        }

    }


    public boolean autenticar(String login, String senha) {

        Usuario p = Cadastro.getInstance().login(login, senha);

        if (p != null) {
            if(p.getUser().equals(login) && p.getPass().equals(senha)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader
                .load(getClass().getResource("visao/loginWindow.fxml"));
        stage.setScene(new Scene(root,600,400));
        stage.setTitle("Copa do Mundo 2018");
        login.myStage = stage;
        stage.show();

    }
}
