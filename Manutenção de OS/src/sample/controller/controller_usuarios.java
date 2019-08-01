package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import sample.Main;
import sample.model.Usuario.Cliente;
import sample.model.Usuario.JDBCClienteDAO;

import java.io.IOException;
import java.util.Optional;

class controller_usuarios {

    static void ADICIONA_CLIENTE() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Cliente");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("view/cadastro/cadastro_cliente.fxml"));
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
    }

    @FXML
    static void DELETE_CLIENTE(Cliente c) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmação!");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(controller_os.class.getResource("../view/dialogs/confirmacao_exclusao.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            JDBCClienteDAO.getInstance().delete(c);
        }
    }

    static void ADICIONA_FUNCIONARIO() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Usuário");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("view/cadastro/cadastro_funcionarios_view.fxml"));
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
    }
}
