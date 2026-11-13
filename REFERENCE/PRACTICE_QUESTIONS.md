# Mock exam variations — attempt before looking at code

1. Student mark checker: receive name and mark, show PASS if >=50, FAIL otherwise, reject invalid mark.
2. Delivery charge calculator: total price, free delivery at P500, otherwise P40; use Servlet and JSP.
3. Currency conversion exercise: accept pula and an explicitly supplied practice rate; calculate amount.
4. Search students: add 5 students to an in-memory List, filter by name, display JSTL table.
5. Shopping cart session: add product names to an HttpSession List; clear the session.
6. Page visit counter: session visits and global visits (compare session attribute with ServletContext attribute; consider concurrency).
7. Form validation: required fields, email format, and redisplay entered values after error.
8. Login demonstration: session attribute, guarded page, logout, post-logout test.
9. Cookie preference: choose language or theme, reload to show saved setting.
10. JDBC DAO: change Product into Student, ISBN into student number, or Student into Employee.
11. CRUD: add/edit/delete a department; ensure POST + redirect and PreparedStatement.
12. MySQL search: WHERE name LIKE ?, bind % + search + %.
13. Bilingual catalogue: change language in session with JSTL fmt, use translated properties.
14. MVC debugging: locate incorrect name, URL mapping, request attribute, and forward path.
15. request.getParameter() vs getAttribute() vs session.getAttribute() comparison.
16. Forward vs redirect: after POST, decide which you need and justify it.
17. Loan rules: design a service enforcing at most 3 active loans; explain why controller should not contain rules.
18. Role access: describe web.xml constraints and why hiding a menu is not security.

**Practice routine:** Start new blank project, attempt 15–25 min, consult codebank only if stuck, then explain every line aloud.