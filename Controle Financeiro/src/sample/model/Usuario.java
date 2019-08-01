package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Usuario implements Serializable{

    private String nome;
    private int id;
    private String user;
    private String senha;
    private double saldo;
    private ObservableList<Lancamento> movimento = FXCollections.observableArrayList();
    private ArrayList<Categoria> categorias = new ArrayList<>();

    public Usuario(String nome, String user, String senha) {
        this.nome = nome;
        this.user = user;
        this.senha = senha;
        this.saldo = 0;
    }
    public Usuario(String nome, String user, String senha, int id, double saldo) {
        this.id = id;
        this.nome = nome;
        this.user = user;
        this.senha = senha;
        this.saldo = saldo;
    }

    public ObservableList<Lancamento> List() {
        return FXCollections.observableArrayList(movimento);
    }

    public void addTransacao(Lancamento l){
        try {
            movimento.add(l);
            this.saldo += l.getValor();
        }catch (NullPointerException e){
            System.out.println("ERRO");
        }
    }
    public void limpaTransacao(){
        movimento.clear();
        this.saldo = 0;
    }
    public void addCategoria(Categoria c){
        try {
            categorias.add(c);
        }catch (NullPointerException e){
            System.out.println("ERRO");
        }
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getUser() {
        return user;
    }

    String getSenha() {
        return senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo += saldo;
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
