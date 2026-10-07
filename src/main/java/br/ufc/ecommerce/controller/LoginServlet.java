package br.ufc.ecommerce.controller;

import br.ufc.ecommerce.dao.UsuarioDAO;
import br.ufc.ecommerce.model.Usuario;
import br.ufc.ecommerce.util.SenhaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * CONTROLLER do login (área única para clientes e administradores).
 * Fluxo: lê parâmetros -> pede ao DAO o usuário -> confere a senha -> cria a sessão -> redireciona.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final int TRINTA_DIAS = 60 * 60 * 24 * 30;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login") == null ? "" : req.getParameter("login").trim();
        String senha = req.getParameter("senha");
        boolean lembrar = req.getParameter("lembrar") != null;

        Usuario usuario = (login.isEmpty() || senha == null || senha.isEmpty())
                ? null : usuarioDAO.buscarPorLogin(login);

        // Mesma mensagem para "usuário não existe" e "senha errada" (não revela qual login existe).
        if (usuario == null || !SenhaUtil.verificar(senha, usuario.getSenha())) {
            req.setAttribute("erroLogin", "Login ou senha inválidos.");
            req.setAttribute("loginLembrado", login);
            req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
            return;
        }

        // COOKIE: guarda só o login (nunca a senha) por 30 dias, se o usuário marcou "lembrar".
        Cookie cookie = new Cookie("loginLembrado", lembrar ? usuario.getLogin() : "");
        cookie.setPath(req.getContextPath() + "/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(lembrar ? TRINTA_DIAS : 0); // 0 = apaga o cookie
        resp.addCookie(cookie);

        // SESSÃO: o objeto Usuario fica guardado no servidor, ligado ao cookie JSESSIONID.
        HttpSession sessao = req.getSession(true);
        req.changeSessionId(); // novo id após o login (proteção contra session fixation)
        usuario.setSenha(null); // o hash não precisa ficar na sessão
        sessao.setAttribute("usuarioLogado", usuario);

        String destino = usuario.isAdministrador() ? "/admin/home" : "/cliente/perfil";
        resp.sendRedirect(req.getContextPath() + destino);
    }
}
