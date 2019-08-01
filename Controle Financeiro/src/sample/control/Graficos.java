package sample.control;

import javafx.scene.chart.PieChart;
import sample.model.JDBCLancamentosDAO;
import sample.model.Lancamento;
import sample.model.Usuario;

class Graficos {
    private PieChart frequenciaGastos = new PieChart();
    private PieChart frequenciaCategorias;
    private PieChart frequenciaSubCategorias;

    PieChart frequencia(Usuario usuario){
        for(Lancamento l : JDBCLancamentosDAO.getInstance().listGraficoCategoria(usuario)){
            frequenciaGastos.getData().addAll(new PieChart.Data(l.getCategoria(), l.getFrequencia()));
        }
        System.out.println("OK");
        return frequenciaGastos;
    }
}
