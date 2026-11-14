# Exam Scenario: Farmer & Livestock Management System

**Level:** intermediate • **Stack:** Java 17+, Tomcat 11, Jakarta Servlet 6.1, JSP/JSTL/EL, MySQL, JDBC via a JNDI DataSource, Maven.

## The practical exam question (40 marks)
A local farmers' cooperative needs a web system to keep track of farmers and their livestock. Each farmer may own many animals; each animal must belong to exactly one farmer. Build an MVC Java web application that:

1. Registers, lists, edits and deletes farmers (8 marks).
2. Registers, lists, searches, edits and deletes livestock (12 marks).
3. Stores permanent records in MySQL with a one-to-many relationship and a unique ear-tag number (7 marks).
4. Uses **Servlet -> service -> DAO -> database** for actions, and JSP with JSTL/EL for output (7 marks).
5. Validates input and handles errors: no future birth dates, invalid species, duplicate ear tags or deleting a farmer who still owns animals (6 marks).

### Example information
Farmers: **Kabo Dube** (Mochudi) and **Naledi Molefe** (Maun).
Livestock: **BW-C001** (cow, Kabo), **BW-G001** (goat, Naledi), **BW-S001** (sheep, Kabo).

## 1. What to open in MySQL Workbench
Open **MySQL Workbench** -> connect to the exam MySQL server -> open a SQL tab -> open and execute **`database/farm_exam.sql`**. Verify:

```sql
USE farm_exam;
SELECT * FROM farmers;
SELECT * FROM livestock;
SELECT f.full_name, a.tag_number, a.species
FROM farmers f JOIN livestock a ON a.farmer_id = f.farmer_id;
```

### Relationship to remember
`farmers.farmer_id` (PK) <--- `livestock.farmer_id` (FK). One farmer owns many animals. Tag number is UNIQUE. Foreign-key `ON DELETE RESTRICT` prevents accidentally deleting a farmer with livestock.

## 2. Open the project in IntelliJ
1. From the repository, download ZIP or clone the GitHub repository.
2. In IntelliJ IDEA, choose **File -> Open** and select **this folder** (`scenarios/farm-livestock`) containing its own `pom.xml`.
3. Select **JDK 17 or newer** (Tomcat 11 requires Java 17+).
4. Maven panel -> reload; use **Lifecycle -> clean -> package**. Or run `mvn clean package`.
5. The WAR output is `target/farm-livestock.war`.
6. Java web source: `src/main/java/bw/study/farm/`. JSP pages: `src/main/webapp/WEB-INF/views/`.

## 3. Configure MySQL for Tomcat 11 (JNDI DataSource)
This example deliberately uses the assignment-style **container-managed DataSource**, not hard-coded DriverManager credentials.

1. Install/configure Tomcat **11** and Java **17+**.
2. Place a compatible MySQL **Connector/J** driver JAR in `TOMCAT_HOME/lib`, then restart Tomcat.
3. Copy **`setup/tomcat-context.example.xml`** to Tomcat's **`conf/Catalina/localhost/farm-livestock.xml`** on the exam PC.
4. Change `url` to the MySQL host/database used in the lab; set the database username and password **only in that local Tomcat configuration file**. Do **not** push credentials to GitHub.
5. Ensure your local Tomcat installation supports the included DBCP2 datasource factory (standard Tomcat distribution).
6. Deploy `target/farm-livestock.war` to Tomcat's `webapps` directory, or configure Tomcat Server -> Local with WAR deployment in supported IntelliJ editions.
7. Start Tomcat and open **http://localhost:8080/farm-livestock/** (unless lecturer assigned another port/context).
8. If your school uses a shared MySQL server, replace **localhost** with the correct school server hostname. Ask your lecturer before changing network settings.

**Note:** External context file binds the JNDI resource to the deployed app and should be kept on the exam PC. `WEB-INF/web.xml` declares its resource reference. Never store a real password in this repository.

## 4. Understand the code flow
- `Farmer.java` and `Livestock.java`: Java model objects.
- `FarmerDao.java` and `LivestockDao.java`: SQL queries using PreparedStatement.
- `Database.java`: looks up `java:comp/env/jdbc/farmdb` DataSource from Tomcat.
- `FarmService.java`: validates data and enforces rules.
- `FarmersServlet.java` and `LivestockServlet.java`: handle GET forms/list and POST create/edit/delete.
- `farmers.jsp` and `livestock.jsp`: display pages with JSTL/EL, without Java scriptlets.

**The key exam flow:** Browser form -> Servlet -> FarmService -> DAO -> MySQL -> Servlet -> JSP.

## 5. Test these exact scenarios
| Action | Expected result |
|---|---|
| List farmers | The seeded farmers appear |
| Register new farmer | A new row appears in MySQL and the webpage |
| Register goat with tag BW-G999 | Goat is linked to selected farmer |
| Register second BW-G999 | Friendly duplicate tag error |
| Search 'Cattle' or 'BW-C001' | Matching animal(s) appear |
| Edit animal owner | Ownership changes in the table |
| Enter future birth date | Friendly validation error |
| Delete farmer who still owns animals | Rejected with meaningful message |
| Delete an animal | Row removed (POST, not GET) |
| Delete a farmer with no animals | Farmer can be deleted |

## 6. How to modify this during the exam
If the question asks for **students and courses** instead:
1. Change the model names and table names.
2. Change the input labels and fields.
3. Change DAO SQL and JDBC row mapping.
4. Keep the Servlet -> Service -> DAO -> JSP structure.
5. Re-test CREATE, READ, UPDATE, DELETE.

If the question asks **farmers and livestock**, reuse the files as a reference, but adapt according to the exact assignment and your lecturer's code-reuse rules.

## 7. Common errors
- **404:** wrong Tomcat context or `@WebServlet` URL; confirm `/farm-livestock/farmers`.
- **500 / NameNotFoundException:** JNDI datasource not configured under `java:comp/env/jdbc/farmdb`.
- **No suitable driver:** Connector/J missing from Tomcat shared `lib`.
- **Access denied:** MySQL hostname / credentials / permissions wrong.
- **Unknown database/table:** SQL setup not executed.
- **JSTL URI not found:** Maven dependencies not packaged; check `jakarta.tags.core` and reload Maven.
- **Data not shown:** check controller's `setAttribute`, JSP's `items` name and DAO query.
- **Tomcat will not start:** run `java -version`, check 8080 is free, inspect Tomcat logs.

## 8. Limits of this learning example
No authentication, CSRF protection, concurrency handling or audit logs are implemented. **Do not deploy this publicly with real farmer data.** Keep it as a local exam-practice application. Use your course's security requirements for assessed work.
