package bw.study.farm.controller;

import bw.study.farm.model.Farmer;
import bw.study.farm.service.FarmService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/farmers")
public class FarmersServlet extends HttpServlet {
    private final FarmService service = new FarmService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String edit = request.getParameter("edit");
            if (edit != null && request.getAttribute("formId") == null) {
                Farmer farmer = service.getFarmer(FarmService.parseId(edit, "Farmer"));
                if (farmer == null) throw new IllegalArgumentException("Farmer not found.");
                request.setAttribute("formId", farmer.getFarmerId());
                request.setAttribute("formName", farmer.getFullName());
                request.setAttribute("formVillage", farmer.getVillage());
                request.setAttribute("formPhone", farmer.getPhone());
            }
            request.setAttribute("farmers", service.allFarmers());
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
        } catch (SQLException ex) {
            log("Could not load farmers", ex);
            request.setAttribute("error", "Database error. Check your MySQL connection.");
        }
        request.getRequestDispatcher("/WEB-INF/views/farmers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if ("save".equals(action)) {
                String id = request.getParameter("id");
                Integer farmerId = id == null || id.isBlank()
                        ? null : FarmService.parseId(id, "Farmer");
                service.saveFarmer(farmerId, request.getParameter("fullName"),
                        request.getParameter("village"), request.getParameter("phone"));
            } else if ("delete".equals(action)) {
                service.deleteFarmer(FarmService.parseId(request.getParameter("id"), "Farmer"));
            } else {
                throw new IllegalArgumentException("Unknown form action.");
            }
            response.sendRedirect(request.getContextPath() + "/farmers?success=1");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            preserveForm(request);
            doGet(request, response);
        } catch (SQLException ex) {
            log("Could not save farmer", ex);
            request.setAttribute("error", "Database error. Check the connection or constraints.");
            preserveForm(request);
            doGet(request, response);
        }
    }

    private void preserveForm(HttpServletRequest request) {
        if (!"save".equals(request.getParameter("action"))) return;
        String id = request.getParameter("id");
        if (id != null && id.matches("[0-9]+")) request.setAttribute("formId", id);
        request.setAttribute("formName", request.getParameter("fullName"));
        request.setAttribute("formVillage", request.getParameter("village"));
        request.setAttribute("formPhone", request.getParameter("phone"));
    }
}