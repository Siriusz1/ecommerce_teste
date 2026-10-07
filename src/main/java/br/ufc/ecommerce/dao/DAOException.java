package br.ufc.ecommerce.dao;

/** Exceção não verificada para encapsular SQLException e não poluir os controllers com try/catch. */
public class DAOException extends RuntimeException {
    public DAOException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
