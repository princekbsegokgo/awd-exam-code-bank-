package bw.study.codebank.dao;
import bw.study.codebank.model.Product;import java.sql.*;import java.util.*;import java.math.BigDecimal;
public class ProductDao {
 public List<Product> findAll(String search)throws SQLException {
  String sql="SELECT id,name,price FROM products WHERE name LIKE ? ORDER BY id";
  List<Product> found=new ArrayList<>();
  try(Connection c=Db.connect();PreparedStatement ps=c.prepareStatement(sql)) {
   ps.setString(1,"%"+(search==null?"":search.trim())+"%");
   try(ResultSet rs=ps.executeQuery()){while(rs.next())found.add(map(rs));}
  }return found;
 }
 public Product findById(int id)throws SQLException {
  try(Connection c=Db.connect();PreparedStatement ps=c.prepareStatement("SELECT id,name,price FROM products WHERE id=?")){
   ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?map(rs):null;}
  }
 }
 public void create(String name,BigDecimal price)throws SQLException {
  try(Connection c=Db.connect();PreparedStatement ps=c.prepareStatement("INSERT INTO products(name,price) VALUES(?,?)")){
   ps.setString(1,name);ps.setBigDecimal(2,price);ps.executeUpdate();
  }
 }
 public boolean update(int id,String name,BigDecimal price)throws SQLException {
  try(Connection c=Db.connect();PreparedStatement ps=c.prepareStatement("UPDATE products SET name=?,price=? WHERE id=?")){
   ps.setString(1,name);ps.setBigDecimal(2,price);ps.setInt(3,id);return ps.executeUpdate()>0;
  }
 }
 public boolean delete(int id)throws SQLException {
  try(Connection c=Db.connect();PreparedStatement ps=c.prepareStatement("DELETE FROM products WHERE id=?")){
   ps.setInt(1,id);return ps.executeUpdate()>0;
  }
 }
 private Product map(ResultSet r)throws SQLException{return new Product(r.getInt("id"),r.getString("name"),r.getBigDecimal("price"));}
}