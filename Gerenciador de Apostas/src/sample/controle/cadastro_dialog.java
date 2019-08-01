package sample.controle;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import sample.modelo.Cadastro;
import sample.modelo.Torneio;
import sample.modelo.Partida;
import sample.modelo.Usuario;

import java.io.IOException;
import java.time.LocalDate;

public class cadastro_dialog {

    @FXML
    private TextField tfnome;
    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField pfPass;

    @FXML
    private TextField tfTime1;
    @FXML
    private TextField tfTime2;
    @FXML
    private DatePicker dpJogo;
    @FXML
    private TextField tfTorneio;

    public Usuario processResult() {

        try{
            if(!Cadastro.getInstance().validaLogin(tfUser.getText(),tfnome.getText())){
                Usuario usuario = new Usuario(tfnome.getText(), tfUser.getText(), pfPass.getText());
                return usuario;
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cadastro Invalida");
                alert.setContentText("Login ou nome já Existente!");
                alert.showAndWait();
                return null;
            }
        }catch (Exception d){
            System.out.println(d.getMessage());
        }
        return null;
    }

    public Partida processResultPartida(Torneio t) {
        String time1 = tfTime1.getText();
        String time2 = tfTime2.getText();
        LocalDate horarios = dpJogo.getValue();
        Partida partida = new Partida(time1, time2, horarios);

        if(!tfTime1.getText().isEmpty() && !tfTime2.getText().isEmpty() && dpJogo.getValue() != null) {
            return partida;
        }
        else
            return null;
    }

    public Torneio processResultTorneio() throws IOException {
        String nomeTorneio = tfTorneio.getText();

        if(!tfTorneio.getText().isEmpty()) {
            Torneio torneio = new Torneio(nomeTorneio);
            return torneio;
        }
        else {
            return null;
        }
    }
}
