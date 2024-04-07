package com.epf.rentmanager.servlet.reservation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import static com.epf.rentmanager.utils.IOUtils.print;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/reservation/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        LocalDate debut = LocalDate.parse(request.getParameter("debut"),formatter);
        LocalDate fin = LocalDate.parse(request.getParameter("fin"),formatter);
        Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);

        boolean resalimit= Reservation.isCarNotRentUnder7days(reservation);
        boolean resadate=false;

        try {
            resadate= Reservation.isNotTheSameDay(reservation,reservationService);
        }catch (ServiceException e){
            throw new RuntimeException(e);
        }
        try {
            if(resalimit && resadate) {
                reservationService.create(reservation);
                response.sendRedirect(request.getContextPath() + "/rents");
            }

            if(!resalimit) {
                response.getWriter().write("Erreur : réservation dépasse 7 jours\n");
            }
            if(!resadate) {
                response.getWriter().write("Erreur : dates deja prises\n");
            }
        } catch (ServiceException | NumberFormatException e) {
            print("Une erreur a été rencontrer");
        }
    }
}
