package sample.modelo;

import java.io.*;
import java.util.ArrayList;

public class Cadastro {
    private ArrayList<Usuario> usuarios;
    private static Cadastro instance;

    private static String NOME_ARQ_BIN= "user.bin";


    public static Cadastro getInstance(){
        if(instance==null){
            instance = new Cadastro();
        }
        return instance;
    }


    private Cadastro(){
        usuarios = new ArrayList<>();
    }

    public void addUser(Usuario t) throws IOException {
        usuarios.add(t);
        salvarUsers();
    }

    public boolean validaLogin(String user, String pass){
        for(Usuario t: usuarios){
            if(t.getUser().equals(user) && t.getPass().equals(pass)){
                    return true;
                }
            }
        return false;
    }

    public Usuario login(String user, String pass){
        for(Usuario t: usuarios){
            if(t.getUser().equals(user)){
                if(t.getPass().equals(pass)) {
                    return t;
                }
            }
        }
        return null;
    }

    public boolean removeUser(Usuario u) throws IOException {
        if(u.getTipo()==1){
            usuarios.remove(u);
            salvarUsers();
            return true;
        }
        return false;
    }

    public ArrayList<Usuario> lista() {
        return usuarios;
    }



    public void salvarUsers() throws IOException{

        try(FileOutputStream fs = new FileOutputStream(new File(NOME_ARQ_BIN));
            ObjectOutputStream os = new ObjectOutputStream(fs)){

            for(Usuario t: usuarios){
                os.writeObject(t);
            }
        }
    }

    public void lerUsers() throws IOException{

        try(FileInputStream fi = new FileInputStream(new File(NOME_ARQ_BIN));
            ObjectInputStream oi = new ObjectInputStream(fi)){

            usuarios.clear();

            try {
                while(true) {
                    Usuario t = (Usuario) oi.readObject();
                    usuarios.add(t);
                }
            }catch (ClassNotFoundException e){
                System.out.println("Erro na Leitura de Usuários!!!"+e.getMessage());
            }catch (EOFException e){
            }
        }
    }
}