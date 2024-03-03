package com.epf.rentmanager.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

    public class VehicleCreateServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            request.getRequestDispatcher("/WEB-INF/views/vehicle/create.jsp").forward(request, response);
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            VehicleService vehicleService = VehicleService.getInstance();
            try {
                String constructeur = request.getParameter("constructeur");
                String modele = request.getParameter("modele");
                int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));

                Vehicle vehicle = new Vehicle(constructeur, modele, nbPlaces);
                vehicleService.create(vehicle);

                response.sendRedirect(request.getContextPath() + "/vehicles");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/vehicle/create");
            } catch (ServiceException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/vehicle/create");
            }
        }
    }
