package bw.study.farm.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/** Retrieves a connection from Tomcat 11's container-managed connection pool. */
public final class Database {
    private Database() {}

    public static Connection connection() throws SQLException {
        try {
            DataSource source = (DataSource) new InitialContext()
                    .lookup("java:comp/env/jdbc/farmdb");
            return source.getConnection();
        } catch (NamingException exception) {
            throw new SQLException("DataSource jdbc/farmdb not configured in Tomcat", exception);
        }
    }
}