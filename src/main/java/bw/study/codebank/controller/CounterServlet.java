package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/counter") public class CounterServlet extends HttpServlet {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  HttpSession session=req.getSession();Integer visits=(Integer)session.getAttribute("visits");visits=visits==null?1:visits+1;
  session.setAttribute("visits",visits);req.getRequestDispatcher("/WEB-INF/views/counter.jsp").forward(req,resp);
 }
}