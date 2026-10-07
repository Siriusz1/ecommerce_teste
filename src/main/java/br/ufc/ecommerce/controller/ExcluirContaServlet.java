package br.ufc.ecommerce.controller;

import br.ufc.ecommerce.dao.UsuarioDAO;
import br.ufc.ecommerce.model.Usuario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** CONTROLLER: o cliente exclui o próprio cadastro. Só aceita POST (exclusão nunca por link/GET). */
@WebServlet("/cliente/excluir")
public class ExcluirContaServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/cliente/perfil");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario logado = (Usuario) req.getSession().getAttribute("usuarioLogado");

        usuarioDAO.excluir(logado.getId());
        req.getSession().invalidate(); // encerra a sessão do usuário que acabou de ser excluído

        resp.sendRedirect(req.getContextPath() + "/?msg=conta_excluida");
    }
}
