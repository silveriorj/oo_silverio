package sample.controle;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.modelo.*;
import sample.Main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Callable;

public class user_controller extends Application{

    private static Stage atualStage;


    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private Label lbPontos;
    @FXML
    private GridPane gpDetalhes;
    @FXML
    private Label lbVencedor;
    @FXML
    private ListView<Torneio> ltvwTorneios;
    @FXML
    private ListView<Usuario> ltvwRanking;
    @FXML
    private ListView<Partida> ltvwResultados;
    @FXML
    private ListView<Aposta> ltvwPalpites;
    @FXML
    protected ListView<Partida> ltvwPartidas;
    @FXML
    private VBox vbJogos;
    @FXML
    private Button btAposta;

    private static Usuario atualUser;


    protected Usuario setAtualUser(Usuario u) {
        atualUser = u;

        return atualUser;
    }

    public void initialize() {
        populaListViewTorneio();
        populaListViewResultados();

        try {
            populaApostas(atualUser);
            atualizaPontos(atualUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void logout() throws Exception {
        Main main = new Main();
        main.start(new Stage());
        atualUser = null;
        user_controller.atualStage.close();
    }

    @FXML
    private void populaListViewTorneio(){
        ltvwTorneios.getItems().clear();

        for(Torneio t: GerenciaTorneios.getInstance().lista()){
            ltvwTorneios.getItems().add(t);
        }
        if(GerenciaTorneios.getInstance().lista().isEmpty()) {
            vbJogos.setVisible(false);
        }
    }

        @FXML
        private void populaListViewJogo (Torneio t){
            try {
                ltvwPartidas.getItems().clear();

                for (Partida p : t.getPartidas()) {
                    if(p.getWinner()==null) {
                        ltvwPartidas.getItems().add(p);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        private void populaApostas(Usuario u) throws IOException {
            try {
                ltvwPalpites.getItems().clear();
                for (Aposta aposta : CadastroApostas.getInstance().lista()) {
                    if(aposta.getApostador().getUser().equals(u.getUser())) {
                        ltvwPalpites.getItems().add(aposta);
                    }
                }
                cor();
            } catch (Exception e) {
                e.getMessage();
            }
            try {
                lbPontos.setText("Pontos: " + String.valueOf(u.getPontuacao()));
            } catch (Exception e) {
                e.getMessage();
            }
            populaListViewRanking();
            populaListViewResultados();
            populaListViewTorneio();
            atualizaPontos(u);
        }

        @FXML
    private void populaListViewPalpites() throws IOException {
        populaApostas(atualUser);
    }

    @FXML
    private void populaListViewRanking() {
        try {
            ArrayList<Usuario> list = Cadastro.getInstance().lista();
            Collections.sort(list);
            ltvwRanking.getItems().clear();
            for (Usuario o: list) {
                if(o.getTipo()!= 0) {
                    ltvwRanking.getItems().add(o);
                }
            }
        } catch(Exception e) {
            e.getMessage();
        }
    }

    @FXML
    private void onClickedListViewTorneio() {
        onClickTorneio(atualUser);

    }
    private void onClickTorneio(Usuario u){
        Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
        if(this.atualUser==null){
            System.out.println(u);
        }
        if(t!= null) {
            if (t.getPartidas() != null) {
                populaListViewJogo(t);
            }
            if (!vbJogos.isVisible()) {
                vbJogos.setVisible(true);
            }
        }
    }

    @FXML
    private void atualizaPontos(Usuario u) throws IOException {
        try {
            for (Torneio torneio : GerenciaTorneios.getInstance().lista()) {
                for (Partida jogo : torneio.getPartidas()) {
                    // Verifica Apostas Cadastradas
                    for (Aposta palpite : CadastroApostas.getInstance().lista()) {
                        if(palpite.getApostador() == atualUser) {
                            //verifica se palpite ja esta contabilizado
                            if (!palpite.isAcertou() && !palpite.isCravou()) {
                                if (palpite.getJogo().getData().equals(jogo.getData())
                                        && palpite.getJogo().getTime1().equals(jogo.getTime1())
                                        && palpite.getJogo().getTime2().equals(jogo.getTime2())) {

                                    if (palpite.getVencedor().equals(jogo.getWinner())) {
                                        u.setPontuacao(u.getPontuacao() + 3);
                                        palpite.setAcertou(true);
                                        if (palpite.getGolA().equals(String.valueOf(jogo.getR1()))
                                                &&
                                                palpite.getGolB().equals(String.valueOf(jogo.getR2()))) {
                                            u.setPontuacao(u.getPontuacao() + 7);
                                            palpite.setAcertou(false);
                                            palpite.setCravou(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch(Exception e) {
            e.getMessage();
        }
        Cadastro.getInstance().salvarUsers();
    }

    @FXML
    private void cor() {
        ltvwPalpites.setCellFactory(new Callback<ListView<Aposta>, ListCell<Aposta>>() {
            @Override
            public ListCell<Aposta> call(ListView<Aposta> palpitesListView) {
                ListCell<Aposta> cell = new ListCell<Aposta>() {
                    @Override
                    protected void updateItem(Aposta item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.toString());
                            if (item.isCravou()) {
                                setTextFill(Color.GREEN);
                            } else if(item.isAcertou()){
                                setTextFill(Color.BLUE);
                            }
                            else {
                                setTextFill(Color.RED);
                            }
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void onActionListViewJogo(){

        Partida p = ltvwPartidas.getSelectionModel().getSelectedItem();

        for(Aposta a: CadastroApostas.getInstance().lista()){
            if(a.getJogo().getTime2().equals(p.getTime2()) && a.getJogo().getTime1().equals(p.getTime1())) {
                if (a.getApostador().getUser().equals(atualUser.getUser())) {
                    btAposta.setDisable(true);
                }
                else{
                    btAposta.setDisable(false);
                }
            }
            else{
                btAposta.setDisable(false);
            }
        }
    }

    private void fazPalpite(Usuario u) throws IOException {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Lançar resultado");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../visao/janela_apostas.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        Torneio t = ltvwTorneios.getSelectionModel().getSelectedItem();
        Partida p = ltvwPartidas.getSelectionModel().getSelectedItem();

        if(p != null) {

            janela_apostas controller = fxmlLoader.getController();
            controller.carregarDados(p);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Aposta a  = controller.processResult(p, u);
                System.out.println(a);
                if(a!=null){
                    CadastroApostas.getInstance().addAposta(a);
                    populaApostas(u);
                    populaListViewJogo(t);
                    vbJogos.setVisible(false);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setContentText("Não foi possível apostar, preencha os campos obrigatórios!");
                    alert.showAndWait();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione um jogo!");
            alert.showAndWait();
        }

    }

    @FXML
    private void onActionFazPalpite() throws IOException {
        fazPalpite(atualUser);

    }

    public void deleteAposta(Usuario u) throws IOException {
        Aposta p = ltvwPalpites.getSelectionModel().getSelectedItem();
        if(p!= null) {
            try {
                CadastroApostas.getInstance().delAposta(p);
            }catch(NullPointerException e){
                System.out.println(e.getMessage());
            }
            populaApostas(u);
            ltvwPartidas.getItems().clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione uma Aposta!");
            alert.showAndWait();
        }
        CadastroApostas.getInstance().salvarAposta();
    }

    @FXML
    private void delAposta() throws IOException {
        deleteAposta(atualUser);
    }

    @FXML
    private void populaListViewResultados() {
        try {
            ltvwResultados.getItems().clear();

            for(Torneio torneio : GerenciaTorneios.getInstance().lista()) {
                for (Partida jogo : torneio.getPartidas()) {
                    if (jogo.getWinner() != null) {
                        ltvwResultados.getItems().add(jogo);
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @FXML
    private void mostrarDetalhes() {
        Partida j = ltvwResultados.getSelectionModel().getSelectedItem();

        if(j != null) {
            lbVencedor.setText("Vencedor: " + j.getWinner());
            gpDetalhes.setManaged(true);
            gpDetalhes.setVisible(true);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader
                .load(getClass().getResource("../visao/user_window.fxml"));
        stage.setScene(new Scene(root,600,400));
        stage.setTitle("Copa do Mundo 2018");
        user_controller.atualStage = stage;
        stage.show();
    }
}
