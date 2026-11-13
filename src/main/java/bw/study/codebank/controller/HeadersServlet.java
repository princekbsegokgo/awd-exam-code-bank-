package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/headers") public class HeadersServlet extends HttpServlet {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  req.setAttribute("method",req.getMethod());req.setAttribute("agent",req.getHeader("User-Agent"));
  resp.setHeader("Cache-Control","no-store");req.getRequestDispatcher("/WEB-INF/views/headers.jsp").forward(req,resp);
 }
}