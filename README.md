# Sistema de Estoque de Produtos

Exercício de implementação em Java aplicando classes abstratas, herança, interfaces,
polimorfismo, composição e tratamento de exceções, através de um sistema simples de
cadastro e venda de produtos em estoque.

## Estrutura do projeto

```
.
├── EstoqueException.java              # Exceção base do domínio
├── QuantidadeInvalidaException.java   # Preço/quantidade inválidos no cadastro
├── ProdutoIndisponivelException.java  # Venda maior que o estoque disponível
├── Vendavel.java                      # Interface com o método vender()
├── Product.java                       # Classe abstrata base (implements Vendavel)
├── ProdutoComum.java                  # Produto sem regra especial de cálculo
├── ProdutoPerecivel.java              # Produto com desconto perto do vencimento
├── Estoque.java                       # Composição: mantém uma lista de Product
└── EstoqueApp.java                    # Classe principal (main)
```

## Hierarquia de classes

- **Exceções:** `EstoqueException` (base) → `QuantidadeInvalidaException` e
  `ProdutoIndisponivelException` (específicas).
- **Produtos:** `Product` (abstrata, implementa `Vendavel`) → `ProdutoComum` e
  `ProdutoPerecivel` (herança + polimorfismo dinâmico).
- **Estoque:** `Estoque` **tem uma** lista de `Product` (composição, não herança).

## Regras de negócio

- O construtor de `Product` lança `QuantidadeInvalidaException` se `preco` ou
  `quantidade` forem negativos.
- `calcularValorTotal()` é abstrato: cada subclasse calcula à sua maneira.
  - `ProdutoComum`: `preco × quantidade`.
  - `ProdutoPerecivel`: `preco × quantidade`, com 20% de desconto automático quando
    `diasParaVencer <= 3`.
- `vender(int quantidadeDesejada)` lança `ProdutoIndisponivelException` se a
  quantidade pedida for maior que o estoque disponível.
- `aplicarDesconto(double percentual)` e `aplicarDesconto(double percentual, double descontoMaximo)`
  são versões sobrecarregadas (polimorfismo estático) do mesmo método.
- `Estoque.calcularValorTotalEstoque()` percorre a lista somando o valor de cada
  produto sem saber qual subtipo está processando (polimorfismo dinâmico).

## Como compilar e executar

Requer JDK instalado (Java 17+).

```bash
javac *.java
java EstoqueApp
```

## Saída esperada

O `EstoqueApp` demonstra, no `main()`:

1. Cadastro de 2 `ProdutoComum` e 2 `ProdutoPerecivel` (um deles com
   `diasParaVencer <= 3`).
2. Uma tentativa de cadastro com quantidade negativa, capturando
   `QuantidadeInvalidaException`.
3. Uma venda válida, seguida de uma tentativa de vender mais do que o disponível,
   capturando `ProdutoIndisponivelException`.
4. O valor total do estoque, somando corretamente produtos comuns e perecíveis.