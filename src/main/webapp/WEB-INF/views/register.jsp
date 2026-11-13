<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Register (validation practice)</h2><c:if test="${not empty error}"><p><c:out value="${error}"/></p></c:if>
<form method="post" action="${pageContext.request.contextPath}/register">
<label>Name <input name="name" required value="<c:out value='${name}'/>"></label>
<label>Email <input name="email" required value="<c:out value='${email}'/>"></label><button>Submit</button></form></body></html>