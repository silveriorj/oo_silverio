package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.controle.login;
import sample.modelo.*;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader
                .load(getClass().getResource("visao/loginWindow.fxml"));
        stage.getIcons().add(new Image("file:Imagens/copa_icon.png"));
        stage.setScene(new Scene(root,450,250));
        stage.setTitle("Gerenciador de Apostas");
        login.myStage = stage;
        stage.show();

    }


    @Override
    public void init() throws Exception{

        try {
            Cadastro.getInstance().lerUsers();
            GerenciaTorneios.getInstance().lerTorneios();
            CadastroApostas.getInstance().lerApostas();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception{

    }

}
