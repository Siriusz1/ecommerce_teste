package br.ufc.ecommerce.model;

/** MODEL: categoria de produtos. (DAO e telas ficam para a 3ª entrega.) */
public class Categoria {

    private Integer id;
    private String descricao;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
