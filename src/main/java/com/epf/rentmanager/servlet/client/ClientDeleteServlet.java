package com.epf.rentmanager.servlet.client;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/client/delete")
public class ClientDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            clientService.delete(Integer.parseInt(request.getParameter("id")));

        } catch (NumberFormatException | ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/client");
    }
}
