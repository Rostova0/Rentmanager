package com.epf.rentmanager.servlet.reservation;
import com.epf.rentmanager.except.DaoException;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epf.rentmanager.utils.IOUtils.print;

@WebServlet("rents/details")
public class ReservationDetailsServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp");
        try {

            int id = Integer.parseInt(request.getParameter("id"));
            final List<Client> clients = clientService.findByReservationClient(id);
            final List<Vehicle> vehicles = vehicleService.findByReservationVehicle(id);
            final Reservation rents = reservationService.findResaById(id);
            request.setAttribute("clients",clients);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("rents", rents);



        } catch (final Exception e) {
            print(e.getMessage());
        }
        dispatcher.forward(request, response);
    }
}
