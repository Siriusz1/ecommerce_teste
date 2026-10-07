package br.ufc.ecommerce.dao;

import br.ufc.ecommerce.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO (Data Access Object): único lugar que conhece o SQL da tabela usuario.
 * Os controllers chamam estes métodos com objetos Usuario e não sabem que existe banco.
 *
 * Boas práticas usadas aqui:
 *  - PreparedStatement com "?" (evita SQL Injection);
 *  - try-with-resources (fecha Connection, Statement e ResultSet sozinho).
 */
public class UsuarioDAO {

    private static final String COLUNAS = "id, nome, endereco, email, login, senha, administrador";

    /** INSERT. Após inserir, o id gerado pelo banco é colocado no próprio objeto. */
    public void inserir(Usuario u) {
        String sql = "INSERT INTO usuario (nome, endereco, email, login, senha, administrador) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"})) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getEndereco());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getLogin());
            ps.setString(5, u.getSenha());
            ps.setBoolean(6, u.isAdministrador());
            ps.executeUpdate();

            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    u.setId(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir usuário", e);
        }
    }

    /** UPDATE dos dados pessoais (não mexe na senha nem no perfil administrador). */
    public void atualizar(Usuario u) {
        String sql = "UPDATE usuario SET nome = ?, endereco = ?, email = ?, login = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getEndereco());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getLogin());
            ps.setInt(5, u.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar usuário", e);
        }
    }

    /** UPDATE só da senha (recebe o hash já pronto). */
    public void atualizarSenha(int id, String senhaHash) {
        String sql = "UPDATE usuario SET senha = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, senhaHash);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar senha", e);
        }
    }

    /** DELETE. As vendas do usuário caem junto por causa do ON DELETE CASCADE. */
    public void excluir(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir usuário", e);
        }
    }

    public Usuario buscarPorId(int id) {
        return buscarUm("SELECT " + COLUNAS + " FROM usuario WHERE id = ?", id);
    }

    public Usuario buscarPorLogin(String login) {
        return buscarUm("SELECT " + COLUNAS + " FROM usuario WHERE login = ?", login);
    }

    /** Login já usado por qualquer usuário? (usado no cadastro) */
    public boolean existeLogin(String login) {
        return contar("SELECT COUNT(*) FROM usuario WHERE login = ?", login) > 0;
    }

    /** Login já usado por OUTRO usuário? (usado na edição, para não conflitar consigo mesmo) */
    public boolean existeLoginDeOutro(String login, int idDoUsuarioAtual) {
        return contar("SELECT COUNT(*) FROM usuario WHERE login = ? AND id <> ?", login, idDoUsuarioAtual) > 0;
    }

    public boolean existeAdministrador() {
        return contar("SELECT COUNT(*) FROM usuario WHERE administrador = TRUE") > 0;
    }

    // ---------- métodos auxiliares privados ----------

    private Usuario buscarUm(String sql, Object parametro) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar usuário", e);
        }
    }

    private int contar(String sql, Object... parametros) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar usuários", e);
        }
    }

    /** Converte uma linha do ResultSet em objeto Usuario (mapeamento objeto-relacional manual). */
    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setEndereco(rs.getString("endereco"));
        u.setEmail(rs.getString("email"));
        u.setLogin(rs.getString("login"));
        u.setSenha(rs.getString("senha"));
        u.setAdministrador(rs.getBoolean("administrador"));
        return u;
    }
}
