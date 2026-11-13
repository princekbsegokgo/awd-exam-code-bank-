<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Calculator result</h2>
<c:choose><c:when test="${not empty error}"><p><c:out value="${error}"/></p></c:when><c:otherwise><p>Answer: <c:out value="${result}"/></p></c:otherwise></c:choose>
<a href="${pageContext.request.contextPath}/calculator.html">Back</a></body></html>