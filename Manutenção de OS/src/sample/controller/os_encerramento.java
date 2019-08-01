package sample.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sample.model.Manutencao.JDBCManutencaoDAO;
import sample.model.Manutencao.OrdemServico;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class os_encerramento {
    @FXML
    public JFXTextField tfID;
    @FXML
    public JFXTextField tfCliente;
    public JFXTextField tfProtocolo;
    public JFXTextField tfDataPrev;
    public JFXTextField tfDataHoje;
    @FXML
    private JFXTextArea taDesc;

    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    private static OrdemServico os;

    public void initialize(){
    }

    void inicia_dados(OrdemServico ors){
        LocalDate ld = LocalDate.now();
        os = ors;
        tfProtocolo.setText("Protocolo: "+String.valueOf(os.getProtocolo()));
        tfCliente.setText("Nome: "+os.getNome_cliente());
        tfID.setText("Garra: "+String.valueOf(os.getId_cliente()));
        tfDataHoje.setText("Aberto em: "+df.format(os.getDatain()));
        tfDataPrev.setText("Finalizado: "+df.format(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }

    void processa() throws Exception {
        LocalDate ld = LocalDate.now();

        System.out.println("OK");
        String servico = taDesc.getText();

        os.setServico(servico);
        os.setStatus(true);
        os.setDataout(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        JDBCManutencaoDAO.getInstance().finalizarOS(os);
    }
}
