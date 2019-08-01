package sample.modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class GerenciaTorneios extends ArrayList<Torneio> {

    private ArrayList<Torneio> torneios;
    private static GerenciaTorneios instance;
    private static String arqJSon = "torneios.json";


    // Cria a Instancia do Objeto
    public static GerenciaTorneios getInstance() {
        if (instance == null) {
            instance = new GerenciaTorneios();
        }
        return instance;
    }

    public void addTorneio(Torneio t) throws IOException {
        torneios.add(t);salvarTorneios();
    }

    public void delTorneio(Torneio t) throws IOException {
        torneios.remove(t);salvarTorneios();
    }

    public ArrayList<Torneio> lista() {
        return this.torneios;
    }

    public void salvarTorneios() throws IOException {
        Gson torneioGson = new GsonBuilder().create();

        try (FileWriter fw = new FileWriter(arqJSon)) {
            torneioGson.toJson(torneios, fw);
            System.out.println(torneios);
        } catch (IOException e) {
            throw e;
        }
    }

    public void lerTorneios() throws IOException {
        Gson torneioGson = new GsonBuilder().create();
        Type tipoLista = new TypeToken<ArrayList<Torneio>>() {}.getType();

        ArrayList<Torneio> listaTemp = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arqJSon))) {
            listaTemp = torneioGson.fromJson(br, tipoLista);
            torneios = listaTemp;

        } catch (IOException e) {
            throw e;
        }
    }
}
