package com.epf.rentmanager.servlet;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.except.ServiceException;

import static com.epf.rentmanager.utils.IOUtils.print;


@WebServlet("/client/create")
public class ClientCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClientService clientService = ClientService.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            LocalDate naissance = LocalDate.parse(request.getParameter("naissance"), formatter);

            Client client = new Client(nom, prenom, email,naissance);
            clientService.create(client);

            response.sendRedirect(request.getContextPath() + "/client");
        } catch (ServiceException e) {
             print("Une erreur a été rencontrer");
        }
    }
}