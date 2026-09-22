import java.util.ArrayList;
import java.util.List;

public class Estoque {

    private List<Product> produtos = new ArrayList<>();

    public void adicionarProduto(Product p) {
        produtos.add(p);
    }

    public void venderProduto(int indice, int quantidade) throws ProdutoIndisponivelException {
        Product produto = produtos.get(indice);
        produto.vender(quantidade);
    }

    public double calcularValorTotalEstoque() {
        double total = 0.0;
        for (Product p : produtos) {
            total += p.calcularValorTotal();
        }
        return total;
    }

    public List<Product> getProdutos() {
        return produtos;
    }
}