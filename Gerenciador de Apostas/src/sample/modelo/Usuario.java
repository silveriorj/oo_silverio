package sample.modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Usuario implements Serializable, Comparable<Usuario>{

    private String nome;
    private String user;
    private String pass;
    private int pontuacao;
    private int tipo;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    public Usuario(String nome, String user, String pass) {
        this.nome = nome;
        this.user = user;
        this.pass = pass;
        this.pontuacao = 0;
        if(user.contains("admin")&&pass.contains("admin")) {
            this.tipo = 0;
        } else {
            this.tipo = 1;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public int compareTo(Usuario u)
    {
        if (this.pontuacao > u.getPontuacao()) {
            return -1;
        }
        else if (this.pontuacao < u.getPontuacao()) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        String str="";
        str+=nome+"\t"+user+"\tPontos: "+String.valueOf(pontuacao);

        return str;
    }
}
