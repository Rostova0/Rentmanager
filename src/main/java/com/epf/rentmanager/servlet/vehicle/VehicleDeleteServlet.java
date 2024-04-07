package com.epf.rentmanager.servlet.vehicle;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet{
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
            vehicleService.delete(Integer.parseInt(request.getParameter("id")));

        } catch (NumberFormatException | ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/cars");
    }
}
