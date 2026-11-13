package bw.study.codebank.controller;
import bw.study.codebank.model.Book;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;
import java.io.IOException;import java.util.*;import java.util.stream.Collectors;
@WebServlet("/search") public class SearchServlet extends HttpServlet {
 private final List<Book> books=List.of(new Book(1,"Networking Basics","M. Dube"),new Book(2,"Java Servlets","T. Molefe"),new Book(3,"Database Design","K. Moyo"));
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
  String q=Optional.ofNullable(req.getParameter("q")).orElse("").trim().toLowerCase(Locale.ROOT);
  List<Book> filtered=books.stream().filter(b->b.getTitle().toLowerCase(Locale.ROOT).contains(q)||b.getAuthor().toLowerCase(Locale.ROOT).contains(q)).collect(Collectors.toList());
  req.setAttribute("books",filtered);req.setAttribute("query",req.getParameter("q"));
  req.getRequestDispatcher("/WEB-INF/views/search.jsp").forward(req,resp);
 }
}