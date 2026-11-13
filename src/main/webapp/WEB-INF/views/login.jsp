<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Practice login</h2><p>Demo only: student / practice123</p>
<c:if test="${not empty error}"><p><c:out value="${error}"/></p></c:if>
<form action="${pageContext.request.contextPath}/login" method="post"><input name="username" required><input name="password" type="password" required><button>Login</button></form></body></html>