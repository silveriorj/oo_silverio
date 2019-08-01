package sample.controle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.modelo.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class admin_controller extends Application {

    // JOGADORES
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfSenha;
    @FXML
    private ListView<Usuario> ltvwUser;

    private Usuario item;

    //  PARTIDAS
    @FXML
    private ListView<Torneio> ltvwTorneios;
    @FXML
    private ListView<Partida> ltvwPartida;
    @FXML
    public Label contadorP;
    @FXML
    private ContextMenu contextPartida;
    @FXML
    private ContextMenu contextTorneio;
    @FXML
    private TextField tfTitulo;
    @FXML
    private Label txData;
    @FXML
    private TextField tfResult1;
    @FXML
    private TextField tfResult2;
    @FXML
    private Label lbResult;
    @FXML
    private VBox vbPartidas;


    // CONTROLA O STAGE
    public static Stage myStage;

    public void initialize(){

        // POPULA LISTAS
        populaListTorneios();
        populaListJogador();

        contextPartida = new ContextMenu(); // Cria o menu de mouse na Partida
        contextTorneio = new ContextMenu(); // Cria o menu de mouse no Torneio

        MenuItem deleteJogo = new MenuItem("Delete");
        MenuItem outTorneio = new MenuItem("Delete");
        MenuItem inTorneio = new MenuItem("Adiciona");

        deleteJogo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
                Partida item = ltvwPartida.getSelectionModel().getSelectedItem();
                try {
                    deletePartida(t,item);
                    populaListViewJogo(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        outTorneio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
                try {
                    deleteTorneio(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        inTorneio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
                try {
                    addPartida(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        contextPartida.getItems().addAll(deleteJogo);
        contextTorneio.getItems().addAll(inTorneio,outTorneio);

        ltvwPartida.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Seleciona 1 item por clique
        ltvwTorneios.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);// Seleciona 1 item por clique

        // DETALHES DA PARTIDA SELECIONADA
        ltvwPartida.getSelectionModel().selectedItemProperty().
        addListener(new ChangeListener<Partida>() {
            @Override
            public void changed(ObservableValue<? extends Partida> observableValue,
                                Partida antiga,
                                Partida nova){
                if(nova != null){
                    Partida p = ltvwPartida.getSelectionModel().getSelectedItem();
                    tfTitulo.setText(p.getTime1()+" x "+p.getTime2());
                    if(p.getWinner()!=""){
                        tfResult1.setText(String.valueOf(p.getR1()));
                        tfResult2.setText(String.valueOf(p.getR2()));
                        lbResult.setText(p.getWinner());
                        lbResult.setVisible(true);
                    }
                    else{
                        tfResult1.setText(String.valueOf(p.getR1()));
                        tfResult2.setText(String.valueOf(p.getR2()));
                        lbResult.setVisible(!true);
                    }
                    if(p.getData().isBefore(LocalDate.now())){
                        txData.setText("Data Encerramento: "+Torneio.df.format(p.getData()));

                        if(p.getWinner()!=null){
                            if(p.getWinner().contains("Empate")){
                                lbResult.setTextFill(Color.BLUE);
                            }else{
                                lbResult.setTextFill(Color.GREEN);
                            }
                        }
                    }else{
                        tfResult2.setEditable(true);
                        tfResult1.setEditable(true);
                        txData.setText("Data Encerramento: "+Torneio.df.format(p.getData()));
                    }
                }
            }
        });

        ltvwTorneios.getSelectionModel().selectedItemProperty().
        addListener(new ChangeListener<Torneio>() {
            @Override
            public void changed(ObservableValue<? extends Torneio> observableValue,
                                Torneio antiga,
                                Torneio nova){
                onClickedListViewTorneio();

            }
        });
        ltvwTorneios
                .setCellFactory(new Callback<ListView<Torneio>, ListCell<Torneio>>() {
            @Override
            public ListCell<Torneio> call(ListView<Torneio> torneioListView) {
                ListCell<Torneio> cell = new ListCell<Torneio>() {
                    @Override
                    protected void updateItem(Torneio item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(item.getTorneio());
                                setTextFill(Color.BLACK);
                            }
                        }
                };
                cell.emptyProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasEmpty, Boolean isNowEmpty) {

                        if(isNowEmpty){
                            cell.setContextMenu(null);
                        }else{
                            cell.setContextMenu(contextTorneio);
                        }
                    }});

                return cell;
            }
        });



        // LISTVIEW DAS PARTIDAS
        ltvwPartida
        .setCellFactory(new Callback<ListView<Partida>,
                ListCell<Partida>>() {
            @Override
            public ListCell<Partida> call(ListView<Partida> partidaListView) {
                ListCell<Partida> cell = new ListCell<Partida>(){
                    @Override
                    protected void updateItem(Partida item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(item.getTime1()+" x "+item.getTime2());
                            if(item.getData().isBefore(LocalDate.now())){
                                setTextFill(Color.RED);
                            }
                            else if(item.getData().isEqual(LocalDate.now())){
                                setTextFill(Color.GREEN);
                            }
                            else{
                                setTextFill(Color.BLUE);
                            }
                        }
                    }
                };

                cell.emptyProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasEmpty, Boolean isNowEmpty) {

                        if(isNowEmpty){
                            cell.setContextMenu(null);
                        }else{
                            cell.setContextMenu(contextPartida);
                        }
                    }});
                return cell;
            }
        });
    }

    /*======================================== APLICAÇÕES GERAIS DO ADMINISTRADOR ===========================*/

    // RECEBE O TORNEIO SELECIONADO E RETORNA AS PARTIDAS CADASTRADAS
    @FXML
    private void onClickedListViewTorneio() {
        Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();     // RECEBE O TORNEIO SELECIONADO
        ltvwPartida.getItems().clear();                                    // LIMPA AS PARTIDAS

        if(t!= null) {                                                    // SE TORNEIO FOR INICIALIZADO
            if (t.getPartidas() != null) {                               // SE EXISTIR PARTIDAS CADASTRADAS
                populaListViewJogo(t);                                  // POPULA A LISTA DE PARTIDAS
            }
            if (!vbPartidas.isVisible()) {                               // SE A AREA DE PARTIDAS NÃO FOR VISÍVEL
                vbPartidas.setVisible(true);                            // SETA COMO VISÍVEL
            }
        }
    }
    /*---------------------------------------------------------------------------------------------------------*/
    /*============================================== MANTER TORNEIO ================================================*/

    @FXML
    private void addTorneio() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Adiciona Torneio");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../visao/janela_torneio.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            cadastro_dialog controller = fxmlLoader.getController();
            Torneio t = controller.processResultTorneio();
            if(t != null) {
                GerenciaTorneios.getInstance().addTorneio(t);
                populaListTorneios();
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Cadastro Concluído!");
                dialogoInfo.setContentText("Torneio cadastrado com Sucesso!");
                dialogoInfo.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Não foi possível cadastrar o torneio, não deixe campos vazios!");
                alert.showAndWait();
            }
        }
    }
    // MOSTRA AS PARTIDAS CADASTRADAS NO TORNEIO
    @FXML
    private void populaListViewJogo(Torneio t) {        // RECEBE O TORNEIO SELECIONADO
        ltvwPartida.getItems().clear();                 // LIMPA O LISTVIEW
        for (Partida jogo : t.getPartidas()) {          //
            ltvwPartida.getItems().add(jogo);
        }
        if(t.getPartidas().isEmpty()) {
            vbPartidas.setVisible(false);
        }
    }

    @FXML
    private void onClickedListUser(){
        Usuario c = ltvwUser.getSelectionModel().getSelectedItem();

        tfNome.setText(c.getNome());
        tfUsuario.setText(c.getUser());
        tfSenha.setText(c.getPass());
    }


    private void populaListJogador(){
        // LISTA DE USUARIOS
        ltvwUser.getItems().clear();
        for(Usuario u: Cadastro.getInstance().lista()){
            ltvwUser.getItems().add(u);
        }
    }

    @FXML
    private void populaListTorneios(){
        // LISTA DE TORNEIOS
        ltvwTorneios.getItems().clear();
        for(Torneio lp: GerenciaTorneios.getInstance().lista()){
            ltvwTorneios.getItems().add(lp);
        }
        if(ltvwTorneios.getItems().isEmpty()){
            vbPartidas.setVisible(false);
        }else{vbPartidas.setVisible(true);}
    }

    private void populaListJogos(Torneio t){
        // LISTA DE PARTIDAS
        ltvwPartida.getItems().clear();

        for (Partida jogo : t.getPartidas()) {
            ltvwPartida.getItems().add(jogo);
        }

        if(t.getPartidas().isEmpty()) {
            vbPartidas.setVisible(false);
        }
    }


    @FXML
    public void actionDeleteTorneio() throws IOException {
        Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
        deleteTorneio(t);
    }
    private void deleteTorneio(Torneio t) throws IOException {
        if(t != null){
            GerenciaTorneios.getInstance().delTorneio(t);
        }
        populaListTorneios();
    }

    /*======================================== MANTER PARTIDAS ===========================================*/

    // ADICIONA UMA NOVA PARTIDA
    @FXML
    public void actionAdicionaPartida() throws IOException {
        Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
        addPartida(t);
    }

    // ABRE UM DIALOG PANE PARA ADICIONAR UMA NOVA PARTIDA
    public void addPartida(Torneio t) throws IOException {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Adiciona Partida");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../visao/cadastroPartida_dialog.fxml"));
        try{dialog.getDialogPane().setContent(fxmlLoader.load());}
        catch (IOException e){System.out.println(e.getMessage()); return;}

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            cadastro_dialog controller = fxmlLoader.getController();

            Partida p = controller.processResultPartida(t);

            if(p != null) {
                t.addPartida(p);
                GerenciaTorneios.getInstance().salvarTorneios();
                populaListViewJogo(t);
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Cadastro Concluído!");
                dialogoInfo.setContentText("Partida cadastrada com Sucesso!");
                dialogoInfo.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Não foi possível cadastrar a partida, não deixe campos vazios!");
                alert.showAndWait();
            }
        }
    }

    // DELETA A PARTIDA
    public void deletePartida(Torneio t, Partida p) throws IOException {
        t.removePartida(p);
        GerenciaTorneios.getInstance().salvarTorneios();
    }

    // DELETA A PARTIDA SELECIONADA
    @FXML
    public void actionDeletePartida(){
        Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
        Partida p = ltvwPartida.getSelectionModel().getSelectedItem();

        if(t!= null){
            t.removePartida(p);
            populaListViewJogo(t);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione um jogo!");
            alert.showAndWait();
        }
    }

    // ATUALIZA O RESULTADO DA PARTIDA SELECIONADA
    @FXML
    private void setResult() throws IOException {
        Partida p = ltvwPartida.getSelectionModel().getSelectedItem();

        if(tfResult1 != null && tfResult2 != null){
            p.setR1(Integer.valueOf(tfResult1.getText()));
            p.setR2(Integer.valueOf(tfResult2.getText()));
            if(p.getR1() > p.getR2()){
                p.setWinner(p.getTime1());
            }
            else if(p.getR1() < p.getR2()){
                p.setWinner(p.getTime2());
            }
            else{
                p.setWinner("Empate");
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Preencha um Resultado!");
            alert.showAndWait();
        }
        GerenciaTorneios.getInstance().salvarTorneios();
    }
    /*--------------------------------------------- FIM --------------------------------------------- */


    /*====================================== MANTER USUÁRIOS ========================================= */

    // ABRE UM DIALOG PANE PARA ADICIONAR UM NOVO JOGADOR
    @FXML
    private void actionAdicionaJogador() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Adicionar Jogador");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../visao/cadastro_dialog.fxml"));
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
            cadastro_dialog controller = fxmlLoader.getController();
            Usuario t = controller.processResult();
            if(t != null) {
                Cadastro.getInstance().addUser(t);
                populaListJogador();
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Cadastro Concluído!");
                dialogoInfo.setContentText("Usuário cadastrado com Sucesso!");
                dialogoInfo.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Não foi possível cadastrar usuário, não deixe campos vazios!");
                alert.showAndWait();
            }
        }
    }

    // DELETA O JOGADOR SELECIONADO SE NÃO FOR IGUAL A ADMINISTRADOR
    @FXML
    public void actionDeleteJogador() throws IOException {
        Usuario u = ltvwUser.getSelectionModel().getSelectedItem();
        deleteUser(u);
        populaListJogador();
    }
    private void deleteUser(Usuario u) throws IOException {
        if(!Cadastro.getInstance().removeUser(u)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Impossível remover Administrador!!");
            alert.showAndWait();
        }
    }

    // MOSTRA AS INFORMAÇÕES DO USUÁRIO SELECIONADO
    @FXML
    private void actionShowUserDetails(){
        Usuario u = ltvwUser.getSelectionModel().getSelectedItem();

        tfNome.setText(u.getNome());
        tfUsuario.setText(u.getUser());
        tfSenha.setText(u.getPass());
    }

    // ATUALIZA AS INFORMAÇÕES DE USUÁRIO
    @FXML
    private void actionUpdateUsers() throws IOException {
        Usuario u = ltvwUser.getSelectionModel().getSelectedItem();

        u.setNome(tfNome.getText());
        u.setUser(tfUsuario.getText());
        u.setPass(tfSenha.getText());
        Cadastro.getInstance().salvarUsers();
        populaListJogador();
    }
    /* ------------------------------------------ FIM --------------------------------------------- */


    // LIMPA O RESULTADO DO SHOW DETAILS
    private void limpa(){
        //PARTIDAS
        tfTitulo.setText("");
        tfResult1.setText("");
        tfResult2.setText("");
        txData.setText("Data Encerramento: ");
        // USUÁRIO
        tfNome.setText("");
        tfUsuario.setText("");
        tfSenha.setText("");
    }


    // FECHA A JANELA E RETORNA A JANELA DE LOGIN (LOGOUT)
    @FXML
    private void actionExit() throws Exception {
        Main main = new Main();                       // Cria uma nova janela de Inicio declarada na Main
        main.start(new Stage());                     // Abre a nova Janela
        admin_controller.myStage.close();           // FECHA A JANELA ATUAL
    }

    // SETA O USUÁRIO ATUAL COMO ADMINISTRADOR
    public Usuario setUser(Usuario item) {
        String tipo = "";
        this.item = item;
        tipo = "Administrador";
        return item;
    }

    // START THE SCENE
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader
                .load(getClass().getResource("../visao/admin_window.fxml"));
        stage.setScene(new Scene(root,720,450));
        stage.setTitle("Copa do Mundo 2018");
        admin_controller.myStage = stage;
        stage.show();
    }
}
