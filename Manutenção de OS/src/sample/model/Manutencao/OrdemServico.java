package sample.model.Manutencao;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class OrdemServico implements Serializable {

    private SimpleIntegerProperty protocolo;
    private SimpleIntegerProperty id_funcionario;
    private SimpleIntegerProperty id_cliente;
    private SimpleStringProperty nome_cliente;
    private SimpleStringProperty descricao;
    private SimpleStringProperty servico;
    private SimpleBooleanProperty status;
    private Date datain;
    private Date dataout;
    private Date dataprev;

    public OrdemServico(String desc, Date data, int c, String cliente){
        LocalDate ld = LocalDate.now();
        this.descricao = new SimpleStringProperty(desc);
        this.nome_cliente = new SimpleStringProperty(cliente);
        this.datain = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.id_cliente = new SimpleIntegerProperty(c);
        this.dataprev = data;
    }

    public OrdemServico(int protocolo, int id_funcionario, int id_cliente, String descricao, Date datain, Date dataout, Date prazo, Boolean st, String nome, String serv) {
        this.protocolo = new SimpleIntegerProperty(protocolo);
        this.id_funcionario = new SimpleIntegerProperty(id_funcionario);
        this.id_cliente = new SimpleIntegerProperty(id_cliente);
        this.descricao = new SimpleStringProperty(descricao);
        this.datain = datain;
        this.dataout = dataout;
        this.dataprev = prazo;
        this.status = new SimpleBooleanProperty(st);
        this.nome_cliente = new SimpleStringProperty(nome);
        this.servico = new SimpleStringProperty(serv);
    }

    public int getProtocolo() {
        return protocolo.get();
    }

    public int getId_funcionario() {
        return id_funcionario.get();
    }

    public int getId_cliente() {
        return id_cliente.get();
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = new SimpleIntegerProperty(id_cliente);
    }

    public String getNome_cliente() {
        return nome_cliente.get();
    }

    public void setNome_cliente(String nome){
        this.nome_cliente = new SimpleStringProperty(nome);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String desc){
        this.descricao = new SimpleStringProperty(desc);
    }

    public void setStatus(boolean status) {
        this.status = new SimpleBooleanProperty(status);
    }

    public String getServico() {
        return servico.get();
    }

    public void setServico(String servico) {
        this.servico = new SimpleStringProperty(servico);
    }

    public Date getDatain() {
        return datain;
    }

    public Date getDataout() {
        return dataout;
    }

    public void setDataout(Date dataout) {
        this.dataout = dataout;
    }

    public Date getDataprev() {
        return dataprev;
    }

    public String toString() {
        String str = "";
        str += protocolo+"\t\t"+this.descricao;
        return str;
    }
}
