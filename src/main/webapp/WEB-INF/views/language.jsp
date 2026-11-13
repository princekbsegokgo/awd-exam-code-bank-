<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %><%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html><html><body><h2><fmt:message key="title"/></h2><p><fmt:message key="welcome"/></p>
<a href="${pageContext.request.contextPath}/language?lang=en">English</a> |
<a href="${pageContext.request.contextPath}/language?lang=tn">Setswana</a></body></html>