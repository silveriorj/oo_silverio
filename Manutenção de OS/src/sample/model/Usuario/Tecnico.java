package sample.model.Usuario;

public class Tecnico extends Funcionario {
    public Tecnico(String nome, String email, String user, String pass) {
        super(nome, email, user, pass);
        setTipo(2);
    }

    Tecnico(int id, String nome, String email, String user, String pass, int tipo) {
        super(id, nome, email, user, pass, tipo);
        setTipo(2);
    }
}
