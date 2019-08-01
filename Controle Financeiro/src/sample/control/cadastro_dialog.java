package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.model.JDBCUsuarioDAO;
import sample.model.Usuario;

public class cadastro_dialog {

    @FXML
    private TextField tfnome;
    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField pfPass;

    void processResult() {
        String nome, user, senha;
        nome = tfnome.getText();
        user = tfUser.getText();
        senha = pfPass.getText();

        try{
            Encrypt e = new Encrypt();
            String pwd = e.encrypt(senha);
            Usuario u = new Usuario(nome, user, pwd);

            JDBCUsuarioDAO.getInstance().create(u);
        }catch (Exception d){
            System.out.println(d.getMessage());
        }
    }
}
