<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Farmers | Farm Exam Practice</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/site.css"></head>
<body>
<h1>Farm &amp; Livestock Management</h1>
<nav><a href="${pageContext.request.contextPath}/">Home</a>
<a href="${pageContext.request.contextPath}/farmers">Farmers</a>
<a href="${pageContext.request.contextPath}/livestock">Livestock</a></nav>
<p>Register farmers, then assign animals to them.</p>
<c:if test="${param.success eq '1'}"><p class="notice">Change saved successfully.</p></c:if>
<c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>

<section><h2>Registered farmers</h2>
<div class="table-scroll"><table>
<thead><tr><th>ID</th><th>Full name</th><th>Village</th><th>Phone</th><th>Number of animals</th><th>Actions</th></tr></thead>
<tbody>
<c:forEach var="f" items="${farmers}">
<tr>
<td><c:out value="${f.farmerId}"/></td><td><c:out value="${f.fullName}"/></td>
<td><c:out value="${f.village}"/></td><td><c:out value="${f.phone}" default="—"/></td>
<td><c:out value="${f.animalCount}"/></td>
<td>
<c:url var="editFarmerLink" value="/farmers"><c:param name="edit" value="${f.farmerId}"/></c:url>
<a href="${editFarmerLink}">Edit</a>
<form method="post" action="${pageContext.request.contextPath}/farmers"
      onsubmit="return confirm('Delete this farmer? Only possible if they own no animals.');"
      style="display:inline">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="id" value="${f.farmerId}">
<button type="submit" class="danger">Delete</button>
</form>
</td></tr>
</c:forEach></tbody></table></div>
<c:if test="${empty farmers}"><p>No farmers found. Check MySQL or add a farmer.</p></c:if>
</section>

<section><h2><c:choose><c:when test="${not empty formId}">Edit farmer</c:when>
<c:otherwise>Register a farmer</c:otherwise></c:choose></h2>
<form method="post" action="${pageContext.request.contextPath}/farmers">
<input type="hidden" name="action" value="save">
<input type="hidden" name="id" value="${fn:escapeXml(formId)}">
<label>Full name <input name="fullName" required maxlength="100" value="${fn:escapeXml(formName)}"></label>
<label>Village <input name="village" required maxlength="80" value="${fn:escapeXml(formVillage)}"></label>
<label>Phone (optional) <input name="phone" maxlength="25" value="${fn:escapeXml(formPhone)}"></label>
<button type="submit">Save farmer</button>
</form>
<c:if test="${not empty formId}"><p><a href="${pageContext.request.contextPath}/farmers">Cancel editing</a></p></c:if>
</section>
<p><small>Learning example. Database writes use POST; the page displays values through JSTL and escaped EL.</small></p>
</body></html>