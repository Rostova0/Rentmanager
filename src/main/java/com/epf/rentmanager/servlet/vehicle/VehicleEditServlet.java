package com.epf.rentmanager.servlet.vehicle;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/edit")
public class VehicleEditServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    private VehicleService vehicleService;


    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            final Vehicle vehicles = vehicleService.findById(id);
            request.setAttribute("vehicles", vehicles);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp")
                .forward(request, response);

    }

    protected void doPost(HttpServletRequest   request,   HttpServletResponse response)
            throws ServletException,  IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Vehicle vehicle = new Vehicle();
        String constructeur = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int nb_place = Integer.parseInt(request.getParameter("seats"));
        vehicle.setId(id);
        vehicle.setConstructeur(constructeur);
        vehicle.setModele(modele);
        vehicle.setNb_places(nb_place);
        boolean nbPlaces = Vehicle.isNbPlacesOK(vehicle);

        try {
            if(nbPlaces) {
                vehicleService.edit(vehicle);
                response.sendRedirect(request.getContextPath() + "/cars");
            } if(!nbPlaces) {
                response.getWriter().write("Erreur : nombre de places du véhicules incorrecte\n");
            }

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }


    }
}
