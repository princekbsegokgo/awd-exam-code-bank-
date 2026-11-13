<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Search books (in memory)</h2>
<form method="get" action="${pageContext.request.contextPath}/search"><input name="q" value="<c:out value='${query}'/>" placeholder="Title or author"><button>Search</button></form>
<table border="1"><tr><th>ID</th><th>Title</th><th>Author</th></tr>
<c:forEach items="${books}" var="b"><tr><td><c:out value="${b.id}"/></td><td><c:out value="${b.title}"/></td><td><c:out value="${b.author}"/></td></tr></c:forEach></table>
<c:if test="${empty books}"><p>No books found.</p></c:if></body></html>