-- Script de criação das tabelas (PostgreSQL), baseado no MER da TF01.
-- Antes: crie o banco "ecommerce" (veja o passo a passo) e execute este script DENTRO dele.

CREATE TABLE IF NOT EXISTS usuario (
    id            SERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    endereco      VARCHAR(200) NOT NULL,
    email         VARCHAR(100) NOT NULL,
    login         VARCHAR(50)  NOT NULL UNIQUE,
    senha         VARCHAR(100) NOT NULL,          -- hash BCrypt (60 caracteres)
    administrador BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS categoria (
    id        SERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto (
    id           SERIAL PRIMARY KEY,
    descricao    VARCHAR(150)     NOT NULL,
    preco        DOUBLE PRECISION NOT NULL,
    foto         VARCHAR(255),
    quantidade   INTEGER          NOT NULL,
    categoria_id INTEGER          NOT NULL REFERENCES categoria (id)
);

-- Se o cliente excluir o cadastro, suas vendas (e itens) são removidas junto (CASCADE).
CREATE TABLE IF NOT EXISTS venda (
    id         SERIAL PRIMARY KEY,
    data_hora  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_id INTEGER   NOT NULL REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS venda_produto (
    venda_id   INTEGER          NOT NULL REFERENCES venda (id) ON DELETE CASCADE,
    produto_id INTEGER          NOT NULL REFERENCES produto (id),
    preco      DOUBLE PRECISION NOT NULL,
    quantidade INTEGER          NOT NULL,
    PRIMARY KEY (venda_id, produto_id)
);
