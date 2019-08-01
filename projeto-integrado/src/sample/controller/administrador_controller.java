package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

public class administrador_controller {
    // DADOS FUNCIONÁRIOS
    @FXML
    private TextField tfNome, tfEmail, tfUsuario, tfSenha, tfTipo;
    // DADOS CLIENTES
    @FXML
    private TextField tfNomeCliente, tfGarra, tfEmailCliente, tfRua, tfBairro, tfCidade, tfNumero, tfComplemento, tfFixo, tfMovel;
    // DADOS O.S's
    @FXML
    private TextField tfProtocolo, tfCliente, tfFunc, tfDataIn, tfDataIn2, tfDataOut, tfPrazo;
    @FXML
    private TextArea txDescricao;

    @FXML
    private ListView<Funcionario> ltvwUser;
    @FXML
    private ListView<OrdemServico> ltvwOs;

    @FXML
    private ContextMenu contextCliente;

    //private controles_funcoes controle = new controles_funcoes();

    @FXML
    private ListView<Cliente> ltvwCliente;

    public void initialize() throws Exception {
        //controle.populaOS(ltvwOs);
        ltvwOs.getItems().addAll(JDBCManutencaoDAO.getInstance().listarOS());

        populaList();

        contextCliente = new ContextMenu();

        MenuItem deleteUser = new MenuItem("Apagar");
        MenuItem addUser = new MenuItem("Adicionar");

        deleteUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(ltvwUser.getSelectionModel().getSelectedItem() != null) {
                    deleteUser.setDisable(false);
                    try {
                        deleteFuncionario();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    deleteUser.setDisable(true);
                }
            }
        });
        addUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    addFuncionario();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        contextCliente.getItems().addAll(deleteUser, addUser);
        ltvwUser.setCellFactory(new Callback<ListView<Funcionario>, ListCell<Funcionario>>() {
            @Override
            public ListCell<Funcionario> call(ListView<Funcionario> param) {
                ListCell<Funcionario> cell = new ListCell<Funcionario>() {
                    @Override
                    protected void updateItem(Funcionario item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getNome());
                            setTextFill(Color.BLACK);
                        }
                    }
                };
                cell.emptyProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasEmpty, Boolean isNowEmpty) {
                        if (isNowEmpty) {
                            cell.setContextMenu(null);
                        } else {
                            cell.setContextMenu(contextCliente);
                        }
                    }
                });
                return cell;
            }
        });
    }


    /*====================================== MANTER CLIENTES ========================================= */

    @FXML
    private void addCliente(){
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Adicionar Cliente");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/cadastro_cliente.fxml"));
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
            cadastro_clientes controller = fxmlLoader.getController();
            controller.processResult();
        }
        populaList();
    }


    // MOSTRA AS INFORMAÇÕES DO USUÁRIO SELECIONADO
    @FXML
    private void actionShowUserDetails(){
        if(ltvwUser.getSelectionModel().getSelectedItem() != null) {
            Funcionario u = ltvwUser.getSelectionModel().getSelectedItem();
            tfNome.setText(u.getNome());
            tfEmail.setText(u.getEmail());
            tfUsuario.setText(u.getUser());
            tfSenha.setText(u.getSenha());
            tfTipo.setText(String.valueOf(u.getTipo()));
        }
    }
    @FXML
    private void actionShowClienteDetails(){
        if(ltvwCliente.getSelectionModel().getSelectedItem() != null) {
            Cliente c = ltvwCliente.getSelectionModel().getSelectedItem();
            manutencao_controller.detalhes_cliente(c, tfGarra, tfNomeCliente, tfEmailCliente, tfFixo, tfMovel, tfRua, tfNumero, tfComplemento, tfBairro, tfCidade);
        }
    }

    // ATUALIZA AS INFORMAÇÕES DE USUÁRIO
    @FXML
    private void actionUpdateUsers() throws Exception {
        if(ltvwUser.getSelectionModel().getSelectedItem() != null) {
            Funcionario u = ltvwUser.getSelectionModel().getSelectedItem();

            u.setNome(tfNome.getText());
            u.setEmail(tfEmail.getText());
            u.setUser(tfUsuario.getText());
            u.setSenha(tfSenha.getText());
            u.setTipo(Integer.parseInt(tfTipo.getText()));
            JDBCFuncionarioDAO.getInstance().update(u);
        }
        populaList();
    }


    private void populaList(){
        // LISTA DE FUNCIONARIOS CADASTRADOS
        ltvwUser.getItems().clear();
        for(Funcionario f: JDBCFuncionarioDAO.getInstance().list()){
            ltvwUser.getItems().add(f);
        }
        // LISTA DE CLIENTES CADASTRADOS
        ltvwCliente.getItems().clear();
        for(Cliente c: JDBCClienteDAO.getInstance().list()){
            ltvwCliente.getItems().add(c);
        }
    }
    @FXML
    private void addFuncionario() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Adicionar Usuário");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/cadastro_funcionarios_view.fxml"));
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
            cadastro_funcionarios controller = fxmlLoader.getController();
            controller.cadastra();
        }
        populaList();
    }

    @FXML
    private void deleteFuncionario() throws Exception {
        if(ltvwUser.getSelectionModel().getSelectedItem() != null){
            Funcionario u = ltvwUser.getSelectionModel().getSelectedItem();
            //controle.deleteFunc(u);
            populaList();
        }
    }

    // CONTROLE DE ORDENS DE SERVIÇO
    @FXML
    private void ListOS() throws Exception {
        //controle.populaOS(ltvwOs);
        ltvwCliente.getItems().clear();
        ltvwOs.getItems().clear();

        ltvwOs.getItems().addAll(JDBCManutencaoDAO.getInstance().listarOS());
        ltvwCliente.getItems().addAll(JDBCClienteDAO.getInstance().list());
    }

    @FXML
    private void addOS() throws Exception {
        //controle.actionAddOS(ltvwOs);
    }

    @FXML
    private void detailsOS(){
        //controles_funcoes.detailsOS(ltvwOs, tfProtocolo, tfCliente, txDescricao, tfFunc, tfDataIn, tfPrazo, tfDataOut);
    }


    /* ------------------------------------------ FIM --------------------------------------------- */



    // FECHA A JANELA E RETORNA A JANELA DE LOGIN (LOGOUT)
    @FXML
    private void actionExit() throws Exception {
        Main.changeScreen("login");
        Main.setUser(null);
    }
}
