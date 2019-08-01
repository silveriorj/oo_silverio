package sample.controler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.model.Jogador;
import sample.model.Time;


public class ControlerJanelaCadastroTime {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfAno;


    public Time processResult(){

        String nome = tfNome.getText();
        String cidade = tfCidade.getText();
        int anoFundacao = Integer.valueOf(tfAno.getText());

        Time t = new Time();
        t.setNome(nome);
        t.setCidade(cidade);
        t.setAnoFundacao(anoFundacao);

        return t;
    }


}
