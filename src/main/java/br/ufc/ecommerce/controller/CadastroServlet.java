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
 * CONTROLLER do cadastro de novo cliente (público).
 *  GET  /cadastro -> mostra o formulário
 *  POST /cadastro -> valida, grava no banco via DAO e redireciona
 */
@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("formUsuario", new Usuario());
        req.getRequestDispatcher("/WEB-INF/views/cadastro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1) Monta o objeto do MODEL a partir dos parâmetros do formulário
        Usuario u = new Usuario();
        u.setNome(valor(req, "nome"));
        u.setEndereco(valor(req, "endereco"));
        u.setEmail(valor(req, "email"));
        u.setLogin(valor(req, "login"));
        u.setAdministrador(false); // quem se cadastra pelo site é sempre cliente

        String senha = req.getParameter("senha");
        String confirmacao = req.getParameter("confirmacao");

        // 2) Valida (o servidor nunca confia só no JavaScript)
        List<String> erros = Validador.validar(u, senha, confirmacao, true);
        if (erros.isEmpty() && usuarioDAO.existeLogin(u.getLogin())) {
            erros.add("Este login já está em uso. Escolha outro.");
        }

        // 3) Se houver erros, volta para o formulário mantendo o que foi digitado
        if (!erros.isEmpty()) {
            req.setAttribute("erros", erros);
            req.setAttribute("formUsuario", u);
            req.getRequestDispatcher("/WEB-INF/views/cadastro.jsp").forward(req, resp);
            return;
        }

        // 4) Grava (senha como hash) e redireciona (padrão Post/Redirect/Get: F5 não duplica o cadastro)
        u.setSenha(SenhaUtil.gerarHash(senha));
        usuarioDAO.inserir(u);
        resp.sendRedirect(req.getContextPath() + "/?msg=cadastro_ok");
    }

    private String valor(HttpServletRequest req, String nome) {
        String v = req.getParameter(nome);
        return v == null ? "" : v.trim();
    }
}
