package br.ufc.ecommerce.filter;

import br.ufc.ecommerce.model.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * FILTER: intercepta TODA requisição a /cliente/* e /admin/* ANTES do servlet.
 * É o "porteiro" do sistema: sem sessão válida, o servlet nem chega a ser executado.
 */
@WebFilter(urlPatterns = {"/cliente/*", "/admin/*"})
public class AutenticacaoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String ctx = req.getContextPath();

        HttpSession sessao = req.getSession(false); // false = não cria sessão nova
        Usuario usuario = (sessao == null) ? null : (Usuario) sessao.getAttribute("usuarioLogado");

        if (usuario == null) {
            resp.sendRedirect(ctx + "/?msg=login_necessario");
            return;
        }

        String caminho = req.getServletPath();
        if (caminho.startsWith("/admin") && !usuario.isAdministrador()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso restrito a administradores.");
            return;
        }
        if (caminho.startsWith("/cliente") && usuario.isAdministrador()) {
            resp.sendRedirect(ctx + "/admin/home");
            return;
        }

        // Impede o navegador de guardar a página em cache (o botão "voltar" após o logout não a mostra).
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        chain.doFilter(request, response); // libera o acesso
    }
}
