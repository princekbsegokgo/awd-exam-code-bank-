package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/grade") public class GradeServlet extends HttpServlet {
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  String student=req.getParameter("student");String raw=req.getParameter("mark");
  try { int mark=Integer.parseInt(raw);
   if(student==null||student.isBlank()||mark<0||mark>100)throw new IllegalArgumentException("Enter a name and a mark from 0 to 100.");
   req.setAttribute("student",student.trim());req.setAttribute("mark",mark);req.setAttribute("result",mark>=50?"PASS":"FAIL");
  } catch(IllegalArgumentException ex){req.setAttribute("error","Enter a valid name and a whole-number mark from 0 to 100.");}
  req.getRequestDispatcher("/WEB-INF/views/grade-result.jsp").forward(req,resp);
 }
}