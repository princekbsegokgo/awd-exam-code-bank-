package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
/** Only for understanding HttpSession; NOT a secure production authentication system. */
@WebServlet("/login") public class LoginServlet extends HttpServlet {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);}
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  String username=req.getParameter("username"),password=req.getParameter("password");
  if("student".equals(username)&&"practice123".equals(password)){
   req.getSession(true).setAttribute("user",username);resp.sendRedirect(req.getContextPath()+"/dashboard");
  }else{req.setAttribute("error","Invalid practice login");doGet(req,resp);}
 }
}