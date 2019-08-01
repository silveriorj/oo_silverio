package sample.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;
import sample.model.Manutencao.JDBCManutencaoDAO;
import sample.model.Manutencao.OrdemServico;
import sample.model.Usuario.Cliente;
import sample.model.Usuario.JDBCClienteDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class os_alterar {

    public JFXTextField tfCliente;
    public JFXTextField tfDataHoje;

    public JFXDatePicker dpDataPrev;
    public JFXTextField tfID;
    public JFXTextArea taDesc;
    public JFXTextField tfProtocolo;

    private ArrayList<String> autocomplete = new ArrayList<>();
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public void initialize(){
        for(Cliente list: JDBCClienteDAO.getInstance().list()){
            autocomplete.add(list.getNome());
        }
        TextFields.bindAutoCompletion(tfCliente, autocomplete);
    }

    void inicia_dados(OrdemServico os){
        tfProtocolo.setText("Protocolo: "+String.valueOf(os.getProtocolo()));
        tfCliente.setText("Nome: "+os.getNome_cliente());
        tfID.setText("Garra: "+String.valueOf(os.getId_cliente()));
        tfDataHoje.setText("Aberto em: "+df.format(os.getDatain()));
    }

    @FXML
    private void seleciona_cliente_by_ID(KeyEvent key){
        if(key.getCode() == KeyCode.TAB){
            for(Cliente c : JDBCClienteDAO.getInstance().list()){
                if(tfID.getText().equals(String.valueOf(c.getGarra()))){
                    tfCliente.setText(c.getNome());
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
                }
            }
        }
    }

    void finaliza_alteracao(OrdemServico os) throws Exception {
        os.setDescricao(taDesc.getText());
        os.setId_cliente(Integer.parseInt(tfID.getText()));
        os.setNome_cliente(tfCliente.getText());

        JDBCManutencaoDAO.getInstance().alterarOS(os);
    }
}
