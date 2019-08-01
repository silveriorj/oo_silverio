package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main{
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String senha="admin";
        String senha2 = "a";

        String teste = Base64.getEncoder().encodeToString(senha.getBytes());
        byte[] decod = Base64.getDecoder().decode(teste);
        String retorno = new String(decod);

        // SENHA SHA-256
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for(byte b: messageDigest){
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhaF = hexString.toString();

        // SENHA COMPARAÇÃO
        MessageDigest algorithm2 = MessageDigest.getInstance("SHA-256");
        byte messageDigest2[] = algorithm2.digest(senha2.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString2 = new StringBuilder();
        for(byte b: messageDigest2){
            hexString2.append(String.format("%02X", 0xFF & b));
        }
        String senhaG = hexString2.toString();


        // OUTPUT
        System.out.println("Base64: "+teste);
        System.out.println("Base64: "+retorno);
        System.out.println("SHA-256: "+senhaG);
        System.out.println("SHA-256: "+senhaF);

    }
}
