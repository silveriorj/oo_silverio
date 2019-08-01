package sample.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;
import sample.model.Manutencao.JDBCManutencaoDAO;
import sample.model.Manutencao.OrdemServico;
import sample.model.Usuario.Cliente;
import sample.model.Usuario.Endereco;
import sample.model.Usuario.JDBCClienteDAO;

import java.io.IOException;

public class f_operador_controller {

    public TableColumn<OrdemServico, SimpleIntegerProperty> tbProtocoloCol1;
    public TableColumn<OrdemServico, SimpleStringProperty> tbDescCol1;

    public TextArea taConsiders;
    public TextField tfCidadeCliente;
    public TextField tfBairroCliente;
    public TextField tfComplCliente;
    public TextField tfNumeroCliente;
    public TextField tfRuaCliente;
    public JFXTextField cbTecnic;
    public TextField tfClient;
    public TextField tfDateEnding;
    public TextField tfDateOpening;
    public JFXTextArea tfDescription;
    public TextField tfProtocol;


    @FXML
    private TableView<OrdemServico> tbOS = new TableView<>();
    @FXML
    private TableView<Cliente> tbCliente = new TableView<>();
    @FXML
    private TableColumn<Cliente, SimpleIntegerProperty> tbIDCol;
    @FXML
    private TableColumn<Cliente, SimpleStringProperty> tbNomeCol;
    @FXML
    private TableColumn<Endereco, SimpleStringProperty> tbEnderecoCol;

    @FXML
    private TextField tfNomeC, tfEmailC, tfFixoC, tfMovel, tfRua, tfNumero, tfCompl, tfBairro, tfCidade, tfID;

    public void initialize() throws Exception {
        atualiza();
    }


// ============================================ CONTROLLER MANTER O.S ================================================ //

    @FXML
    void ABERTURA_OS() throws Exception {
        controller_os.abertura_os();
        atualiza();
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
        atualiza();
    }

    @FXML
    void ATRIBUIR_TECNICO() throws Exception {
        OrdemServico os = tbOS.getSelectionModel().getSelectedItem();
        if(os!= null){
            controller_os.atribuir_funcionario(os);
            atualiza();
        }else{
            Alerta.nao_selecionado();
        }
    }

    @FXML
    void DETALHES_OS() throws Exception {
        limpa_tela();
        OrdemServico os = tbOS.getSelectionModel().getSelectedItem();
        if(os!=null){
            controller_os.detalhes_os(os, taConsiders, tfCidadeCliente, tfBairroCliente, tfComplCliente, tfNumeroCliente, tfRuaCliente, cbTecnic, tfClient, tfDateEnding, tfDateOpening, tfDescription, tfProtocol);
        }
    }

// ========================================== CONTROLER MANTER CLIENTES ============================================== //

    @FXML
    void ADICIONA_CLIENTE() throws Exception {
        controller_usuarios.ADICIONA_CLIENTE();
        atualiza();
    }

    @FXML
    void DELETE_CLIENTE() throws Exception {
        Cliente c = tbCliente.getSelectionModel().getSelectedItem();
        if(c!=null) {
            controller_usuarios.DELETE_CLIENTE(c);
        }else{
            Alerta.nao_selecionado();
        }
        atualiza();
    }

    @FXML
    void DETALHES_CLIENTE() {
        limpa_tela();
        Cliente c = tbCliente.getSelectionModel().getSelectedItem();
        if(c!=null){
            controller_os.detalhes_cliente(c, tfID, tfNomeC, tfEmailC, tfFixoC, tfMovel, tfRua, tfNumero, tfCompl, tfBairro, tfCidade);
        }
    }

// ============================================ CONTROLLER DE SCENE ===================================================//

    private void atualiza() throws Exception {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(JDBCClienteDAO.getInstance().list());

        tbIDCol.setCellValueFactory(new PropertyValueFactory<>("garra"));
        tbNomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbEnderecoCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbCliente.setItems(clientes);

        ObservableList<OrdemServico> os = FXCollections.observableArrayList(JDBCManutencaoDAO.getInstance().listarOS());

        tbProtocoloCol1.setCellValueFactory(new PropertyValueFactory<>("protocolo"));
        tbDescCol1.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbOS.setItems(os);
    }

    private void limpa_tela() {
        taConsiders.setText("");
        tfCidadeCliente.setText("");
        tfBairroCliente.setText("");
        tfComplCliente.setText("");
        tfNumeroCliente.setText("");
        tfRuaCliente.setText("");
        cbTecnic.setText("");
        tfClient.setText("");
        tfDateEnding.setText("");
        tfDateOpening.setText("");
        tfDescription.setText("");
        tfProtocol.setText("");

        tfID.setText("");
        tfCidade.setText("");
        tfBairro.setText("");
        tfNumero.setText("");
        tfRua.setText("");
        tfFixoC.setText("");
        tfMovel.setText("");
        tfCompl.setText("");
        tfEmailC.setText("");
    }

// =================================================== FINALIZA SCENE =================================================//

    @FXML
    private void logoff() throws IOException {
        Main.setUser(null);
        Main.changeScreen("login");
    }
}
