package sample.model;

public interface LancamentoDAO {
    void movimentacao(Usuario u, Lancamento l, Categoria c, SubCategoria d) throws Exception;
    void limpa(Usuario u) throws Exception;
    void update(Lancamento l) throws Exception;
}
