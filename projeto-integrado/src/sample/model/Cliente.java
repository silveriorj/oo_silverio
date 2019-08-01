package sample.model;

import java.util.ArrayList;

public class Cliente extends Usuario{
    private int garra;
    private ArrayList<Telefone> telefone = new ArrayList<>();
    private Endereco endereco;

    public Cliente(String nome, String email, Endereco endereco) {
        super(nome, email);
        this.endereco = endereco;
    }

    public Cliente(String nome, String email, int garra, Endereco e) {
        super(nome, email);
        this.garra = garra;
        this.endereco = e;
    }

    public int getGarra() {
        return garra;
    }


    public ArrayList<Telefone> getTelefone() {
        return telefone;
    }


    public Endereco getEndereco() {
        return endereco;
    }


    public String toString(){
        String str ="";
        str += getGarra()+"\t"+getNome();
        return str;
    }
}
