package br.ufc.ecommerce.controller;

import br.ufc.ecommerce.dao.UsuarioDAO;
import br.ufc.ecommerce.model.Usuario;
import br.ufc.ecommerce.util.SenhaUtil;
import br.ufc.ecommerce.util.Validador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * CONTROLLER da área do cliente: ver e alterar os próprios dados.
 * Protegido pelo AutenticacaoFilter (só chega aqui quem tem sessão de cliente).
 * O id vem SEMPRE da sessão, nunca de um parâmetro, para um cliente não editar outro.
 */
@WebServlet("/cliente/perfil")
public class PerfilServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario logado = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if ("atualizado".equals(req.getParameter("msg"))) {
            req.setAttribute("sucesso", "Dados atualizados com sucesso.");
        }
        req.setAttribute("formUsuario", usuarioDAO.buscarPorId(logado.getId()));
        req.getRequestDispatcher("/WEB-INF/views/cliente/perfil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario logado = (Usuario) req.getSession().getAttribute("usuarioLogado");

        Usuario u = new Usuario();
        u.setId(logado.getId());
        u.setNome(valor(req, "nome"));
        u.setEndereco(valor(req, "endereco"));
        u.setEmail(valor(req, "email"));
        u.setLogin(valor(req, "login"));
        u.setAdministrador(logado.isAdministrador());

        String novaSenha = req.getParameter("senha");        // opcional na edição
        String confirmacao = req.getParameter("confirmacao");

        List<String> erros = Validador.validar(u, novaSenha, confirmacao, false);
        if (erros.isEmpty() && usuarioDAO.existeLoginDeOutro(u.getLogin(), u.getId())) {
            erros.add("Este login já está em uso por outro usuário.");
        }

        if (!erros.isEmpty()) {
            req.setAttribute("erros", erros);
            req.setAttribute("formUsuario", u);
            req.getRequestDispatcher("/WEB-INF/views/cliente/perfil.jsp").forward(req, resp);
            return;
        }

        usuarioDAO.atualizar(u);
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            usuarioDAO.atualizarSenha(u.getId(), SenhaUtil.gerarHash(novaSenha));
        }

        // Atualiza o usuário guardado na sessão (para o nome no topo da página refletir a mudança)
        Usuario atualizado = usuarioDAO.buscarPorId(u.getId());
        atualizado.setSenha(null);
        req.getSession().setAttribute("usuarioLogado", atualizado);

        resp.sendRedirect(req.getContextPath() + "/cliente/perfil?msg=atualizado");
    }

    private String valor(HttpServletRequest req, String nome) {
        String v = req.getParameter(nome);
        return v == null ? "" : v.trim();
    }
}
