package sample.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Manutencao.JDBCManutencaoDAO;
import sample.model.Manutencao.OrdemServico;
import sample.model.Usuario.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class controller_os {
    @FXML
    JFXComboBox<Funcionario> comboTec = new JFXComboBox<>();

    public void initialize(){
        ObservableList<Funcionario> tecnico = FXCollections.observableArrayList(JDBCFuncionarioDAO.getInstance().list_tecnico());
        comboTec.getItems().addAll(tecnico);

    }

//  ============================================= CONTROLLER MANTER O.S ============================================= //

    static void abertura_os() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Abertura de Ordem de Serviço");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(controller_os.class.getResource("../view/os/abertura_os.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            os_abertura controller = fxmlLoader.getController();
            controller.finaliza();
        }
    }

    static void altera_os(OrdemServico os) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Alterar Ordem de Serviço");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(controller_os.class.getResource("../view/os/altera_os.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        os_alterar controller = fxmlLoader.getController();
        controller.inicia_dados(os);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            controller.finaliza_alteracao(os);
        }
    }

    @FXML
    static void delete_os(OrdemServico os) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmação!");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(controller_os.class.getResource("../view/dialogs/dialog_remove_os.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            JDBCManutencaoDAO.getInstance().cancelarOS(os.getProtocolo());
        }
    }

    static void atribuir_funcionario(OrdemServico os) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Usuário");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(controller_os.class.getResource("../view/os/atribuir_tecnico.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            controller_os controller = fxmlLoader.getController();
            controller.atribui(os);
        }
    }

    private void atribui(OrdemServico os) throws Exception {
        Funcionario tec = comboTec.getSelectionModel().getSelectedItem();
        if(tec instanceof Tecnico){
            JDBCManutencaoDAO.getInstance().atribuirOS(tec, os);
        }else{
            Alerta.nao_selecionado();
        }
    }

    static void encerra_os(OrdemServico os) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Finalizar Ordem de Serviço");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(controller_os.class.getResource("../view/os/encerramento.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        os_encerramento controller = fxmlLoader.getController();
        controller.inicia_dados(os);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            controller.processa();
        }
    }

    static void detalhes_os(OrdemServico os, TextArea taConsiders, TextField tfCidadeCliente, TextField tfBairroCliente, TextField tfComplCliente, TextField tfNumeroCliente, TextField tfRuaCliente, JFXTextField cbTecnic, TextField tfClient, TextField tfDateEnding, TextField tfDateOpening, JFXTextArea tfDescription, TextField tfProtocol) throws Exception {
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");

        Funcionario tec = JDBCFuncionarioDAO.getInstance().search(os.getId_funcionario());
        for(Cliente c : JDBCClienteDAO.getInstance().list()){
            if(c.getGarra() == os.getId_cliente()){
                tfProtocol.setText(String.valueOf(os.getProtocolo()));
                tfDateOpening.setText(df.format(os.getDatain()));
                tfClient.setText(os.getNome_cliente());
                tfDescription .setText("Descrição do Problema:\n\n"+os.getDescricao());
                if(os.getDataout()!=null){tfDateEnding.setText(String.valueOf(os.getDataout()));}
                if(tec!=null){cbTecnic.setText("Técnico Responsável: "+tec.getNome());}
                getEndereco(tfCidadeCliente, tfBairroCliente, tfComplCliente, tfNumeroCliente, tfRuaCliente, c);
                if(os.getServico()!=null){
                    taConsiders.setText(os.getServico());
                }
            }
        }
    }

// ============================================ CONTROLLER FUNÇÕES CLIENTES ========================================== //

    static void detalhes_cliente(Cliente cliente, TextField tfID, TextField tfNome, TextField tfEmail, TextField tfFixo, TextField tfMovel, TextField tfRua, TextField tfNumero, TextField tfComplemento, TextField tfBairro, TextField tfCidade){
        tfID.setText(String.valueOf(cliente.getGarra()));
        tfNome.setText(cliente.getNome());
        tfEmail.setText(cliente.getEmail());

        int a = 0;
        if(cliente.getTelefone()!=null) {
            for (Telefone t : cliente.getTelefone()) {
                if (t != null && a == 0) {
                    tfFixo.setText(String.valueOf(t));
                } else if (t != null) {
                    tfMovel.setText(String.valueOf(t));
                }
                a++;
            }
        }
        getEndereco(tfCidade, tfBairro, tfComplemento, tfNumero, tfRua, cliente);
    }

    private static void getEndereco(TextField tfCidadeCliente, TextField tfBairroCliente, TextField tfComplCliente, TextField tfNumeroCliente, TextField tfRuaCliente, Cliente c) {
        if(c.getEndereco()!=null){
            if(c.getEndereco().getRua()!=null){
                tfRuaCliente.setText(c.getEndereco().getRua());
            }if(c.getEndereco().getNumero()!=null){
                tfNumeroCliente.setText(c.getEndereco().getNumero());
            }if(c.getEndereco().getComplemento()!=null){
                tfComplCliente.setText(c.getEndereco().getComplemento());
            }if(c.getEndereco().getBairro()!=null){
                tfBairroCliente.setText(c.getEndereco().getBairro());
            }if(c.getEndereco().getCidade()!=null){
                tfCidadeCliente.setText(c.getEndereco().getCidade());
            }
        }
    }
}
