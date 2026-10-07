<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Painel - Loja Virtual</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/views/fragmentos/topo.jsp" />

  <main class="container estreito">
    <h1>Painel do administrador</h1>
    <div class="cartao">
      <p>Login de administrador funcionando, com sessão válida.</p>
      <p class="pequeno">As telas de produtos e categorias entram na 3ª entrega, e os relatórios na 4ª.</p>
    </div>
  </main>
</body>
</html>
