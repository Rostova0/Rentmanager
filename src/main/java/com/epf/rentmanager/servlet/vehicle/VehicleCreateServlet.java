package com.epf.rentmanager.servlet.vehicle;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.except.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import static com.epf.rentmanager.utils.IOUtils.*;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nb_places= Integer.parseInt(request.getParameter("nb_places"));

            Vehicle vehicle = new Vehicle(constructeur, modele, nb_places);
            vehicleService.create(vehicle);

            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (ServiceException e) {
            print("Une erreur a été rencontrer");
        }
    }
}