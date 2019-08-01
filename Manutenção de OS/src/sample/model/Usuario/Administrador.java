package sample.model.Usuario;

public class Administrador extends Funcionario {
    public Administrador(String nome, String email, String user, String pass) {
        super(nome, email, user, pass);
        setTipo(0);
    }

    public Administrador(int id, String nome, String email, String user, String pass, int tipo) {
        super(id, nome, email, user, pass, tipo);
        setTipo(0);
    }
}
