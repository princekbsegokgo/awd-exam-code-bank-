package cse24_033.controller;

import cse24_033.model.Livestock;
import cse24_033.service.FarmService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet({"/animals", "/livestock"})
public class AnimalsServlet extends HttpServlet {
    private final FarmService service = new FarmService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String edit = request.getParameter("edit");
            if (edit != null && request.getAttribute("formAnimalId") == null) {
                Livestock a = service.getAnimal(FarmService.parseId(edit, "Animal"));
                if (a == null) throw new IllegalArgumentException("Animal not found.");
                request.setAttribute("formAnimalId", a.getAnimalId());
                request.setAttribute("formTag", a.getTagNumber());
                request.setAttribute("formSpecies", a.getSpecies());
                request.setAttribute("formBreed", a.getBreed());
                request.setAttribute("formSex", a.getSex());
                request.setAttribute("formBirthDate",
                        a.getDateOfBirth() == null ? "" : a.getDateOfBirth().toString());
                request.setAttribute("formStatus", a.getStatus());
                request.setAttribute("formFarmerId", a.getFarmerId());
            }
            request.setAttribute("farmers", service.allFarmers());
            request.setAttribute("animals", service.searchAnimals(request.getParameter("q")));
            request.setAttribute("query", request.getParameter("q"));
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
        } catch (SQLException ex) {
            log("Could not load animals", ex);
            request.setAttribute("error", "Database error. Check your MySQL connection.");
        }
        request.getRequestDispatcher("/WEB-INF/views/livestock.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if ("save".equals(action)) {
                String id = request.getParameter("id");
                Integer animalId = id == null || id.isBlank()
                        ? null : FarmService.parseId(id, "Animal");
                service.saveAnimal(animalId, request.getParameter("tagNumber"),
                        request.getParameter("species"), request.getParameter("breed"),
                        request.getParameter("sex"), request.getParameter("birthDate"),
                        request.getParameter("status"), request.getParameter("farmerId"));
            } else if ("delete".equals(action)) {
                service.deleteAnimal(FarmService.parseId(request.getParameter("id"), "Animal"));
            } else {
                throw new IllegalArgumentException("Unknown form action.");
            }
            response.sendRedirect(request.getContextPath() + "/animals?success=1");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            preserveForm(request);
            doGet(request, response);
        } catch (SQLException ex) {
            log("Could not save animals", ex);
            request.setAttribute("error", "Database error. Check your connection or duplicate tag.");
            preserveForm(request);
            doGet(request, response);
        }
    }

    private void preserveForm(HttpServletRequest request) {
        if (!"save".equals(request.getParameter("action"))) return;
        String id = request.getParameter("id");
        if (id != null && id.matches("[0-9]+")) request.setAttribute("formAnimalId", id);
        request.setAttribute("formTag", request.getParameter("tagNumber"));
        request.setAttribute("formSpecies", request.getParameter("species"));
        request.setAttribute("formBreed", request.getParameter("breed"));
        request.setAttribute("formSex", request.getParameter("sex"));
        request.setAttribute("formBirthDate", request.getParameter("birthDate"));
        request.setAttribute("formStatus", request.getParameter("status"));
        request.setAttribute("formFarmerId", request.getParameter("farmerId"));
    }
}
