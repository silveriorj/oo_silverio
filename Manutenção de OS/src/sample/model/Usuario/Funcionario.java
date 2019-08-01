package sample.model.Usuario;

public class Funcionario extends Usuario{
    private int id;
    private String user;
    private String senha;
    private int tipo;


    Funcionario(String nome, String email, String user, String pass) {
        super(nome, email);
        this.user = user;
        this.senha = pass;
        this.tipo = 0;
    }
    Funcionario(int id, String nome, String email, String user, String pass, int tipo){
        super(nome, email);
        this.id = id;
        this.user = user;
        this.senha = pass;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        String str="";
        str+=getNome()+"\t"+getUser();
        return str;
    }
}
