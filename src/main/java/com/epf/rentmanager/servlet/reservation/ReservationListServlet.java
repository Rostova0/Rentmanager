package com.epf.rentmanager.servlet.reservation;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import static com.epf.rentmanager.utils.IOUtils.print;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Reservation> reservations = reservationService.findAll();
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            print("Une erreur a été rencontrer");
        }
    }
}
