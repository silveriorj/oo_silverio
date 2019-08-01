package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lancamento {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty id_categoria;
    private SimpleIntegerProperty id_subcategoria;
    private SimpleStringProperty categoria;
    private SimpleStringProperty subcategoria;
    private SimpleDoubleProperty valor;
    private SimpleStringProperty dataLancamento;
    private SimpleStringProperty status;

    private int frequencia;
    private double soma;



    Lancamento(double val, int freq, String categoria, String subcategoria){
        this.soma = val;
        this.frequencia = freq;
        this.categoria = new SimpleStringProperty(categoria);
        this.subcategoria = new SimpleStringProperty(subcategoria);
    }

    public Lancamento(double val, int cat, int subCat, String data, Boolean status){
        this.valor = new SimpleDoubleProperty(val);
        this.id_categoria = new SimpleIntegerProperty(cat);
        this.id_subcategoria = new SimpleIntegerProperty(subCat);
        if(status) {
            this.status = new SimpleStringProperty("PAGO");
        }else{this.status = new SimpleStringProperty("PENDENTE");}
        this.dataLancamento = new SimpleStringProperty(data);
    }

    Lancamento(int id, double val, int cat, int subCat, String categoria, String subcategoria, boolean stat, String data){
        this.id = new SimpleIntegerProperty(id);
        this.id_categoria = new SimpleIntegerProperty(cat);
        this.id_subcategoria = new SimpleIntegerProperty(subCat);
        this.categoria = new SimpleStringProperty(categoria);
        this.subcategoria = new SimpleStringProperty(subcategoria);
        this.valor = new SimpleDoubleProperty(val);
        if(stat){this.status = new SimpleStringProperty("PAGO");
        }else{this.status = new SimpleStringProperty("PENDENTE");}
        this.dataLancamento = new SimpleStringProperty(data);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId_categoria() {
        return id_categoria.get();
    }

    public SimpleIntegerProperty id_categoriaProperty() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria.set(id_categoria);
    }

    public int getId_subcategoria() {
        return id_subcategoria.get();
    }

    public SimpleIntegerProperty id_subcategoriaProperty() {
        return id_subcategoria;
    }

    public void setId_subcategoria(int id_subcategoria) {
        this.id_subcategoria.set(id_subcategoria);
    }

    public String getCategoria() {
        return categoria.get();
    }

    public SimpleStringProperty categoriaProperty() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = new SimpleStringProperty(categoria);
    }

    public String getSubcategoria() {
        return subcategoria.get();
    }

    public SimpleStringProperty subcategoriaProperty() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = new SimpleStringProperty(subcategoria);
    }

    public double getValor() {
        return valor.get();
    }

    public SimpleDoubleProperty valorProperty() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor.set(valor);
    }

    public String getDataLancamento() {
        return dataLancamento.get();
    }

    public SimpleStringProperty dataLancamentoProperty() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento.set(dataLancamento);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public double getSoma() {
        return soma;
    }

    public void setSoma(double soma) {
        this.soma = soma;
    }

    @Override
    public String toString() {
        return ""+this.status+"";
    }
}
