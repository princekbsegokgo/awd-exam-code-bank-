<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Livestock | Farm Exam Practice</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/site.css"></head>
<body>
<h1>Farm &amp; Livestock Management</h1>
<nav><a href="${pageContext.request.contextPath}/">Home</a>
<a href="${pageContext.request.contextPath}/farmers">Farmers</a>
<a href="${pageContext.request.contextPath}/livestock">Livestock</a></nav>
<c:if test="${param.success eq '1'}"><p class="notice">Change saved successfully.</p></c:if>
<c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>

<section>
<h2>Search &amp; view livestock</h2>
<form method="get" action="${pageContext.request.contextPath}/livestock">
<label>Search by tag, species or farmer name
<input name="q" value="${fn:escapeXml(query)}" placeholder="e.g. BW-C001, Goat, Kabo"></label>
<button type="submit">Search</button>
<a href="${pageContext.request.contextPath}/livestock">Clear</a></form>
<div class="table-scroll"><table>
<thead><tr><th>Tag</th><th>Species</th><th>Breed</th><th>Sex</th><th>Born</th><th>Status</th><th>Farmer</th><th>Actions</th></tr></thead>
<tbody>
<c:forEach var="a" items="${animals}"><tr>
<td><c:out value="${a.tagNumber}"/></td><td><c:out value="${a.species}"/></td>
<td><c:out value="${a.breed}"/></td><td><c:out value="${a.sex}"/></td>
<td><c:out value="${a.dateOfBirth}" default="Unknown"/></td><td><c:out value="${a.status}"/></td>
<td><c:out value="${a.farmerName}"/></td>
<td>
<c:url var="editAnimalLink" value="/livestock"><c:param name="edit" value="${a.animalId}"/></c:url>
<a href="${editAnimalLink}">Edit</a>
<form method="post" action="${pageContext.request.contextPath}/livestock" style="display:inline"
      onsubmit="return confirm('Delete this animal record?');">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="id" value="${a.animalId}">
<button type="submit" class="danger">Delete</button>
</form></td></tr></c:forEach>
</tbody></table></div>
<c:if test="${empty animals}"><p>No matching livestock found.</p></c:if>
</section>

<section><h2><c:choose><c:when test="${not empty formAnimalId}">Edit animal</c:when>
<c:otherwise>Register an animal</c:otherwise></c:choose></h2>
<form method="post" action="${pageContext.request.contextPath}/livestock">
<input type="hidden" name="action" value="save">
<input type="hidden" name="id" value="${fn:escapeXml(formAnimalId)}">
<label>Unique ear-tag number <input name="tagNumber" required maxlength="30"
value="${fn:escapeXml(formTag)}" placeholder="BW-C999"></label>
<label>Species <select name="species" required>
<option value="">Choose...</option>
<option value="Cattle" ${formSpecies eq 'Cattle' ? 'selected' : ''}>Cattle</option>
<option value="Goat" ${formSpecies eq 'Goat' ? 'selected' : ''}>Goat</option>
<option value="Sheep" ${formSpecies eq 'Sheep' ? 'selected' : ''}>Sheep</option>
<option value="Chicken" ${formSpecies eq 'Chicken' ? 'selected' : ''}>Chicken</option>
<option value="Donkey" ${formSpecies eq 'Donkey' ? 'selected' : ''}>Donkey</option>
</select></label>
<label>Breed <input name="breed" maxlength="80" required value="${fn:escapeXml(formBreed)}"></label>
<label>Sex <select name="sex" required>
<option value="">Choose...</option>
<option value="Female" ${formSex eq 'Female' ? 'selected' : ''}>Female</option>
<option value="Male" ${formSex eq 'Male' ? 'selected' : ''}>Male</option>
</select></label>
<label>Date of birth (optional) <input name="birthDate" type="date" value="${fn:escapeXml(formBirthDate)}"></label>
<label>Status <select name="status" required>
<option value="Active" ${empty formStatus or formStatus eq 'Active' ? 'selected' : ''}>Active</option>
<option value="Sold" ${formStatus eq 'Sold' ? 'selected' : ''}>Sold</option>
<option value="Deceased" ${formStatus eq 'Deceased' ? 'selected' : ''}>Deceased</option>
</select></label>
<label>Farmer (owner) <select name="farmerId" required>
<option value="">Choose farmer...</option>
<c:forEach var="f" items="${farmers}">
<option value="${f.farmerId}" ${formFarmerId eq f.farmerId ? 'selected' : ''}><c:out value="${f.fullName}"/></option>
</c:forEach></select></label>
<button type="submit">Save animal</button>
</form>
<c:if test="${not empty formAnimalId}"><p><a href="${pageContext.request.contextPath}/livestock">Cancel editing</a></p></c:if>
</section>
<p><small>Before registering livestock, add at least one farmer using the Farmers page.</small></p>
</body></html>