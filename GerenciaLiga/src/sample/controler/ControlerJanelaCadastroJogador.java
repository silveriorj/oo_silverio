package sample.controler;

import javafx.fxml.FXML;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.model.Jogador;

import java.time.LocalDate;


public class ControlerJanelaCadastroJogador {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPeso;

    @FXML
    private TextField tfAltura;


    public Jogador processResult(){

        String nome = tfNome.getText();

        double peso = Double.valueOf(tfPeso.getText());
        double altura = Double.valueOf(tfAltura.getText());


        Jogador j = new Jogador();
        j.setNome(nome);
        j.setPeso(peso);
        j.setAltura(altura);

        return j;
    }


}
