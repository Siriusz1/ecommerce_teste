package br.ufc.ecommerce.listener;

import br.ufc.ecommerce.dao.UsuarioDAO;
import br.ufc.ecommerce.model.Usuario;
import br.ufc.ecommerce.util.SenhaUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * LISTENER: roda uma vez quando a aplicação sobe no Tomcat.
 * Cria um administrador padrão se ainda não existir nenhum
 * (o enunciado diz que o admin pode ser inserido direto no banco).
 *
 * Login: admin | Senha: admin123  (troque depois!)
 */
@WebListener
public class InicializacaoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            if (!dao.existeAdministrador()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEndereco("Sede da loja");
                admin.setEmail("admin@loja.com");
                admin.setLogin("admin");
                admin.setSenha(SenhaUtil.gerarHash("admin123"));
                admin.setAdministrador(true);
                dao.inserir(admin);
                System.out.println("[Ecommerce] Administrador padrão criado (admin / admin123).");
            }
        } catch (Exception e) {
            System.err.println("[Ecommerce] Não foi possível verificar/criar o administrador. "
                    + "O banco está rodando e o schema.sql foi executado? " + e.getMessage());
        }
    }
}
