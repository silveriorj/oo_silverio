package sample.controle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;
import sample.modelo.*;

import java.util.ArrayList;


public class janela_apostas {

    @FXML
    private Label lbTimeA;

    @FXML
    private Label lbTimeB;

    @FXML
    private TextField tfGolA;

    @FXML
    private TextField tfGolB;

    @FXML
    private Label lbData;

    @FXML
    private Label lbVencedor;

    public void carregarDados(Partida j) {
        lbTimeA.setText(j.getTime1());
        lbTimeB.setText(j.getTime2());
        lbData.setText(Torneio.df.format(j.getData()));

        if(j.getWinner() != null) {
            tfGolA.setText(String.valueOf(j.getR1()));
            tfGolB.setText(String.valueOf(j.getR2()));
            lbVencedor.setText(j.getWinner());
        }
    }



    public Aposta processResult(Partida p, Usuario u) {

        if(!tfGolA.getText().isEmpty() && !tfGolB.getText().isEmpty()) {

            String golA;
            String golB;
            String vencedor;

            try {
                golA = tfGolA.getText();
                golB = tfGolB.getText();
                if(Integer.parseInt(golA) > Integer.parseInt(golB)){
                    vencedor = lbTimeA.getText();
                }
                else if(Integer.parseInt(golA) > Integer.parseInt(golB)){
                    vencedor = lbTimeB.getText();
                }
                else{
                    vencedor = "Empate";
                }
            } catch (Exception e){
                e.getMessage();
                return null;
            }

            Aposta res = new Aposta(golA, golB, vencedor, p, u);
            return res;
        }

        return null;
    }
}
