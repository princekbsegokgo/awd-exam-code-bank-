<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><body><h2>Inventory CRUD (MySQL)</h2>
<c:if test="${not empty error}"><p><c:out value="${error}"/></p></c:if>
<form method="get" action="${pageContext.request.contextPath}/inventory"><input name="q" placeholder="Search name" value="<c:out value='${q}'/>"><button>Search</button></form>
<table border="1"><tr><th>ID</th><th>Name</th><th>Price</th><th>Actions</th></tr>
<c:forEach items="${products}" var="p"><tr><td><c:out value="${p.id}"/></td><td><c:out value="${p.name}"/></td><td><c:out value="${p.price}"/></td><td>
<a href="${pageContext.request.contextPath}/inventory?edit=${p.id}">Edit</a>
<form method="post" action="${pageContext.request.contextPath}/inventory" style="display:inline" onsubmit="return confirm('Delete this item?')">
<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${p.id}"><button>Delete</button></form>
</td></tr></c:forEach></table>
<h3><c:choose><c:when test="${not empty editing}">Edit item</c:when><c:otherwise>Add item</c:otherwise></c:choose></h3>
<form method="post" action="${pageContext.request.contextPath}/inventory">
<c:choose><c:when test="${not empty editing}"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${editing.id}"></c:when>
<c:otherwise><input type="hidden" name="action" value="create"></c:otherwise></c:choose>
<label>Name <input name="name" required maxlength="100" value="<c:out value='${not empty enteredName ? enteredName : editing.name}'/>"></label>
<label>Price <input name="price" required type="number" step="0.01" min="0" value="<c:out value='${not empty enteredPrice ? enteredPrice : editing.price}'/>"></label>
<button>Save</button></form>
<p>This is a teaching CRUD example without authentication or CSRF protection. Do not deploy publicly.</p></body></html>