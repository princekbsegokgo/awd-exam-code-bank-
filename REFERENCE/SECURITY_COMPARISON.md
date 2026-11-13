# Sessions vs container-managed FORM authentication

**Foundational session login:** LoginServlet checks a sample credential and puts user into HttpSession. DashboardFilter redirects unauthenticated visits. This helps you understand HttpSession and filters but is not a secure production login.

**LIBIS CA3 requirement:** Remove the custom authentication filter and use Jakarta Security/Tomcat container-managed FORM authentication with a DataSourceRealm, roles member and librarian, login form field names j_username, j_password, and action j_security_check. Protect routes through security-constraint entries. Use request.getRemoteUser() for trusted identity. This codebank DOES NOT implement that complete security configuration.

For real login: password hashes and secure transport are essential, and demo credentials must never be used on a public service.