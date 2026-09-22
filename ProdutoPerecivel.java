public class ProdutoPerecivel extends Product {

    private int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        this.diasParaVencer = diasParaVencer;
    }

    @Override
    public double calcularValorTotal() {
        double valor = getPreco() * getQuantidade();
        if (diasParaVencer <= 3) {
            valor *= 0.8; // 20% de desconto automático
        }
        return valor;
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + String.format(" | Vence em: %d dia(s)", diasParaVencer);
    }
}