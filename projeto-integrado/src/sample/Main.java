package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.controller.login_controller;
import sample.controller.operador_controller;
import sample.controller.tecnico_controller;
import sample.model.Funcionario;
import sample.model.Usuario;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;

    private static Scene loginStage;
    private static Usuario user;


    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        Parent fxLogin = FXMLLoader.load(getClass().getResource("view/login_view.fxml"));
        primaryStage.setTitle("Autenticação");
        loginStage = new Scene(fxLogin, 328, 168);
        primaryStage.setScene(loginStage);
        stage.getIcons().add(new Image(login_controller.class.getResourceAsStream("../style/img/verificacao.png")));


        primaryStage.show();
    }

    public static void changeScreen(String scr) throws IOException {
        switch (scr){
            case "login":
                stage.close();
                stage.getIcons().clear();
                Parent fxLogin = FXMLLoader.load(Main.class.getResource("view/login_view.fxml"));
                stage.setTitle("Autenticação");
                loginStage = new Scene(fxLogin, 328, 168);
                stage.setScene(loginStage);
                stage.getIcons().add(new Image(login_controller.class.getResourceAsStream("../style/img/verificacao.png")));
                stage.show();

                break;

            case "ADMINISTRADOR":
                stage.close();
                stage.getIcons().clear();
                Parent fxAdmin = FXMLLoader.load(Main.class.getResource("view/administrador_view.fxml"));
                stage.setTitle("Autenticação");
                loginStage = new Scene(fxAdmin, 981, 560);
                stage.setScene(loginStage);
                stage.getIcons().add(new Image(login_controller.class.getResourceAsStream("../style/img/verificacao.png")));
                stage.show();
                break;

            case "OPERADOR":
                stage.close();
                stage.getIcons().clear();
                Parent fxOperador = FXMLLoader.load(Main.class.getResource("view/operador_view.fxml"));
                stage.setTitle("Autenticação");
                loginStage = new Scene(fxOperador, 980, 560);
                stage.setScene(loginStage);
                stage.getIcons().add(new Image(operador_controller.class.getResourceAsStream("../style/img/verificacao.png")));
                stage.show();
                break;

            case "TECNICO":
                stage.close();
                stage.getIcons().clear();
                Parent fxTecnico = FXMLLoader.load(Main.class.getResource("view/login_view.fxml"));
                stage.setTitle("Autenticação");
                loginStage = new Scene(fxTecnico, 328,168);
                stage.setScene(loginStage);
                stage.getIcons().add(new Image(tecnico_controller.class.getResourceAsStream("../style/img/verificacao.png")));
                stage.show();
                break;
        }
    }

    public static void setUser(Funcionario usuario){
        user = usuario;
    }
    public static Usuario getUser() {
        return user;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
