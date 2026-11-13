package bw.study.codebank.controller;
import bw.study.codebank.service.ProductService;import bw.study.codebank.model.Product;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;
import java.io.IOException;import java.sql.SQLException;
@WebServlet("/inventory") public class InventoryServlet extends HttpServlet {
 private final ProductService service=new ProductService();
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  try {req.setAttribute("products",service.getDao().findAll(req.getParameter("q")));
   String edit=req.getParameter("edit");if(edit!=null){int id=Integer.parseInt(edit);req.setAttribute("editing",service.getDao().findById(id));}
  }catch(SQLException e){getServletContext().log("Inventory DB error",e);req.setAttribute("error","Database unavailable. Check MySQL setup.");}
   catch(NumberFormatException e){req.setAttribute("error","Invalid product ID.");}
  req.setAttribute("q",req.getParameter("q"));req.getRequestDispatcher("/WEB-INF/views/inventory.jsp").forward(req,resp);
 }
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  req.setCharacterEncoding("UTF-8");String action=req.getParameter("action");
  try {
   if("create".equals(action)){service.create(req.getParameter("name"),req.getParameter("price"));}
   else if("update".equals(action)){service.update(Integer.parseInt(req.getParameter("id")),req.getParameter("name"),req.getParameter("price"));}
   else if("delete".equals(action)){service.delete(Integer.parseInt(req.getParameter("id")));}
   else throw new IllegalArgumentException("Unknown action.");
   resp.sendRedirect(req.getContextPath()+"/inventory"); // POST / redirect / GET
  }catch(SQLException e){getServletContext().log("Inventory DB error",e);req.setAttribute("error","Could not save. Check the database connection.");doGet(req,resp);}
   catch(IllegalArgumentException e){req.setAttribute("error",e.getMessage());req.setAttribute("enteredName",req.getParameter("name"));req.setAttribute("enteredPrice",req.getParameter("price"));doGet(req,resp);}
 }
}