<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Cadastro - Loja Virtual</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/views/fragmentos/topo.jsp" />

  <main class="container estreito">
    <h1>Criar conta</h1>

    <%-- Erros vindos do servidor (Validador / DAO) --%>
    <c:if test="${not empty erros}">
      <div class="erros">
        <ul><c:forEach var="e" items="${erros}"><li><c:out value="${e}" /></li></c:forEach></ul>
      </div>
    </c:if>
    <%-- Erros vindos do JavaScript (preenchido por js/validacao.js) --%>
    <div id="erros-js" class="erros" hidden></div>

    <form id="formUsuario" method="post" action="${ctx}/cadastro" class="cartao" data-senha-obrigatoria="true" novalidate>
      <label for="nome">Nome completo</label>
      <input type="text" id="nome" name="nome" maxlength="100" value="<c:out value='${formUsuario.nome}' />">

      <label for="endereco">Endereço</label>
      <input type="text" id="endereco" name="endereco" maxlength="200" value="<c:out value='${formUsuario.endereco}' />">

      <label for="email">E-mail</label>
      <input type="email" id="email" name="email" maxlength="100" value="<c:out value='${formUsuario.email}' />">

      <label for="login">Login</label>
      <input type="text" id="login" name="login" maxlength="50" value="<c:out value='${formUsuario.login}' />">

      <label for="senha">Senha (mín. 6 caracteres)</label>
      <input type="password" id="senha" name="senha" maxlength="50">

      <label for="confirmacao">Confirmar senha</label>
      <input type="password" id="confirmacao" name="confirmacao" maxlength="50">

      <div class="acoes">
        <button type="submit" class="botao">Cadastrar</button>
        <a href="${ctx}/">Voltar à página inicial</a>
      </div>
    </form>
  </main>

  <script src="${ctx}/js/validacao.js"></script>
</body>
</html>
