package sample.model;

import java.util.ArrayList;

public class Categoria {

    private String titulo;
    private int id;
    private ArrayList<SubCategoria> subCategorias = new ArrayList<>();

    public Categoria(String titulo){
        this.titulo = titulo;
    }

    public Categoria(String titulo, int id){
        this.titulo = titulo;
        this.id  = id;
    }

    public void addSubCategorias(SubCategoria sub){
        this.subCategorias.add(sub);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<SubCategoria> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(ArrayList<SubCategoria> subCategorias) {
        this.subCategorias = subCategorias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
