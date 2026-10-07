<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Loja Virtual</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/views/fragmentos/topo.jsp" />

  <main class="container grade">
    <section>
      <h1>Produtos</h1>
      <c:if test="${not empty mensagem}">
        <div class="aviso"><c:out value="${mensagem}" /></div>
      </c:if>
      <div class="cartao vazio">
        A lista de produtos em estoque será exibida aqui na 3ª entrega.
      </div>
    </section>

    <aside>
      <c:choose>
        <c:when test="${not empty sessionScope.usuarioLogado}">
          <div class="cartao">
            <h2>Sua conta</h2>
            <p>Você está logado como <strong><c:out value="${sessionScope.usuarioLogado.login}" /></strong>.</p>
            <c:choose>
              <c:when test="${sessionScope.usuarioLogado.administrador}">
                <a class="botao" href="${ctx}/admin/home">Ir para o painel</a>
              </c:when>
              <c:otherwise>
                <a class="botao" href="${ctx}/cliente/perfil">Meus dados</a>
              </c:otherwise>
            </c:choose>
          </div>
        </c:when>
        <c:otherwise>
          <div class="cartao">
            <h2>Entrar</h2>
            <c:if test="${not empty erroLogin}">
              <div class="erros"><c:out value="${erroLogin}" /></div>
            </c:if>
            <form method="post" action="${ctx}/login">
              <label for="login">Login</label>
              <input type="text" id="login" name="login" required
                     value="<c:out value='${loginLembrado}' />">

              <label for="senha">Senha</label>
              <input type="password" id="senha" name="senha" required>

              <label class="check">
                <input type="checkbox" name="lembrar" ${not empty loginLembrado ? 'checked' : ''}>
                Lembrar meu login
              </label>

              <button type="submit" class="botao">Entrar</button>
            </form>
            <p class="pequeno">Ainda não tem conta? <a href="${ctx}/cadastro">Cadastre-se</a></p>
          </div>
        </c:otherwise>
      </c:choose>
    </aside>
  </main>
</body>
</html>
