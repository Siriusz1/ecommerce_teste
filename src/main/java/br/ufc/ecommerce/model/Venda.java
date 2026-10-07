package br.ufc.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** MODEL: compra efetivada por um Usuario, com uma lista de itens (VendaProduto). */
public class Venda {

    private Integer id;
    private LocalDateTime dataHora;
    private Usuario usuario;
    private List<VendaProduto> itens = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<VendaProduto> getItens() { return itens; }
    public void setItens(List<VendaProduto> itens) { this.itens = itens; }
}
