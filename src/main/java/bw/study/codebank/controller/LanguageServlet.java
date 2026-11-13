package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/language") public class LanguageServlet extends HttpServlet {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  String lang=req.getParameter("lang");if("en".equals(lang)||"tn".equals(lang))req.getSession().setAttribute("language",lang);
  req.getRequestDispatcher("/WEB-INF/views/language.jsp").forward(req,resp);
 }
}