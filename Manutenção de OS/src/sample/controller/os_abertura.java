package sample.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;
import sample.model.Usuario.Cliente;
import sample.model.Usuario.JDBCClienteDAO;
import sample.model.Manutencao.JDBCManutencaoDAO;
import sample.model.Manutencao.OrdemServico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class os_abertura {

    public JFXTextField tfCliente;
    public JFXTextField tfDataHoje;

    public JFXDatePicker dpDataPrev;
    public JFXTextField tfID;
    public JFXTextArea taDesc;

    private ArrayList<String> autocomplete = new ArrayList<String>();


    public void initialize(){
        tfDataHoje.setText("Aberto em: "+ LocalDate.now());
        for(Cliente list: JDBCClienteDAO.getInstance().list()){
            autocomplete.add(list.getNome());
        }
        TextFields.bindAutoCompletion(tfCliente, autocomplete);
    }

    @FXML
    private void seleciona_cliente_by_ID(KeyEvent key){
        if(key.getCode() == KeyCode.TAB){
            for(Cliente c : JDBCClienteDAO.getInstance().list()){
                if(tfID.getText().equals(String.valueOf(c.getGarra()))){
                    tfCliente.setText(c.getNome());
                    tfCliente.setDisable(true);
                    tfID.setDisable(true);
                }
            }
        }
    }

    @FXML
    private void seleciona_cliente_by_NOME(KeyEvent key){
        if(key.getCode() == KeyCode.TAB){
            for(Cliente c : JDBCClienteDAO.getInstance().list()){
                if(tfCliente.getText().equals(String.valueOf(c.getNome()))){
                    tfID.setText(String.valueOf(c.getGarra()));
                    tfID.setDisable(true);
                    tfCliente.setDisable(true);
                }
            }
        }
    }

    void finaliza() throws Exception {
        String nome, desc;
        int id;
        Date dataprev;

        if(tfCliente != null && tfID != null && dpDataPrev != null){
            nome = tfCliente.getText();
            id = Integer.parseInt(tfID.getText());
            desc = taDesc.getText();
            dataprev = java.sql.Date.valueOf(dpDataPrev.getValue());

            OrdemServico os  = new OrdemServico(desc, dataprev, id, nome);

            JDBCManutencaoDAO.getInstance().criarOS(os);
        }else{
            Alerta.user_not_filled();
        }
    }
}
