<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Session counter</h2><p>This session has visited <c:out value="${sessionScope.visits}"/> times.</p>
<a href="${pageContext.request.contextPath}/counter">Refresh counter</a></body></html>