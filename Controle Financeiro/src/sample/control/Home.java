package sample.control;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


public class Home extends Application {

    private static Stage myStage;   // SETA A JANELA ATUAL COMO ESTATICA PARA MANIPULAÇÃO
    private static Usuario user;    // SETA O USUARIO ATUAL COMO ESTATICO

    @FXML
    private Label lbUser, tfTotal, lbRegPaid, lbRegPend, lbTotalReg, lbDespesasPeriodo, tfPendencias;

    @FXML
    private TextField tfValor;

    @FXML
    private TextField fxRegistroCateg;

    @FXML
    private ComboBox<Categoria> cbCategoria, cbSearchCategoria;

    @FXML
    private ComboBox<SubCategoria> cbSubCategoria;

    @FXML
    private DatePicker dpDataLancamento, dpDataIni, dpDataFim, dpDataPesq;

    @FXML
    private PieChart grafoPizza;

    @FXML
    private PieChart grafoFrequencia;

    @FXML
    private PieChart grafoSubCategoria;

    // TABELA
    @FXML
    private TableView<Lancamento> tabela, tabelaLis;

    @FXML
    private TableColumn<Usuario, Double> colTrans, valorCol;
    @FXML
    private TableColumn<Usuario, String> colCateg, colSubCat, subcatCol, catCol;
    @FXML
    private TableColumn<Usuario, String> colData, dataCol;
    @FXML
    private TableColumn<Usuario, String>  colStatus, statusCol;

    private funcoes_geral funcao = new funcoes_geral();
    private Graficos grafico = new Graficos();

    @FXML
    private ContextMenu menuStatus = new ContextMenu();

    // INICIALIZA OS COMPONENTES IMPORTANTES DA JANELA
    public void initialize(){
        lbUser.setText(user.getNome());
        dpDataLancamento.setValue(LocalDate.now());

        if(JDBCLancamentosDAO.getInstance().list(user)!= null) {
            atualiza();
        }
    }

/**----------------------------------- AREA VISUAL -----------------------------------------*/
    // ATUALIZA OS ITENS DA PAGINA
    private void atualiza(){
        double valor=0;
        tfTotal.setText("");
        user.setSaldo(0);
        tfTotal.setText(String.valueOf("R$  "+user.getSaldo()));
        for(Lancamento l : JDBCLancamentosDAO.getInstance().list(user)){
            if(l.getStatus().equals("PENDENTE")){
                valor += l.getValor();
            }
        }
        tfPendencias.setText("R$ "+String.valueOf(valor));
        cbCategoria.getItems().clear();
        cbSubCategoria.getItems().clear();

        if(JDBCCategoriasDAO.getInstance().listCategorias(user) != null){
            cbCategoria.setItems(JDBCCategoriasDAO.getInstance().listCategorias(user));
            cbSearchCategoria.setItems(JDBCCategoriasDAO.getInstance().listCategorias(user));
        }

        populaList();
        GERA_GRAFICO();
    }

    //POPULA O GRAFICO DE PIZZA
    private void GERA_GRAFICO() {
        // GRAFICO DE FREQUENCIA DE GASTOS
        grafoFrequencia.getData().clear();

        for(Lancamento l : JDBCLancamentosDAO.getInstance().listGraficoCategoria(user)){
            grafoFrequencia.getData().addAll(new PieChart.Data(l.getCategoria(), l.getFrequencia()));
        }
        grafoFrequencia.setTitle("FREQUENCIA DE GASTOS");
        grafoFrequencia.setLabelsVisible(true);
        grafoFrequencia.setPrefSize(360, 400);

        // GRAFICO MOSTRANDO OS GASTOS POR CATEGORIA
        grafoPizza.getData().clear();

        for (Lancamento t : JDBCLancamentosDAO.getInstance().listGraficoCategoria(user)){
            grafoPizza.getData().addAll(new PieChart.Data(t.getCategoria(), t.getSoma()));
        }
        grafoPizza.setTitle("GASTOS POR CATEGORIA");
        grafoPizza.setLabelsVisible(true);
        grafoPizza.setPrefSize(360,400);

        // GRAFICO MOSTRANDO OS GASTOS POR SUB-CATEGORIA
        grafoSubCategoria.getData().clear();

        for (Lancamento freq : JDBCLancamentosDAO.getInstance().listGraficoSubCategoria(user)){
            grafoSubCategoria.getData().addAll(new PieChart.Data(freq.getSubcategoria(), freq.getSoma()));
        }
        grafoSubCategoria.setTitle("GASTOS POR SUB-CATEGORIA");
        grafoSubCategoria.setLabelsVisible(true);
        grafoSubCategoria.setPrefSize(360,400);
    }

    // POPULA O TABLEVIEW COMPLETO
    private void populaList(){
        colTrans.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colCateg.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colSubCat.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataLancamento"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tabela.setItems(JDBCLancamentosDAO.getInstance().list(user));
        tabela.setEditable(true);
    }

    @FXML
    void alteraStatus() throws Exception {
        if(tabela.getSelectionModel().getSelectedItem()!=null) {

            Lancamento l = tabela.getSelectionModel().getSelectedItem();

            if(l.getStatus().equals("PAGO")) {
                l.setStatus("PENDENTE");
                JDBCLancamentosDAO.getInstance().update(l);
                atualiza();
            }else{
                l.setStatus("PAGO");
                JDBCLancamentosDAO.getInstance().update(l);
                atualiza();
            }
        }else {
            funcao.alerta();
        }
    }

/**--------------------------------- FIM -------------------------------------------------*/


/**------------------------------ AREA DE LISTAGEM --------------------------------------*/
// POPULA O TABLEVIEW DE PESQUISA
@FXML
public void populaListBy() {
    int regPago = 0, regPend=0, total=0;
    double valor =0;

    LocalDate data = dpDataPesq.getValue();
    if(data!=null) {
        valorCol.setCellValueFactory(new PropertyValueFactory<>("valor"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        subcatCol.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("dataLancamento"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        for (Lancamento l : JDBCLancamentosDAO.getInstance().listBy(data, user)) {
            if (l.getStatus().equals("PAGO")) {
                regPago++;
            } else {
                regPend++;
                valor += l.getValor();
            }
            total++;
        }
        lbRegPaid.setText("REGISTROS PAGOS: " + String.valueOf(regPago));
        lbRegPend.setText("REGISTROS PENDENTES: " + String.valueOf(regPend));
        lbTotalReg.setText("TOTAL DE REGISTROS: " + String.valueOf(total));
        lbDespesasPeriodo.setText("DESPESAS PENDENTES: R$ "+String.valueOf(valor));

        tabelaLis.setItems(JDBCLancamentosDAO.getInstance().listBy(data, user));
        tabelaLis.setEditable(true);
    }else {
        funcao.alerta();
    }
}

    // LISTA LANCAMENTOS ENTRE DETERMINADO PERÍODO
    @FXML
    public void list_between() {
        int regPago = 0, regPend=0, total=0;
        double valor = 0;
        LocalDate dataini = dpDataIni.getValue();
        LocalDate datafim = dpDataFim.getValue();
        if(datafim!=null || dataini!=null) {
            valorCol.setCellValueFactory(new PropertyValueFactory<>("valor"));
            catCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            subcatCol.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
            dataCol.setCellValueFactory(new PropertyValueFactory<>("dataLancamento"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


            for (Lancamento l : JDBCLancamentosDAO.getInstance().listBetween(dataini, datafim, user)) {
                if (l.getStatus().equals("PAGO")) {
                    regPago++;
                } else {
                    regPend++;
                    valor += l.getValor();
                }
                total++;
            }
            lbRegPaid.setText("REGISTROS PAGOS: " + String.valueOf(regPago));
            lbRegPend.setText("REGISTROS PENDENTES: " + String.valueOf(regPend));
            lbTotalReg.setText("TOTAL DE REGISTROS: " + String.valueOf(total));
            lbDespesasPeriodo.setText("DESPESAS PENDENTES: R$ "+String.valueOf(valor));
            tabelaLis.setItems(JDBCLancamentosDAO.getInstance().listBetween(dataini, datafim, user));
            tabelaLis.setEditable(true);
        }else {
            funcao.alerta();
        }
    }

    // LISTA AS ATIVIDADES DO USUÁRIO POR CATEGORIA
    @FXML
    public void list_by_categ(){
        int regPago = 0, regPend=0, total=0;
        double valor = 0;

        try {
            Categoria c = cbSearchCategoria.getSelectionModel().getSelectedItem();

            valorCol.setCellValueFactory(new PropertyValueFactory<>("valor"));
            catCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            subcatCol.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
            dataCol.setCellValueFactory(new PropertyValueFactory<>("dataLancamento"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


            for (Lancamento l : JDBCLancamentosDAO.getInstance().list_categoria(user, c)) {
                if (l.getStatus().equals("PAGO")) {
                    regPago++;
                } else {
                    regPend++;
                    valor += l.getValor();
                }
                total++;
            }
            lbRegPaid.setText("REGISTROS PAGOS: " + String.valueOf(regPago));
            lbRegPend.setText("REGISTROS PENDENTES: " + String.valueOf(regPend));
            lbTotalReg.setText("TOTAL DE REGISTROS: " + String.valueOf(total));
            lbDespesasPeriodo.setText("DESPESAS PENDENTES: R$ "+String.valueOf(valor));
            tabelaLis.setItems(JDBCLancamentosDAO.getInstance().list_categoria(user,c));
            tabelaLis.setEditable(true);
        }catch (Exception e){
            funcao.alerta();
        }
    }
/** -------------------------------------- FIM -------------------------------------- **/

/** ------------------------------ AREA DE FUNCIONALIDADES -------------------------- **/

    // ADICIONA UM LANCAMENTO
    @FXML
    public void add() throws Exception {
            double valor = Double.parseDouble(tfValor.getText());
            Categoria c = cbCategoria.getSelectionModel().getSelectedItem();
            SubCategoria sub = cbSubCategoria.getSelectionModel().getSelectedItem();
            LocalDate data = dpDataLancamento.getValue();
            if (!funcao.addConta(valor, user, c, sub, data)) {
                funcao.alerta();
            }
            atualiza();
    }

    // ADICIONA UMA CATEGORIA
    @FXML
    public void addCategoria() throws Exception {
        String titulo;
        titulo = fxRegistroCateg.getText();
        if(!funcao.addCategoria(titulo,user)){
            funcao.alerta();
        }
        if(user.List()!= null){
            atualiza();
        }
    }

    // ADICIONA UMA SUBCATEGORIA
    @FXML
    public void addSubCategoria() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sub-Categoria");
        dialog.initOwner(myStage.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/cadastro_categorias.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("Deu ruim!!!" +e.getMessage());
            return;
        }
        funcoes_geral controller = fxmlLoader.getController();
        controller.initialize(user);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.addSubCategoria(user);
        }
    }

/** ------------------------ AREA DE LIMPEZA E ATUALIZAÇÃO DE ITENS --------------------- **/

    // LIMPA O HISTÓRICO DE TRANSAÇÕES DO USUÁRIO
    @FXML
    public void limpaTransacao() throws Exception {
        JDBCLancamentosDAO.getInstance().limpa(user);
        JDBCUsuarioDAO.getInstance().update(user, 0);
        user.limpaTransacao();
        atualiza();
    }

    // ATUALIZA O COMBOBOX SEMPRE QUE ADICIONADO UMA NOVA CATEGORIA || SUBCATEGORIA
    @FXML
    public void  atualizaCombo(){
        try{
            if(JDBCCategoriasDAO.getInstance().listSubCategorias(cbCategoria.getSelectionModel().getSelectedItem()) != null){
                cbSubCategoria.setItems(JDBCCategoriasDAO.getInstance().listSubCategorias(cbCategoria.getSelectionModel().getSelectedItem()));
            }
        }catch (Exception ignored){ // IGNORA A EXCEÇÂO, NÃO TENHO PACIENCIA PRA TRATAR AGORA, MAS FUNCIONA (é pra funcionar)
        }
    }

    // ATUALIZA O STATUS DE UM LANCAMENTO BASEADO NO CLICK DO MOUSE DENTRO DA TABLEVIEW
    @FXML
    public void updateStatus(MouseEvent md){
        if(tabela.getSelectionModel().getSelectedItem()!=null) {
            if (md.getButton() == MouseButton.SECONDARY) {
                Menu mi = new Menu("ALTERAR STATUS");


                Lancamento l = tabela.getSelectionModel().getSelectedItem();
                MenuItem payMenu = new MenuItem("PAGAR");
                MenuItem notPayMeny = new MenuItem("PENDENTE");

                payMenu.setOnAction(event -> {
                    l.setStatus("PAGO");
                    try {
                        JDBCLancamentosDAO.getInstance().update(l);
                        atualiza();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                notPayMeny.setOnAction(event -> {
                    l.setStatus("PENDENTE");
                    try {
                        JDBCLancamentosDAO.getInstance().update(l);
                        atualiza();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                mi.getItems().addAll(payMenu,notPayMeny);
                menuStatus.getItems().clear();
                menuStatus.getItems().addAll(mi);
                tabela.setOnContextMenuRequested(event -> menuStatus.show(tabela, event.getScreenX(), event.getScreenY()));
            }else {
                menuStatus.hide();
            }
        }
    }
/** ------------------------------- FIM ----------------------------------------  **/


/** --------------------- FUNÇÕES DO STAGE E LIGAR USUARIO  ------------------- **/

    // SETA UM USUÁRIO ESTÁTICO AO PROGRAMA O ATRIBUINDO ÀS FUNÇÕES BASICAS
    void setUser(Usuario u){
        user=u;
    }

    // INICIA A JANELA PRINCIPAL DO USUÁRIO
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader
                .load(getClass().getResource("../view/home.fxml"));
        stage.setScene(new Scene(root,1080,645));
        stage.setTitle("Controle de Gastos "+user.getNome());
        myStage = stage;
        stage.show();
    }
    // AÇÃO DE LOGOUT, FECHA A JANELA E SETA O USUÁRIO COMO NULL (Prevenção para possíveis conflitos entre Logins)
    @FXML
    void actionExit() throws Exception {
        Main main = new Main();
        main.start(new Stage());
        user = null;
        myStage.close();
    }
}
/** ---------------------------------- FIM DO CODIGO ------------------------------------**/