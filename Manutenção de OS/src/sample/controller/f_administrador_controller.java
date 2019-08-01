package sample.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;
import sample.model.Manutencao.JDBCManutencaoDAO;
import sample.model.Manutencao.OrdemServico;
import sample.model.Usuario.Cliente;
import sample.model.Usuario.Funcionario;
import sample.model.Usuario.JDBCClienteDAO;
import sample.model.Usuario.JDBCFuncionarioDAO;

public class f_administrador_controller {

    public TableView<Funcionario> tbFuncionario;
    public TableColumn<Funcionario, Integer> tbIDColuna;
    public TableColumn<Funcionario, String> tbFuncColuna;

    public TableView<Cliente> tbCliente;
    public TableColumn<Cliente, Integer> tbIDCol;
    public TableColumn<Cliente, String> tbNomeCol;
    public TableColumn<Cliente, String> tbEnderecoCol;

    public TableView<OrdemServico> tbOS;
    public TableColumn<OrdemServico, SimpleIntegerProperty> tbProtocoloCol1;
    public TableColumn<OrdemServico, SimpleStringProperty> tbDescCol1;

    public TextField tfProtocol;
    public TextField tfClient;
    public JFXTextArea tfDescription;
    public TextField tfDateOpening;
    public TextField tfDateEnding;
    public JFXTextField cbTecnic;
    public TextField tfRuaCliente;
    public TextField tfNumeroCliente;
    public TextField tfComplCliente;
    public TextField tfBairroCliente;
    public TextField tfCidadeCliente;
    public TextArea taConsiders;

    public ToggleButton btUpdate;

    // DADOS FUNCIONÁRIOS
    @FXML
    private TextField tfid, tfNome, tfEmail, tfUsuario, tfSenha, tfTipo;
    // DADOS CLIENTES
    @FXML
    private TextField tfNomeCliente, tfGarra, tfEmailCliente, tfRua, tfBairro, tfCidade, tfNumero, tfComplemento, tfFixo, tfMovel;

    public void initialize() throws Exception {
        populaList();
    }


// ========================================== CONTROLER MANTER CLIENTES ============================================== //

    @FXML
    private void ADICIONA_CLIENTE() throws Exception {
        controller_usuarios.ADICIONA_CLIENTE();
        populaList();
    }

    @FXML
    private void DELETE_CLIENTE() throws Exception {
        Cliente c = tbCliente.getSelectionModel().getSelectedItem();
        if(c!=null){
            controller_usuarios.DELETE_CLIENTE(c);
        }else{
            Alerta.nao_selecionado();
        }
        populaList();
    }

    @FXML
    private void DETALHES_CLIENTE(){
        if(tbCliente.getSelectionModel().getSelectedItem() != null) {
            Cliente c = tbCliente.getSelectionModel().getSelectedItem();
            controller_os.detalhes_cliente(c, tfGarra, tfNomeCliente, tfEmailCliente, tfFixo, tfMovel, tfRua, tfNumero, tfComplemento, tfBairro, tfCidade);
        }
    }

// ======================================== CONTROLLER MANTER FUNCIONÁRIOS =========================================== //

    @FXML
    private void ADICIONA_FUNCIONARIO() throws Exception {
        controller_usuarios.ADICIONA_FUNCIONARIO();
        populaList();
    }

    @FXML
    private void ALTERA_FUNCIONARIO() throws Exception {
        if (tbFuncionario.getSelectionModel().getSelectedItem() != null) {
            Funcionario u = tbFuncionario.getSelectionModel().getSelectedItem();

            u.setNome(tfNome.getText());
            u.setEmail(tfEmail.getText());
            u.setUser(tfUsuario.getText());
            u.setSenha(tfSenha.getText());
            u.setTipo(Integer.parseInt(tfTipo.getText()));
            JDBCFuncionarioDAO.getInstance().update(u);
        } else {
            Alerta.nao_selecionado();
        }
        populaList();
    }

    @FXML
    private void DELETE_FUNCIONARIO() throws Exception {
        if(tbFuncionario.getSelectionModel().getSelectedItem() != null){
            Funcionario f = tbFuncionario.getSelectionModel().getSelectedItem();
            JDBCFuncionarioDAO.getInstance().delete(f);
            populaList();
        }
    }

    @FXML
    private void DETALHES_FUNCIONARIO(){
        if(tbFuncionario.getSelectionModel().getSelectedItem() != null) {
            Funcionario u = tbFuncionario.getSelectionModel().getSelectedItem();
            tfid.setText("ID: "+String.valueOf(u.getId()));
            tfNome.setText(u.getNome());
            tfEmail.setText(u.getEmail());
            tfUsuario.setText(u.getUser());
            tfSenha.setText(u.getSenha());
            if(u.getTipo()==0){
                tfTipo.setText("ADMINISTRADOR");
            }else if(u.getTipo()==1){
                tfTipo.setText("OPERADOR");
            }else if(u.getTipo()==2){
                tfTipo.setText("TÉCNICO");
            }
        }
    }

// ============================================ CONTROLLER MANTER O.S ================================================ //

    @FXML
    private void ADICIONA_OS() throws Exception {
        controller_os.abertura_os();
    }

    @FXML
    void ALTERA_OS() throws Exception {
        OrdemServico os = tbOS.getSelectionModel().getSelectedItem();
        if(os!=null){
            controller_os.altera_os(os);
        }else{
            Alerta.nao_selecionado();
        }
    }

    @FXML
    void DELETE_OS() throws Exception {
        OrdemServico os = tbOS.getSelectionModel().getSelectedItem();
        if(os!=null){
            controller_os.delete_os(os);
        }else{
            Alerta.nao_selecionado();
        }
        populaList();
    }

    @FXML
    void ATRIBUIR_TECNICO() throws Exception {
        OrdemServico os = tbOS.getSelectionModel().getSelectedItem();
        if(os!= null){
            controller_os.atribuir_funcionario(os);
            populaList();
        }else{
            Alerta.nao_selecionado();
        }
    }

    @FXML
    private void DETALHES_OS() throws Exception {
        OrdemServico os = tbOS.getSelectionModel().getSelectedItem();
        if (os != null) {
            controller_os.detalhes_os(os, taConsiders, tfCidadeCliente, tfBairroCliente, tfComplCliente, tfNumeroCliente, tfRuaCliente, cbTecnic, tfClient, tfDateEnding, tfDateOpening, tfDescription, tfProtocol);
        }
    }

// ---------------------------------------- SCRIPT DE POVOAMENTO DAS TABELAS ----------------------------------------- //

    private void populaList() throws Exception {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(JDBCClienteDAO.getInstance().list());
        tbIDCol.setCellValueFactory(new PropertyValueFactory<>("garra"));
        tbNomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbEnderecoCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbCliente.setItems(clientes);

        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList(JDBCFuncionarioDAO.getInstance().list());
        tbIDColuna.setCellValueFactory(new PropertyValueFactory<>("garra"));
        tbFuncColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbFuncionario.setItems(funcionarios);

        ObservableList<OrdemServico> os = FXCollections.observableArrayList(JDBCManutencaoDAO.getInstance().listarOS());
        tbProtocoloCol1.setCellValueFactory(new PropertyValueFactory<>("protocolo"));
        tbDescCol1.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbOS.setItems(os);
    }

/* ------------------------------------------------------------------------------------------------------------------ */
/* ================================================== FIM ========================================================== */
/* ---------------------------------------------------------------------------------------------------------------- */

    @FXML
    private void logoff() throws Exception {
        Main.changeScreen("login");
        Main.setUser(null);
    }
}
