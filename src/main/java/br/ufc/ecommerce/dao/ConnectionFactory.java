package br.ufc.ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC: fábrica de conexões com o banco.
 * Os valores padrão servem para desenvolvimento local; podem ser trocados
 * com as variáveis de ambiente DB_URL, DB_USER e DB_PASSWORD.
 */
public class ConnectionFactory {

    private static final String URL = env("DB_URL",
            "jdbc:postgresql://localhost:5432/ecommerce");
    private static final String USER = env("DB_USER", "postgres");
    private static final String PASSWORD = env("DB_PASSWORD", "postgres");

    static {
        try {
            // Registra o driver JDBC (necessário em alguns ambientes com Tomcat).
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionFactory() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static String env(String chave, String padrao) {
        String valor = System.getenv(chave);
        return (valor == null || valor.isBlank()) ? padrao : valor;
    }
}
