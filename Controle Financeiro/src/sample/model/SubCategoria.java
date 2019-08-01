package sample.model;

public class SubCategoria{
    private String titulo;
    private int categoria;
    private int id;

    public SubCategoria(String sub, int c){
        this.titulo = sub;
        this.categoria = c;
    }

    SubCategoria(String s, int id_c, int id){
        this.titulo = s;
        this.categoria = id_c;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
