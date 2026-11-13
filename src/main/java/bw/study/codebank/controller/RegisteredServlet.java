package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/registered") public class RegisteredServlet extends HttpServlet {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  HttpSession session=req.getSession(false);String msg=session==null?null:(String)session.getAttribute("notice");
  if(session!=null)session.removeAttribute("notice");req.setAttribute("notice",msg);
  req.getRequestDispatcher("/WEB-INF/views/registered.jsp").forward(req,resp);
 }
}