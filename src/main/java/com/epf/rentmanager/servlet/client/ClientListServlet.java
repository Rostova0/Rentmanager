package com.epf.rentmanager.servlet.client;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import static com.epf.rentmanager.utils.IOUtils.*;

@WebServlet("/client")
public class ClientListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Client> clients = clientService.findAll();
            request.setAttribute("clients", clients);
            request.getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            print("Une erreur a été rencontrer");
        }
    }
}
