package com.epf.rentmanager.servlet;

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
import com.epf.rentmanager.service.ReservationService;

import static com.epf.rentmanager.utils.IOUtils.print;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/reservation/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ReservationService reservationService = ReservationService.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            int clientId = Integer.parseInt(request.getParameter("clientId"));
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            LocalDate debut = LocalDate.parse(request.getParameter("debut"),formatter);
            LocalDate fin = LocalDate.parse(request.getParameter("fin"),formatter);

            Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);
            reservationService.create(reservation);

            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException | NumberFormatException e) {
            print("Une erreur a été rencontrer");
        }
    }
}
