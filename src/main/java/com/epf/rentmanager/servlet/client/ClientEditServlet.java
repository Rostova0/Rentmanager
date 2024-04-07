package com.epf.rentmanager.servlet.client;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
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

@WebServlet("/client/edit")
public class ClientEditServlet extends HttpServlet {

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
            int id = Integer.parseInt(request.getParameter("id"));
            final Client clients = clientService.findById(id);
            request.setAttribute("clients", clients);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/users/edit.jsp")
                .forward(request, response);

    }

    protected void doPost(HttpServletRequest   request,   HttpServletResponse response)
            throws ServletException,  IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Client client = new Client();
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String email = request.getParameter("email");
        LocalDate naissance = LocalDate.parse(request.getParameter("naissance"));
        client.setId(id);
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setNaissance(naissance);
        boolean ageLegal = Client.isLegal(client);
        boolean nameCharacter = Client.isNameOK(client);


        try {
            if(ageLegal && nameCharacter) {
                clientService.edit(client);
                response.sendRedirect(request.getContextPath() + "/client");
            } if(!ageLegal){
                response.getWriter().write("Erreur : vous devez etre majeur\n");
            } if(!nameCharacter){
                response.getWriter().write("Erreur : Nom/Prénom faisant moins de 3 caractères\n");
            }

        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
