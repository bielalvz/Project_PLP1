# Sistema de Estoque de Produtos

Sistema desenvolvido em Java para gerenciamento de produtos em estoque, com foco na aplicação de conceitos de Programação Orientada a Objetos (POO), como classes abstratas, herança, interfaces, polimorfismo, composição e tratamento de exceções.

O projeto permite cadastrar produtos comuns e perecíveis, realizar vendas, controlar a quantidade disponível e calcular o valor total do estoque.

## Estrutura do projeto

```text
.
├── EstoqueException.java              # Exceção base do sistema
├── QuantidadeInvalidaException.java   # Exceção para preço ou quantidade inválidos
├── ProdutoIndisponivelException.java  # Exceção para estoque insuficiente
├── Vendavel.java                      # Interface que define o método vender()
├── Product.java                       # Classe abstrata base dos produtos
├── ProdutoComum.java                  # Produto comum
├── ProdutoPerecivel.java              # Produto com validade e desconto automático
├── Estoque.java                       # Gerencia a lista de produtos
└── EstoqueApp.java                    # Classe principal para execução do sistema
```

## Conceitos de Programação Orientada a Objetos

O projeto utiliza diferentes conceitos de POO:

### Classe abstrata

A classe `Product` é abstrata e serve como classe base para os diferentes tipos de produtos.

Ela possui atributos como:

* Nome
* Preço
* Quantidade

Também define o método abstrato:

```java
public abstract double calcularValorTotal();
```

Cada classe filha implementa esse método de acordo com sua própria regra.

### Herança

As classes `ProdutoComum` e `ProdutoPerecivel` herdam de `Product`:

```text
Product
├── ProdutoComum
└── ProdutoPerecivel
```

Dessa forma, as duas classes reutilizam os atributos e comportamentos definidos na classe base.

### Interface

A interface `Vendavel` define o comportamento de venda:

```java
public interface Vendavel {
    void vender(int quantidadeDesejada) throws ProdutoIndisponivelException;
}
```

A classe `Product` implementa essa interface, fazendo com que seus produtos possam ser vendidos.

### Polimorfismo

O método `calcularValorTotal()` é abstrato em `Product`, permitindo que cada tipo de produto tenha sua própria implementação.

`ProdutoComum` calcula:

```text
preço × quantidade
```

Enquanto `ProdutoPerecivel` aplica uma regra adicional de desconto quando o produto está próximo do vencimento.

Além disso, o método `getDescricao()` é sobrescrito em `ProdutoPerecivel` para incluir a quantidade de dias restantes até o vencimento.

### Sobrecarga de métodos

A classe `Product` possui duas versões do método `aplicarDesconto()`:

```java
aplicarDesconto(double percentual)
```

e

```java
aplicarDesconto(double percentual, double descontoMaximo)
```

A primeira aplica o percentual de desconto diretamente.

A segunda aplica o percentual informado, mas limita o desconto ao valor máximo definido.

### Composição

A classe `Estoque` possui uma lista de objetos do tipo `Product`:

```java
private List<Product> produtos = new ArrayList<>();
```

Isso permite que o estoque armazene tanto `ProdutoComum` quanto `ProdutoPerecivel`.

A classe `Estoque` também possui métodos para:

* Adicionar produtos;
* Realizar vendas;
* Consultar os produtos cadastrados;
* Calcular o valor total do estoque.

## Hierarquia de classes

### Produtos

```text
              Product
             /       \
            /         \
ProdutoComum      ProdutoPerecivel
```

`Product` é a classe abstrata principal e implementa a interface `Vendavel`.

### Exceções

```text
             EstoqueException
              /            \
             /              \
QuantidadeInvalidaException  ProdutoIndisponivelException
```

`EstoqueException` é a exceção base utilizada pelo sistema.

`QuantidadeInvalidaException` é utilizada quando o preço ou a quantidade informados no cadastro são negativos.

`ProdutoIndisponivelException` é utilizada quando uma tentativa de venda possui quantidade maior que a disponível no estoque.

## Regras de negócio

### Cadastro de produtos

O construtor de `Product` verifica se o preço e a quantidade são válidos.

Caso o preço seja negativo:

```text
Preço inválido para o produto
```

Caso a quantidade seja negativa:

```text
Quantidade inválida para o produto
```

Nesses casos, é lançada uma `QuantidadeInvalidaException`.

### Produto comum

O `ProdutoComum` calcula o valor total multiplicando o preço pela quantidade disponível:

```text
Valor total = preço × quantidade
```

### Produto perecível

O `ProdutoPerecivel` possui o atributo `diasParaVencer`.

Quando o produto possui `3 dias ou menos` para vencer, é aplicado automaticamente um desconto de 20% no cálculo do valor total.

```text
Valor com desconto = preço × quantidade × 0,8
```

Caso possua mais de 3 dias para vencer, o cálculo é feito normalmente:

```text
Valor total = preço × quantidade
```

### Venda de produtos

O método:

```java
vender(int quantidadeDesejada)
```

verifica se existe quantidade suficiente no estoque.

Quando a quantidade solicitada é maior que a quantidade disponível, é lançada uma:

```java
ProdutoIndisponivelException
```

Quando a venda é realizada com sucesso, a quantidade vendida é retirada do estoque.

### Cálculo do estoque

O método:

```java
calcularValorTotalEstoque()
```

percorre todos os produtos cadastrados e soma o valor retornado por `calcularValorTotal()`.

Dessa forma, o `Estoque` não precisa saber se está trabalhando com um `ProdutoComum` ou um `ProdutoPerecivel`, utilizando o polimorfismo para executar o cálculo correto.

## Como compilar e executar

É necessário ter o JDK instalado no computador.

O projeto pode ser compilado utilizando:

```bash
javac *.java
```

Depois, execute a classe principal:

```bash
java EstoqueApp
```

O projeto foi desenvolvido utilizando recursos compatíveis com Java 17 ou superior.

## Funcionamento do sistema

A classe `EstoqueApp` contém o método `main()` responsável por demonstrar o funcionamento do sistema.

Durante a execução são realizados os seguintes testes:

1. Cadastro de dois produtos comuns:

   * Parafuso
   * Fita Isolante

2. Cadastro de dois produtos perecíveis:

   * Cola de Contato
   * Tinta Spray

3. A `Tinta Spray` possui apenas 2 dias para vencer, fazendo com que o desconto automático de 20% seja aplicado no cálculo do seu valor.

4. É realizada uma tentativa de cadastro de um produto com quantidade negativa, provocando uma `QuantidadeInvalidaException`.

5. É realizada uma venda válida de 50 unidades de Parafuso.

6. É realizada uma segunda tentativa de venda de 100.000 unidades de Parafuso, quantidade superior ao estoque disponível, provocando uma `ProdutoIndisponivelException`.

7. Por fim, o sistema calcula e exibe o valor total dos produtos disponíveis no estoque.

## Exemplo de saída

A execução do programa apresenta informações semelhantes a:

```text
Falha esperada ao cadastrar produto: Quantidade inválida para o produto 'Produto Inválido': -5 (não pode ser negativa).

=== Produtos cadastrados ===
Parafuso | Preço: R$ 0,50 | Quantidade: 500
Fita Isolante | Preço: R$ 8,90 | Quantidade: 100
Cola de Contato | Preço: R$ 15,00 | Quantidade: 30 | Vence em: 10 dia(s)
Tinta Spray | Preço: R$ 22,00 | Quantidade: 20 | Vence em: 2 dia(s)

=== Testando vendas ===
Venda de 50 unidades de Parafuso realizada com sucesso.
Falha esperada na venda: Estoque insuficiente para 'Parafuso'. Disponível: 450, solicitado: 100000.

=== Valor total do estoque: R$ 1985,00 ===
```

## Objetivo do projeto

O objetivo principal é demonstrar, de forma prática, a utilização de conceitos fundamentais da Programação Orientada a Objetos em Java, incluindo:

* Classes abstratas;
* Herança;
* Interfaces;
* Polimorfismo dinâmico;
* Sobrecarga de métodos;
* Composição;
* Encapsulamento;
* Tratamento de exceções;
* Reutilização de código.

O projeto utiliza esses conceitos em um sistema simples de estoque, facilitando a compreensão de como eles podem ser aplicados em uma situação prática.
