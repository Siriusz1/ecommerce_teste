package br.ufc.ecommerce.model;

/**
 * MODEL: representa a entidade Usuario (cliente ou administrador).
 * É um POJO: só atributos, getters e setters. Não sabe nada de banco ou de HTML.
 */
public class Usuario {

    private Integer id;
    private String nome;
    private String endereco;
    private String email;
    private String login;
    private String senha;          // no banco: hash BCrypt
    private boolean administrador;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isAdministrador() { return administrador; }
    public void setAdministrador(boolean administrador) { this.administrador = administrador; }
}
