package sample.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import sample.model.Jogador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ControlerJanelaPrincipal {

    @FXML
    private ListView<Jogador> ltvJogadores;

    @FXML
    private Text txNome;

    @FXML
    private Text txPeso;

    @FXML
    private Text txAltura;


    public void initialize(){
        atualizaLista();
    }

    private void atualizaLista(){
        ltvJogadores.getItems().clear();
        for(Jogador j:ControlerLiga.getInstance().listaJogadores()){
            ltvJogadores.getItems().add(j);
        }
    }

    @FXML
    private void mostraDetalhesJogador(){
        Jogador j = ltvJogadores.getSelectionModel().getSelectedItem();
        if(j!=null){
            txNome.setText("Nome:"+j.getNome());
            txPeso.setText("Peso:"+j.getPeso());
            txAltura.setText("Altura:"+j.getAltura());
        }
    }

    @FXML
    private void cadastraJogador(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Cadastrar Jogador");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/janelaCadastroJogador.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){
                ControlerJanelaCadastroJogador controle = fxmlLoader.getController();

                Jogador j = controle.processResult();
                if(j!=null){


                    try {
                        ControlerLiga.getInstance().addJogador(j);
                        atualizaLista();
                    }catch (SQLException e){
                        mensagem(Alert.AlertType.ERROR,e.getMessage());
                    }


                }else{
                    mensagem(Alert.AlertType.ERROR,"Dados inválidos!!!");
                }

            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    protected void mensagem(Alert.AlertType tipo, String mensagem){
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Alerta!!");
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }


}
