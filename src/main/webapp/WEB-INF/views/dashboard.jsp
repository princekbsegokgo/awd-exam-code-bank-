<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Protected dashboard</h2><p>Hello <c:out value="${sessionScope.user}"/></p>
<form method="post" action="${pageContext.request.contextPath}/logout"><button>Log out</button></form></body></html>