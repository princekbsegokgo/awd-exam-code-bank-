# CSE303 Exam Computer Setup + IntelliJ + MySQL + GitHub Handbook

**Revision reference — November 2026.** The downloadable Word version was created in ChatGPT. This Markdown file is the GitHub-accessible quick-reference companion. Do not commit real passwords or use reference code contrary to exam rules.

## 1. Classroom Windows login and policy
1. Sign in using the **Windows** username/password supplied by the lecturer.
2. Press `Win + R`, type `cmd`, press Enter.
3. **Only when instructed**, execute `gpupdate /force`; allow it to finish.
4. Restart if the lecturer or group policy tells you to. Sign in again.
5. If account/policy access fails, ask lab staff. Do not bypass controls.

Note: `gpupdate /force` refreshes Group Policy; it does not install Java, MySQL, IntelliJ or Tomcat.

## 2. Connect in MySQL Workbench
1. Open MySQL Workbench; select a prepared connection.
2. Otherwise, click **+** next to MySQL Connections and select **Standard TCP/IP**.
3. Enter hostname **localhost** only if MySQL is installed on that computer. If the school uses a shared MySQL server, get the host address from the lecturer.
4. Port typically **3306** unless configured otherwise. Enter supplied database username and password privately.
5. Click **Test Connection**, then open the SQL Editor.
6. Execute:

```sql
SELECT VERSION();
SHOW DATABASES;
```

### Practice schema (do not overwrite the exam database)
```sql
CREATE DATABASE IF NOT EXISTS exam_practice;
USE exam_practice;
CREATE TABLE IF NOT EXISTS students (
  student_id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  course VARCHAR(60) NOT NULL,
  mark INT NOT NULL CHECK(mark BETWEEN 0 AND 100)
);
INSERT INTO students(full_name,course,mark) VALUES ('Neo','CSE',75);
SELECT * FROM students;
UPDATE students SET mark=80 WHERE student_id=1;
DELETE FROM students WHERE student_id=1;
```

## 3. Download or clone your code from GitHub
Repository: https://github.com/princekbsegokgo/awd-exam-code-bank-

1. In GitHub open the repository; press **t** to search for files.
2. Choose **Code → Download ZIP**, then extract it, **or** copy Code → HTTPS URL and clone:
```bash
git clone https://github.com/princekbsegokgo/awd-exam-code-bank-.git
```
3. Keep the original reference repository unchanged; create a separate practice copy.
4. Open `REFERENCE/EXAM_LOOKUP.md` before deciding which example to adapt.

## 4. Import project in IntelliJ IDEA
1. Start IntelliJ IDEA → **Open** → select the extracted/cloned directory containing `pom.xml`.
2. Allow Maven import and indexing. Select the correct JDK in **File → Project Structure**; the codebank's `pom.xml` targets Java 17.
3. Maven Tool Window → **Reload All Maven Projects**.
4. Build using Maven → Lifecycle → **clean**, then **package**, or Terminal:
```bash
mvn clean package
```
5. Check `target/awd-codebank.war`. If `mvn` is not on PATH, use IntelliJ's Maven Tool Window.

## 5. Tomcat 10.1 deployment
- The codebank uses `jakarta.servlet.*`, compatible with Tomcat 10.1.
- In IDEs offering Tomcat integration: add Tomcat Server (Local) run configuration and deploy the WAR artifact; confirm the context path.
- If no IDE server integration: after Maven packaging, copy `target/awd-codebank.war` into Tomcat's `webapps` and run Tomcat with your lab's permitted method.
- Open the default WAR context path:

```text
http://localhost:8080/awd-codebank/
http://localhost:8080/awd-codebank/grade-form.html
http://localhost:8080/awd-codebank/inventory
```

If 404, verify server running, correct port, deployment, context path, Servlet mapping and form action.

## 6. Database connection: practice DriverManager
The current codebank uses `src/main/java/bw/study/codebank/dao/Db.java`, with runtime environment variables:

```text
AWD_DB_URL=jdbc:mysql://localhost:3306/awd_practice
AWD_DB_USER=root
AWD_DB_PASSWORD=[supply privately]
```

Run `database/inventory.sql` in Workbench first. Configure variables **for the Tomcat process**, then restart Tomcat. Don't put actual credentials in the repository. The `InventoryServlet` and `ProductDao` are practice examples; the CRUD page isn't suitable to expose publicly.

## 7. Assignment-style JNDI DataSource
Your LIBIS CA3 brief calls for container-managed JNDI DataSource, PreparedStatement DAOs, and Jakarta Security. If the exam says JNDI/DataSource, **do not substitute** the simple DriverManager demo.

Example resource **template** in `src/main/webapp/META-INF/context.xml`:
```xml
<Context>
  <Resource name="jdbc/libis"
            auth="Container" type="javax.sql.DataSource"
            factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
            driverClassName="com.mysql.cj.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/libis"
            username="[database user]"
            password="[supply securely]"
            maxActive="20" maxIdle="5"/>
</Context>
```
Java lookup:
```java
DataSource ds = (DataSource) new InitialContext()
    .lookup("java:comp/env/jdbc/libis");
try (Connection con = ds.getConnection()) {
    // PreparedStatement DAO work here.
}
```

The matching Tomcat pool, JDBC driver installation, credential handling and hostname depend on the lab. Check these with the lecturer.

## 8. How code files fit together
**HTML → Servlet/controller → service → DAO → MySQL → Servlet → JSP.**

- HTML `name="student"` must match Java `request.getParameter("student")`.
- Form `action` must match `@WebServlet("/route")` after accounting for context path.
- Request `setAttribute("items", records)` must match JSP `${items}`.
- `getRequestDispatcher(...).forward(...)` retains request attributes.
- `sendRedirect(...)` causes a new HTTP request; usually use after a successful POST.
- DAO uses `PreparedStatement` and `try-with-resources`.
- JSP uses Jakarta JSTL `jakarta.tags.core`, `<c:forEach>`, `<c:out>`, and EL. No scriptlets for CA3.
- Keep validation/business decisions in services.

## 9. Codebank lookup
| Exam scenario | File(s) |
|---|---|
| Form, POST, pass/fail | `grade-form.html`, `GradeServlet.java`, `grade-result.jsp` |
| Calculator | `calculator.html`, `CalculatorServlet.java` |
| Search + JSTL table | `SearchServlet.java`, `search.jsp`, `Book.java` |
| Sessions | `CounterServlet.java`, `counter.jsp` |
| Intro login (demo only) | `LoginServlet.java`, `DashboardServlet.java`, `DashboardFilter.java` |
| Logout | `LogoutServlet.java` |
| Cookies | `PreferencesServlet.java` |
| Validate form, PRG | `RegisterServlet.java`, `RegisteredServlet.java` |
| MySQL | `database/inventory.sql`, `Db.java` |
| CRUD + DAO + service | `Product.java`, `ProductDao.java`, `ProductService.java`, `InventoryServlet.java`, `inventory.jsp` |
| Translation | `LanguageServlet.java`, `language.jsp`, `messages.properties`, `messages_tn.properties` |

## 10. Adapt code to another exam scenario
Example **Products → Students**:
1. In MySQL, create `students(student_id,full_name,course,mark)`.
2. Copy `Product.java` to `Student.java`; update fields, constructor and getters.
3. Copy `ProductDao.java` to `StudentDao.java`; change SQL table/column names, placeholders and row mapping.
4. Copy `ProductService.java` to `StudentService.java`; validate mark as integer 0–100.
5. Copy `InventoryServlet.java` to `StudentServlet.java`; change URL mapping, parameters, service calls, request attributes and JSP path.
6. Copy `inventory.jsp` to `students.jsp`; change form controls and table expressions.
7. Point database configuration to the new schema.
8. Test list → insert → edit → delete; confirm results in Workbench.

## 11. Troubleshooting chart
| Symptom | Check |
|---|---|
| 404 | Deployment/context, URL mapping, form action, Tomcat running |
| 405 | Wrong HTTP method; missing doPost/doGet |
| 500 | Tomcat console; underlying exception |
| `jakarta.servlet` unresolved | Maven dependency, Project SDK, reload |
| JSTL not found | `jakarta.tags.core`, JSTL API + implementation |
| No suitable JDBC driver | Connector/J dependency and server driver placement |
| Communications link failure | Host, port, server running, firewall |
| Access denied MySQL | Credentials/privileges; ask lecturer |
| Unknown database/table | Schema SQL and selected DB |
| JNDI NameNotFound | Resource name or Tomcat context mismatch |
| Empty JSP table | DAO query, setAttribute name, `items` expression |
| Wrong values | Form name, getParameter, SQL binding order, model getter |
| 8080 busy | Another server or configured port |
| SQL updated all rows | Missing WHERE clause |

## 12. Exam-day checklist
- [ ] Sign in correctly; run `gpupdate /force` and restart only if told.
- [ ] Confirm local versus school MySQL hostname.
- [ ] Test Workbench and inspect assigned tables.
- [ ] Get codebank from GitHub.
- [ ] Open `pom.xml` in IntelliJ; reload Maven and select JDK.
- [ ] Package WAR, start Tomcat, confirm context path.
- [ ] Configure permitted DB connection method privately.
- [ ] Test GET, POST, list/SQL, insert/edit/delete, validation.
- [ ] Follow required submission format and original-work rules.

**Safety/academic note:** The photographed slide contained shared credentials, omitted here. The simple session login is a **learning demo**, not the required Jakarta Security FORM authentication for LIBIS CA3.
