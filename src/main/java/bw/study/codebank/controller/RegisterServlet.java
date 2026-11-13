package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.util.regex.Pattern;
@WebServlet("/register") public class RegisterServlet extends HttpServlet {
 private static final Pattern EMAIL=Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req,resp);}
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  String name=req.getParameter("name"),email=req.getParameter("email");
  req.setAttribute("name",name);req.setAttribute("email",email);
  if(name==null||name.isBlank()||email==null||!EMAIL.matcher(email).matches()){
   req.setAttribute("error","Enter a name and a valid email address.");doGet(req,resp);return;
  }
  // In a real application a service and DAO would save the new member here.
  req.getSession().setAttribute("notice","Practice registration validated (not stored in a database).");
  resp.sendRedirect(req.getContextPath()+"/registered");
 }
}