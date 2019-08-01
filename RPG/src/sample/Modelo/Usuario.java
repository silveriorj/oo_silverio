package sample.Modelo;

public class Usuario {
    private String usuario;
    private String senha;
    private String email;

    public Usuario(String usuario, String senha, String email) {
        this.usuario = usuario;
        this.senha = senha;
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }
}
