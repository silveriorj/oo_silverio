package sample.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;


public class OrdemServico implements Serializable {

    private int protocolo;
    private int id_funcionario;
    private int id_cliente;
    private String descricao;
    private Boolean status;
    private Date datain;
    private Date dataout;
    private Date dataprev;

    public OrdemServico(String desc, LocalDate data, int c){
        this.descricao = desc;
        this.datain = new Date();
        Date.from(Instant.now());
        this.id_cliente = c;
        this.dataprev = Date.from(Instant.from(data));
    }

    OrdemServico(int protocolo, int id_funcionario, int id_cliente, String descricao, Date datain, Date dataout, Date prazo, Boolean st) {
        this.protocolo = protocolo;
        this.id_funcionario = id_funcionario;
        this.id_cliente = id_cliente;
        this.descricao = descricao;
        this.datain = datain;
        this.dataout = dataout;
        this.dataprev = prazo;
        this.status = st;
    }

    public int getFuncionario() {
        return id_funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.id_funcionario = funcionario.getId();
    }

    public int getProtocolo() {
        return protocolo;
    }

    String getDescricao() {
        return descricao;
    }

    Date getDatain() {
        return datain;
    }

    public Date getDataout() {
        return dataout;
    }

    public Date getDataprev() {
        return dataprev;
    }

    public int getCliente() {
        return id_cliente;
    }

    public void setCliente(Cliente cliente) {
        this.id_cliente = cliente.getGarra();
    }

    public Boolean getStatus() {
        return status;
    }

    public String toString() {
        String str = "";
        str += protocolo+"\t\t"+this.descricao;
        return str;
    }
}
