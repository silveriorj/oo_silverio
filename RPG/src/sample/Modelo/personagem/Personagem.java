package sample.Modelo.personagem;

import sample.Modelo.Usuario;

public abstract class Personagem extends Usuario {

    private String nickname;
    private int level;
    private double experience;

    public Personagem(String usuario, String senha, String email, String nick) {
        super(usuario, senha, email);
        this.nickname = nick;
        this.level = 0;
        this.experience = 0;
        initial_stats();
    }

    private void initial_stats(){
        this.STR = 10;
        this.CONS = 10;
        this.DEX = 10;
        this.AGI = 10;

        this.INT = 10;
        this.WILL = 10;
        this.PER = 10;
        this.CAR = 10;
    }

    // Atributos FISICOS do Personagem

    private double STR;   // FORÇA
    private double CONS;  // CONSTITUIÇÃO
    private double DEX;   // DEXTREZA
    private double AGI;   // AGILIDADE

    // Atributos MENTAIS do Personagem

    private double INT;   // INTELIGENCIA
    private double WILL;  // VONTADE
    private double PER;   // PERCEPÇÃO
    private double CAR;   // CARISMA


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getSTR() {
        return STR;
    }

    public void setSTR(double STR) {
        this.STR = STR;
    }

    public double getCONS() {
        return CONS;
    }

    public void setCONS(double CONS) {
        this.CONS = CONS;
    }

    public double getDEX() {
        return DEX;
    }

    public void setDEX(double DEX) {
        this.DEX = DEX;
    }

    public double getAGI() {
        return AGI;
    }

    public void setAGI(double AGI) {
        this.AGI = AGI;
    }

    public double getINT() {
        return INT;
    }

    public void setINT(double INT) {
        this.INT = INT;
    }

    public double getWILL() {
        return WILL;
    }

    public void setWILL(double WILL) {
        this.WILL = WILL;
    }

    public double getPER() {
        return PER;
    }

    public void setPER(double PER) {
        this.PER = PER;
    }

    public double getCAR() {
        return CAR;
    }

    public void setCAR(double CAR) {
        this.CAR = CAR;
    }
}
