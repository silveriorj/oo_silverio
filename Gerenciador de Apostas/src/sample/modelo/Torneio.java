package sample.modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Torneio implements Serializable{
    private String Torneio;
    private ArrayList<Partida> partidas;
    public static DateTimeFormatter df=DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Torneio(String nomeTorneio){
        partidas = new ArrayList<>();
        this.Torneio = nomeTorneio;
    }

    private static String NOME_ARQ_JSON="partidas.json";

    public ArrayList<Partida> getPartidas(){
        return this.partidas;
    }

    public void setNomeTorneio(String nomeTorneio) {
        this.Torneio = nomeTorneio;
    }

    public void addPartida(Partida jogo) { partidas.add(jogo); }

    public void removePartida(Partida jogo) {
        partidas.remove(jogo);
    }

    public void setTorneio(String name) {
        this.Torneio = name;
    }

    public String getTorneio() {
        return Torneio;
    }
    public String toString(){
        return Torneio;
    }
}
