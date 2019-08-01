package sample.modelo;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Aposta implements Serializable{
    private Partida jogo;
    private Usuario apostador;


    private String golA;
    private String golB;
    private String vencedor;

    private boolean acertou;
    private boolean cravou;

    public Aposta(String g1, String g2, String win, Partida p, Usuario u) {
        this.golA = g1;
        this.golB = g2;
        this.vencedor = win;
        this.jogo = p;
        this.apostador = u;
        this.acertou = false;
        this.cravou = false;
    }

    public Usuario getApostador() {
        return apostador;
    }

    public void setApostador(Usuario apostador) {
        this.apostador = apostador;
    }

    public Partida getJogo() {
        return jogo;
    }

    public void setJogo(Partida jogo) {
        this.jogo = jogo;
    }

    public String getGolA() {
        return golA;
    }

    public void setGolA(String golA) {
        this.golA = golA;
    }

    public String getGolB() {
        return golB;
    }

    public void setGolB(String golB) {
        this.golB = golB;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public boolean isCravou() {
        return cravou;
    }

    public void setCravou(boolean cravou) {
        this.cravou = cravou;
    }

    public String toString() {
        return apostador.getUser()+" = "+jogo.getTime1() + " [" + golA + "] x [" + golB + "] " + jogo.getTime2() + "]";
    }
}
