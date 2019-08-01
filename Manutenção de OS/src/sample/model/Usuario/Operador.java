package sample.model.Usuario;

public class Operador extends Funcionario {
    public Operador(String nome, String email, String user, String pass) {
        super(nome, email, user, pass);
        setTipo(1);
    }

    Operador(int id, String nome, String email, String user, String pass, int tipo) {
        super(id, nome, email, user, pass, tipo);
        setTipo(1);
    }
}
