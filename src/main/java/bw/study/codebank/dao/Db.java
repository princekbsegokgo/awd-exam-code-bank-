package bw.study.codebank.dao;
import java.sql.*;
/** Configure environment variables for local exam practice. Never commit actual passwords. */
public final class Db {
 private Db(){}
 public static Connection connect()throws SQLException {
  String url=System.getenv().getOrDefault("AWD_DB_URL","jdbc:mysql://localhost:3306/awd_practice");
  String user=System.getenv().getOrDefault("AWD_DB_USER","root");
  String password=System.getenv("AWD_DB_PASSWORD");
  if(password==null)throw new SQLException("AWD_DB_PASSWORD environment variable is missing");
  return DriverManager.getConnection(url,user,password);
 }
}