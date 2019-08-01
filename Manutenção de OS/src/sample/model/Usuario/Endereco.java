package sample.model.Usuario;

public class Endereco {
    private String rua,  bairro,  cidade, numero, complemento;

    public Endereco(String rua, String bairro, String cidade, String comp, String numero){
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        if(comp!=null){
            this.complemento = comp;
        }
    }


    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getComplemento() {
        return complemento;
    }
}
