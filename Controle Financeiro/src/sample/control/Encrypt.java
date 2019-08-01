package sample.control;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//  ENCRIPTA A SENHA BASEADA EM SHA-256
//  NÃO É POSSIVEL RECUPERAR A SENHA!!!!

public class Encrypt {
    public String encrypt(String pwd) throws NoSuchAlgorithmException {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(pwd.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for(byte b: messageDigest){
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }
}
