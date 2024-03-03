package com.epf.rentmanager.servlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
    @WebServlet("/vehicles")
    public class VehicleListServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            VehicleService vehicleService = VehicleService.getInstance();
            try {
                List<Vehicle> vehicles = vehicleService.findAll();
                request.setAttribute("vehicles", vehicles);
                request.getRequestDispatcher("/WEB-INF/views/vehicle/list.jsp").forward(request, response);
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }