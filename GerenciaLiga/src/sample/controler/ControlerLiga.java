package sample.controler;

import sample.model.FabricaConexao;
import sample.model.Jogador;
import sample.model.Time;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControlerLiga {

    private ArrayList<Jogador> jogadores;
    private ArrayList<Time> times;

    public static ControlerLiga instance;

    private ControlerLiga(){
        jogadores= new ArrayList();
        times=new ArrayList<>();
    }

    public static ControlerLiga getInstance(){
        if(instance==null){
            instance = new ControlerLiga();
        }
        return instance;
    }

    public void addJogador(Jogador j) throws SQLException{
        //this.jogadores.add(j);




    }

    public void addTime(Time t) throws SQLException{
        String sql = "insert into times(nome,cidade,ano_fundacao) "+
                     "values(?,?,?)";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement stmt = c.prepareStatement(sql);

        stmt.setString(1,t.getNome());
        stmt.setString(2,t.getCidade());
        stmt.setInt(3,t.getAnoFundacao());

        stmt.execute();

        stmt.close();
        c.close();


    }

    public Jogador buscaJogador(String nome){
        for(Jogador j:jogadores){
            if(j.getNome().equals(nome)){
                return j;
            }
        }
        return null;
    }

    public List<Time> listaTimes(){

        this.times.clear();
        try{
            Connection c = FabricaConexao.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from times");

            while (rs.next()){
                String nome = rs.getString("nome");
                String cidade = rs.getString("cidade");
                int anoFundacao = rs.getInt("ano_fundacao");

                Time t = new Time();
                t.setNome(nome);
                t.setCidade(cidade);
                t.setAnoFundacao(anoFundacao);

                times.add(t);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return times;
    }

    public List<Jogador> listaJogadores(){
        this.jogadores.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select * from jogadores");

            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");

                double peso = rs.getDouble("peso");
                double altura = rs.getDouble("altura");

                Jogador j = new Jogador();
                j.setNome(nome);
                j.setPeso(peso);
                j.setAltura(altura);

                this.jogadores.add(j);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return this.jogadores;
    }


}
