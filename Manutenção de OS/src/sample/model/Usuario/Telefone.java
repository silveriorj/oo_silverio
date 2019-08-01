package sample.model.Usuario;

public class Telefone {
    private int id_cliente;
    private String numero;


    public Telefone(String numero) {
        this.numero = numero;
    }

    public Telefone(int id_cliente, String numero) {
        this.id_cliente = id_cliente;
        this.numero = numero;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        String str="";

        str += this.getNumero();

        return str;
    }
}
