# Advanced Web Development Exam Codebank

Study examples for Java web exams: Servlets, JSP/JSTL/EL, MVC, sessions, form handling, SQL, JDBC, DAO, CRUD, forwarding, validation, internationalisation, and HTTP.

This repository is for learning and adapting patterns, not a submitted assessment solution.

## Scenarios in the prepared reference archive

1. Grade checker (HTML form + Servlet + JSP)
2. Calculator (POST + arithmetic + validation)
3. Book search (model + Servlet + JSTL table)
4. Session counter
5. Session login, filter, dashboard and logout (learning demo only)
6. Cookies and saved preferences
7. Form validation and Post/Redirect/Get
8. Inventory CRUD (model, service, JDBC DAO, MySQL, controller, JSP)
9. English/Setswana language switch
10. HTTP headers

## Exam reminders

- HTML form `name` must match `request.getParameter`.
- Form `action` must match the Servlet URL mapping.
- Servlet `setAttribute` name must match the JSP EL expression.
- Forward to a JSP under `/WEB-INF/views` for displaying results.
- Use JSTL `<c:out>` to escape user-provided text and `<c:forEach>` for rows.
- Use prepared statements for SQL parameters.
- Redirect after successful POST, forward when showing validation errors.
- Never include actual database credentials in committed code.

**Warning:** The CA3 assignment requires container-managed DataSource and Jakarta Security FORM authentication, not just a basic demo session login or direct DriverManager connection. Follow your lecturer's rules for exam reference materials.
