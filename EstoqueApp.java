public class EstoqueApp {

    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        // Cadastro de produtos válidos (2 comuns + 2 perecíveis, um deles perto de vencer)
        try {
            estoque.adicionarProduto(new ProdutoComum("Parafuso", 0.50, 500));
            estoque.adicionarProduto(new ProdutoComum("Fita Isolante", 8.90, 100));
            estoque.adicionarProduto(new ProdutoPerecivel("Cola de Contato", 15.00, 30, 10));
            estoque.adicionarProduto(new ProdutoPerecivel("Tinta Spray", 22.00, 20, 2)); // <= 3 dias: tem desconto
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Erro inesperado ao cadastrar produto válido: " + e.getMessage());
        }

        // Tentativa de cadastro inválido (quantidade negativa)
        try {
            estoque.adicionarProduto(new ProdutoComum("Produto Inválido", 10.00, -5));
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Falha esperada ao cadastrar produto: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Produtos cadastrados ===");
        for (Product p : estoque.getProdutos()) {
            System.out.println(p.getDescricao());
        }

        System.out.println();
        System.out.println("=== Testando vendas ===");
        try {
            estoque.venderProduto(0, 50); // venda válida do Parafuso
            System.out.println("Venda de 50 unidades de Parafuso realizada com sucesso.");
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Erro inesperado na venda válida: " + e.getMessage());
        }

        try {
            estoque.venderProduto(0, 100000); // tenta vender mais do que existe
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Falha esperada na venda: " + e.getMessage());
        }

        System.out.println();
        System.out.printf("=== Valor total do estoque: R$ %.2f ===%n", estoque.calcularValorTotalEstoque());
    }
}