package br.ufc.ecommerce.model;

/**
 * MODEL: item de uma venda (tabela associativa venda_produto).
 * O preço é copiado do produto no momento da compra, para o histórico não mudar
 * se o preço do produto for alterado depois.
 */
public class VendaProduto {

    private Produto produto;
    private double preco;
    private int quantidade;

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getSubtotal() { return preco * quantidade; }
}
