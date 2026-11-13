package bw.study.codebank.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/calculate") public class CalculatorServlet extends HttpServlet {
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  try {double a=Double.parseDouble(req.getParameter("a")),b=Double.parseDouble(req.getParameter("b"));
   double result=switch(req.getParameter("operation")) {
    case "add"->a+b;case "subtract"->a-b;case "multiply"->a*b;
    case "divide"->{if(b==0)throw new IllegalArgumentException("Cannot divide by zero.");yield a/b;}
    default->throw new IllegalArgumentException("Unknown operation.");};
   if(!Double.isFinite(result))throw new IllegalArgumentException("Result is outside the supported range.");
   req.setAttribute("result",result);
  }catch(NumberFormatException ex){req.setAttribute("error","Please enter two valid numbers.");}
   catch(IllegalArgumentException ex){req.setAttribute("error",ex.getMessage());}
  req.getRequestDispatcher("/WEB-INF/views/calculator-result.jsp").forward(req,resp);
 }
}