# E-commerce — 2ª entrega (TF02)

Java 17 · Servlets/JSP (Jakarta EE 10) · JDBC · PostgreSQL · Maven · Tomcat 10.1

## O que esta entrega cobre
- Model: Usuario, Categoria, Produto, Venda, VendaProduto
- Cadastro de cliente (com validação em JavaScript e no servidor)
- Login e logout (clientes e administradores) com sessão
- Área do cliente: alterar dados pessoais e excluir o cadastro
- Cookie "lembrar meu login"

## Como rodar
1. Instale JDK 17+, Maven e PostgreSQL.
2. Crie o banco `ecommerce` e execute `sql/schema.sql` dentro dele.
3. Se seu PostgreSQL não usa postgres/postgres, defina `DB_USER` e `DB_PASSWORD` (e `DB_URL`, se preciso)
   como variáveis de ambiente, ou edite `dao/ConnectionFactory.java`.
4. Na pasta do projeto: `mvn package cargo:run`
   (baixa um Tomcat 10.1 automaticamente). Alternativa: copie `target/ecommerce.war` para a
   pasta `webapps` de um Tomcat 10.1.
5. Acesse http://localhost:8080/ecommerce/

Administrador criado automaticamente na primeira execução: **admin / admin123**.

## Estrutura
```
sql/schema.sql                       criação das tabelas
src/main/java/br/ufc/ecommerce/
  model/        entidades (POJOs)
  dao/          ConnectionFactory (JDBC) e UsuarioDAO (SQL)
  controller/   servlets (Home, Cadastro, Login, Logout, Perfil, ExcluirConta, AdminHome)
  filter/       AutenticacaoFilter (protege /cliente/* e /admin/*)
  listener/     cria o admin padrão ao iniciar
  util/         SenhaUtil (BCrypt) e Validador
src/main/webapp/
  WEB-INF/views/   telas JSP (só acessíveis via controller)
  css/ js/         estilos e validacao.js
```

## Roteiro de estudo
Siga o cadastro de um cliente nesta ordem:
`cadastro.jsp` → `validacao.js` → `CadastroServlet` → `Validador` → `UsuarioDAO.inserir` → `ConnectionFactory`.
Depois siga o login: `index.jsp` → `LoginServlet` → `AutenticacaoFilter` → `PerfilServlet`.

## O que fica para as próximas entregas
Produtos/categorias (DAOs e telas), listagem na home, carrinho, compras e relatórios.
