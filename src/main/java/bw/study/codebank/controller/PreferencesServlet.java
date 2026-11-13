package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/preferences") public class PreferencesServlet extends HttpServlet {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  String theme="light";if(req.getCookies()!=null)for(Cookie c:req.getCookies())if("theme".equals(c.getName())&&"dark".equals(c.getValue()))theme="dark";
  req.setAttribute("theme",theme);req.getRequestDispatcher("/WEB-INF/views/preferences.jsp").forward(req,resp);
 }
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException {
  String value="dark".equals(req.getParameter("theme"))?"dark":"light";
  Cookie cookie=new Cookie("theme",value);cookie.setPath(req.getContextPath().isEmpty()?"/":req.getContextPath());cookie.setMaxAge(60*60*24*7);
  cookie.setHttpOnly(true);resp.addCookie(cookie);resp.sendRedirect(req.getContextPath()+"/preferences");
 }
}