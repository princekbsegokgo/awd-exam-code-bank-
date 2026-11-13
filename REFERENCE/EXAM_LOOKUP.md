# Exam code lookup
| Exam request | Useful file |
|---|---|
| Receive POST and validate | src/main/java/bw/study/codebank/controller/GradeServlet.java |
| Display result in JSP | src/main/webapp/WEB-INF/views/grade-result.jsp |
| HTML form | src/main/webapp/grade-form.html |
| Calculator | CalculatorServlet.java |
| Display list | SearchServlet.java and search.jsp |
| Session counter | CounterServlet.java |
| Login/logout | LoginServlet.java and LogoutServlet.java |
| Cookies | PreferencesServlet.java |
| CRUD | ProductDao.java, InventoryServlet.java, inventory.jsp |
| English/Setswana | language.jsp, messages*.properties |

- getParameter reads form input.
- setAttribute + forward shares data in one request.
- sendRedirect starts another request.
- getSession(false) does not create a new session.
- Always use PreparedStatement with SQL input.
- Keep JSP free from scriptlets for CA3.
- Jakarta Security FORM and DataSource are required for the CA3 assignment.
