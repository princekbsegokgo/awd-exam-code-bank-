<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Grade result</h2>
<c:choose><c:when test="${not empty error}"><p><c:out value="${error}"/></p></c:when>
<c:otherwise><p><c:out value="${student}"/> got <c:out value="${mark}"/>: <strong><c:out value="${result}"/></strong></p></c:otherwise></c:choose>
<a href="${pageContext.request.contextPath}/grade-form.html">Try again</a></body></html>