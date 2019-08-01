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

public class CadastroApostas {

    private ArrayList<Aposta> apostas;
    private static CadastroApostas instance;
    private static String arqJSon = "apostas.json";


    // Cria a Instancia do Objeto
    public static CadastroApostas getInstance() {
        if (instance == null) {
            instance = new CadastroApostas();
        }
        return instance;
    }

    public void addAposta(Aposta t) throws IOException {
        try {
            apostas.add(t);salvarAposta();
        }catch(NullPointerException e){
            System.out.println("Erro");
        }

    }

    public void delAposta(Aposta t) throws IOException {
        apostas.remove(t);salvarAposta();
    }

    public ArrayList<Aposta> lista() {
        return this.apostas;
    }

    public void salvarAposta() throws IOException {
        Gson torneioGson = new GsonBuilder().create();

        try (FileWriter fw = new FileWriter(arqJSon)) {
            torneioGson.toJson(apostas, fw);
            System.out.println(apostas);
        } catch (IOException e) {
            throw e;
        }
    }

    public void lerApostas() throws IOException {
        Gson torneioGson = new GsonBuilder().create();
        Type tipoLista = new TypeToken<ArrayList<Aposta>>() {}.getType();

        ArrayList<Aposta> listaTemp = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arqJSon))) {
            listaTemp = torneioGson.fromJson(br, tipoLista);
            apostas = listaTemp;

        } catch (IOException e) {
            throw e;
        }
    }
}