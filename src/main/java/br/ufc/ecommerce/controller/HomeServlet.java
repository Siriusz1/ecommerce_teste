package br.ufc.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * CONTROLLER da página inicial ("/").
 * Lê o cookie "loginLembrado" (para preencher o campo de login) e traduz o parâmetro ?msg=...
 * em uma mensagem. A tradução é feita aqui, e não direto na tela, para nunca imprimir
 * texto vindo da URL (evita injeção de HTML/XSS).
 */
@WebServlet({"", "/home"})
public class HomeServlet extends HttpServlet {

    private static final Map<String, String> MENSAGENS = Map.of(
            "cadastro_ok", "Cadastro realizado com sucesso! Faça login para continuar.",
            "conta_excluida", "Seu cadastro foi excluído.",
            "logout", "Você saiu da sua conta.",
            "login_necessario", "Faça login para acessar essa página."
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chave = req.getParameter("msg");
        String msg = (chave == null) ? null : MENSAGENS.get(chave);
        if (msg != null) {
            req.setAttribute("mensagem", msg);
        }

        if (req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if ("loginLembrado".equals(c.getName())) {
                    req.setAttribute("loginLembrado", c.getValue());
                }
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
