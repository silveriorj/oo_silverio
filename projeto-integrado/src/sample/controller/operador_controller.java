package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;
import sample.model.*;

import java.io.IOException;
import java.util.Optional;

public class operador_controller {

    public TableColumn tbProtocoloCol1;
    public TableColumn tbDescCol1;
    public TableColumn tbDataPrevCol1;

    @FXML
    private TableView<OrdemServico> tbOS = new TableView<>();
    @FXML
    private TableView<Cliente> tbCliente = new TableView<>();
    @FXML
    private TableColumn<Cliente, Integer> tbIDCol;
    @FXML
    private TableColumn<Cliente, String> tbNomeCol;
    @FXML
    private TableColumn<Endereco, String> tbEnderecoCol;

    @FXML
    private TextField tfNomeC, tfEmailC, tfFixoC, tfMovel, tfRua, tfNumero, tfCompl, tfBairro, tfCidade, tfID;

    private ObservableList<Cliente> clientes;
    private ObservableList<OrdemServico> os;

    public operador_controller() {
    }


    public void initialize() throws Exception {
        atualiza();
    }

    @FXML
    void detalhes_cliente(){
        Cliente c = tbCliente.getSelectionModel().getSelectedItem();
        if(c!=null){
            manutencao_controller.detalhes_cliente(c, tfID, tfNomeC, tfEmailC, tfFixoC, tfMovel, tfRua, tfNumero, tfCompl, tfBairro, tfCidade);
        }
    }

    void atualiza() throws Exception {
        clientes = FXCollections.observableArrayList(JDBCClienteDAO.getInstance().list());

        tbIDCol.setCellValueFactory(new PropertyValueFactory<>("garra"));
        tbNomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbEnderecoCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbCliente.setItems(clientes);

        os = FXCollections.observableArrayList(JDBCManutencaoDAO.getInstance().listarOS());

        tbProtocoloCol1.setCellValueFactory(new PropertyValueFactory<>("protocolo"));
        tbDescCol1.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbDataPrevCol1.setCellValueFactory(new PropertyValueFactory<>("data_criacao"));
        tbOS.setItems(os);

    }

    @FXML
    void abrir_os() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Adicionar Usuário");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/abertura_os.fxml"));
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
            abertura_os controller = fxmlLoader.getController();
            controller.finaliza();
        }
    }

    @FXML
    private void logoff() throws IOException {
        Main.setUser(null);
        Main.changeScreen("login");
    }
}
