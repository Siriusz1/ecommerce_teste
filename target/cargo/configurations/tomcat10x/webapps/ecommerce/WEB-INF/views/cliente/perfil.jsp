<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Meus dados - Loja Virtual</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/views/fragmentos/topo.jsp" />

  <main class="container estreito">
    <h1>Meus dados</h1>

    <c:if test="${not empty sucesso}">
      <div class="aviso"><c:out value="${sucesso}" /></div>
    </c:if>
    <c:if test="${not empty erros}">
      <div class="erros">
        <ul><c:forEach var="e" items="${erros}"><li><c:out value="${e}" /></li></c:forEach></ul>
      </div>
    </c:if>
    <div id="erros-js" class="erros" hidden></div>

    <form id="formUsuario" method="post" action="${ctx}/cliente/perfil" class="cartao" novalidate>
      <label for="nome">Nome completo</label>
      <input type="text" id="nome" name="nome" maxlength="100" value="<c:out value='${formUsuario.nome}' />">

      <label for="endereco">Endereço</label>
      <input type="text" id="endereco" name="endereco" maxlength="200" value="<c:out value='${formUsuario.endereco}' />">

      <label for="email">E-mail</label>
      <input type="email" id="email" name="email" maxlength="100" value="<c:out value='${formUsuario.email}' />">

      <label for="login">Login</label>
      <input type="text" id="login" name="login" maxlength="50" value="<c:out value='${formUsuario.login}' />">

      <p class="pequeno">Deixe os campos de senha em branco para manter a senha atual.</p>

      <label for="senha">Nova senha</label>
      <input type="password" id="senha" name="senha" maxlength="50">

      <label for="confirmacao">Confirmar nova senha</label>
      <input type="password" id="confirmacao" name="confirmacao" maxlength="50">

      <div class="acoes">
        <button type="submit" class="botao">Salvar alterações</button>
        <a href="${ctx}/">Voltar à página inicial</a>
      </div>
    </form>

    <section class="cartao perigo">
      <h2>Excluir cadastro</h2>
      <p>Essa ação é permanente e apaga também o seu histórico de compras.</p>
      <form method="post" action="${ctx}/cliente/excluir"
            onsubmit="return confirm('Tem certeza que deseja excluir seu cadastro? Essa ação não pode ser desfeita.');">
        <button type="submit" class="botao vermelho">Excluir meu cadastro</button>
      </form>
    </section>
  </main>

  <script src="${ctx}/js/validacao.js"></script>
</body>
</html>
