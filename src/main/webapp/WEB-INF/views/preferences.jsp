<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Cookies example</h2><p>Saved theme: <c:out value="${theme}"/></p>
<form method="post" action="${pageContext.request.contextPath}/preferences"><select name="theme"><option value="light">Light</option><option value="dark">Dark</option></select><button>Save for 7 days</button></form>
</body></html>