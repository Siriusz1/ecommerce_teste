<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<header class="topo">
  <a class="marca" href="${ctx}/">🛒 Loja Virtual</a>
  <nav>
    <c:choose>
      <c:when test="${not empty sessionScope.usuarioLogado}">
        <span>Olá, <strong><c:out value="${sessionScope.usuarioLogado.nome}" /></strong></span>
        <c:choose>
          <c:when test="${sessionScope.usuarioLogado.administrador}">
            <a href="${ctx}/admin/home">Painel</a>
          </c:when>
          <c:otherwise>
            <a href="${ctx}/cliente/perfil">Meus dados</a>
          </c:otherwise>
        </c:choose>
        <form method="post" action="${ctx}/logout" class="inline">
          <button type="submit" class="link">Sair</button>
        </form>
      </c:when>
      <c:otherwise>
        <a href="${ctx}/cadastro">Cadastre-se</a>
      </c:otherwise>
    </c:choose>
  </nav>
</header>
