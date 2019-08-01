package sample.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;
import sample.model.Cliente;
import sample.model.JDBCClienteDAO;
import sample.model.JDBCManutencaoDAO;
import sample.model.OrdemServico;

import java.time.LocalDate;
import java.util.ArrayList;

public class abertura_os {

    public JFXTextField tfCliente;
    public JFXTextField tfDataHoje;

    public JFXDatePicker dpDataPrev;
    public JFXTextField tfID;
    public JFXTextArea taDesc;

    private ArrayList<Integer> autocomplete = new ArrayList<Integer>();


    public void initialize(){

    }

    @FXML
    private void seleciona_cliente(KeyEvent key){
        if(key.getCode() == KeyCode.ENTER){
            for(Cliente c : JDBCClienteDAO.getInstance().list()){
                if(tfID.getText().equals(String.valueOf(c.getGarra()))){
                    tfCliente.setText(c.getNome());
                    tfCliente.setDisable(true);
                }
            }
        }
    }

    public void finaliza() throws Exception {
        LocalDate date = dpDataPrev.getValue();
        String desc = taDesc.getText();
        int id = Integer.parseInt(tfID.getText());
        LocalDate datenow = LocalDate.now();

        OrdemServico os = new OrdemServico(desc, datenow, id);

        JDBCManutencaoDAO.getInstance().criarOS(os);
    }
}
