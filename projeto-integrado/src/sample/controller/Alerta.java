package sample.controller;

import javafx.scene.control.Alert;

class Alerta {

    static void user_not_found(){
        Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
        dialogoAviso.setTitle("Usuário não encontrado!");
        dialogoAviso.setHeaderText("Usuário ou Senha Inválida!");
        dialogoAviso.setContentText("Por favor, digite um Usuário e Senha Válido!");
        dialogoAviso.showAndWait();
    }

    static void user_not_filled(){
        Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
        dialogoAviso.setTitle("Não Encontrado!");
        dialogoAviso.setHeaderText("CAMPOS NÃO PREENCHIDOS!");
        dialogoAviso.setContentText("Por favor, preencha todos os campos de Login!");
        dialogoAviso.showAndWait();
    }


    // TEMPLATES
    static void informacao(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Importante");
        info.setContentText("Alerta de Informação configurado!");
        info.showAndWait();
    }

    static void erro(){
        Alert erro = new Alert(Alert.AlertType.ERROR);
        erro.setTitle("Erro");
        erro.setContentText("Alerta de Erro configurado!");
        erro.showAndWait();
    }

    static void aviso(){
        Alert aviso = new Alert(Alert.AlertType.WARNING);
        aviso.setTitle("Aviso!");
        aviso.setContentText("Alerta de Aviso configurado!");
        aviso.showAndWait();
    }

    static void confirmacao(){
        Alert confirma = new Alert(Alert.AlertType.CONFIRMATION);
        confirma.setTitle("Confirmação!");
        confirma.setContentText("Alerta de Confirmação configurado!");
        confirma.showAndWait();
    }
}
