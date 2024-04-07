package com.epf.rentmanager.servlet.vehicle;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/details")
public class VehicleDetailsServlet extends HttpServlet{
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
    private static final long serialVersionUID = 1L;
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {
            int id = Integer.parseInt(request.getParameter("id"));
            final Vehicle vehicles = vehicleService.findById(id);
            final List<Reservation> rents = reservationService.findResaByVehicleId(id);
            final List<Client> clients = clientService.findByVehicleId(id);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("rents", rents);
            request.setAttribute("clients", clients);
            request.setAttribute("rentCount", rents.size());
            request.setAttribute("clientCount", clients.size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp")
                .forward(request, response);

    }
}
