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
import sample.model.Manutencao.OrdemServico;
import sample.model.Usuario.Funcionario;
import sample.model.Manutencao.JDBCManutencaoDAO;

import java.io.IOException;

public class f_tecnico_controller {

    public TextField tfCidadeCliente, tfCidadeCliente1, tfCidadeClientefinal;
    public TextArea taConsiders, taConsiders1, taConsidersfinal;
    public TextField tfBairroCliente, tfBairroCliente1, tfBairroClientefinal;
    public TextField tfComplCliente, tfComplCliente1, tfComplClientefinal;
    public TextField tfNumeroCliente, tfNumeroCliente1, tfNumeroClientefinal;
    public TextField tfRuaCliente, tfRuaCliente1, tfRuaClientefinal;
    public JFXTextField cbTecnic, cbTecnic1, cbTecnicfinal;
    public TextField tfDateEnding, tfDateEnding1, tfDateEndingfinal;
    public TextField tfDateOpening, tfDateOpening1, tfDateOpeningfinal;
    public JFXTextArea tfDescription, tfDescription1, tfDescriptionfinal;
    public TextField tfClient, tfClient1, tfClientfinal;
    public TextField tfProtocol, tfProtocol1, tfProtocolfinal;
    public TableColumn<OrdemServico, SimpleStringProperty> tbDescCol1, tbDescCol11, tbDescColfinal;
    public TableColumn<OrdemServico, SimpleIntegerProperty> tbProtocoloCol1, tbProtocoloCol11, tbProtocolofinal;
    public TableView<OrdemServico> tbOS, tbOS1, tbOSfinalizada;


    private static Funcionario tecnico;

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

        if(os!= null) {
            controller_os.atribuir_funcionario(os);
            atualiza();
        }else{
            Alerta.nao_selecionado();
        }
    }

    @FXML
    void FINALIZA_OS() throws Exception {
        OrdemServico os = tbOS1.getSelectionModel().getSelectedItem();
        if(os!=null){
            controller_os.encerra_os(os);
        }else{
            Alerta.nao_selecionado();
        }
        atualiza();
    }

    @FXML
    void DETALHES_OS() throws Exception {
        limpa_tela();
        OrdemServico os = tbOS.getSelectionModel().getSelectedItem();
        if(os!=null){
            controller_os.detalhes_os(os, taConsiders, tfCidadeCliente, tfBairroCliente, tfComplCliente, tfNumeroCliente, tfRuaCliente, cbTecnic, tfClient, tfDateEnding, tfDateOpening, tfDescription, tfProtocol);
        }
    }

    @FXML
    void DETALHES_OS_TECNICA() throws Exception {
        limpa_tela();
        OrdemServico os_tec = tbOS1.getSelectionModel().getSelectedItem();
        if(os_tec!=null){
            controller_os.detalhes_os(os_tec, taConsiders1, tfCidadeCliente1, tfBairroCliente1, tfComplCliente1,
                    tfNumeroCliente1, tfRuaCliente1, cbTecnic1, tfClient1, tfDateEnding1,
                    tfDateOpening1, tfDescription1, tfProtocol1);
        }
    }

    @FXML
    void DETALHES_OS_FINALIZADAS() throws Exception {
        limpa_tela();
        OrdemServico os_tec = tbOSfinalizada.getSelectionModel().getSelectedItem();
        if(os_tec!=null){
            controller_os.detalhes_os(os_tec, taConsidersfinal, tfCidadeClientefinal, tfBairroClientefinal, tfComplClientefinal,
                    tfNumeroClientefinal, tfRuaClientefinal, cbTecnicfinal, tfClientfinal, tfDateEndingfinal,
                    tfDateOpeningfinal, tfDescriptionfinal, tfProtocolfinal);
        }
    }

// ============================================= CONTROLLER DAS SCENES =============================================== //

    private void atualiza() throws Exception {

        ObservableList<OrdemServico> os = FXCollections.observableArrayList(JDBCManutencaoDAO.getInstance().listarOS());
        tbProtocoloCol1.setCellValueFactory(new PropertyValueFactory<>("protocolo"));
        tbDescCol1.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbOS.setItems(os);

        ObservableList<OrdemServico> os_tec = FXCollections.observableArrayList(JDBCManutencaoDAO.getInstance().listar_abertas(tecnico.getId()));
        tbProtocoloCol11.setCellValueFactory(new PropertyValueFactory<>("protocolo"));
        tbDescCol11.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbOS1.setItems(os_tec);

        ObservableList<OrdemServico> os_end = FXCollections.observableArrayList(JDBCManutencaoDAO.getInstance().listar_encerradas());
        tbProtocolofinal.setCellValueFactory(new PropertyValueFactory<>("protocolo"));
        tbDescColfinal.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbOSfinalizada.setItems(os_end);

    }

    private void limpa_tela(){
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
    }

    public static void setTecnico(Funcionario tecnico) {
        f_tecnico_controller.tecnico = tecnico;
    }

    public static Funcionario getTecnico() {
        return tecnico;
    }

    @FXML
    void logoff() throws IOException {
        Main.setUser(null);
        Main.changeScreen("login");
    }
}
