package bw.study.codebank.service;
import bw.study.codebank.dao.ProductDao;import java.math.BigDecimal;import java.sql.SQLException;
public class ProductService {
 private final ProductDao dao=new ProductDao();
 public ProductDao getDao(){return dao;}
 public void create(String name,String price)throws SQLException {dao.create(validateName(name),validatePrice(price));}
 public boolean update(int id,String name,String price)throws SQLException {return dao.update(id,validateName(name),validatePrice(price));}
 public boolean delete(int id)throws SQLException {return dao.delete(id);}
 private String validateName(String name){if(name==null||name.isBlank()||name.length()>100)throw new IllegalArgumentException("Name must be 1–100 characters.");return name.trim();}
 private BigDecimal validatePrice(String raw){try {BigDecimal p=new BigDecimal(raw).setScale(2,java.math.RoundingMode.UNNECESSARY);if(p.signum()<0||p.compareTo(new BigDecimal("99999999.99"))>0)throw new NumberFormatException();return p;}
  catch(RuntimeException e){throw new IllegalArgumentException("Price must be positive or zero, with at most two decimal places.");}}
}