package sample.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Partida implements Serializable {
    private String time1, time2;
    private String winner;
    private int r1, r2;
    private LocalDate data;

    public Partida(String time1, String time2, LocalDate horario){
        this.time1 = time1;
        this.time2 = time2;
        this.winner = null;
        this.r1 = 0;
        this.r2 = 0;
        this.data = horario;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public LocalDate getData() {
        return data;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public void setData(LocalDate horario) {
        this.data = horario;
    }



    public String toString() {
            return time1 + " [" + r1 + "] x " +  " [" + r2 + "]  " +time2+"\t"+ String.valueOf(data.toString());

    }

}