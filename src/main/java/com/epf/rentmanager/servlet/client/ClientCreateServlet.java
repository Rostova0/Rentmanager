package com.epf.rentmanager.servlet.client;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.except.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import static com.epf.rentmanager.utils.IOUtils.print;


@WebServlet("/client/create")
public class ClientCreateServlet extends HttpServlet {
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
        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        LocalDate naissance = LocalDate.parse(request.getParameter("naissance"), formatter);
        Client client = new Client(nom, prenom, email,naissance);

        boolean ageOk= Client.isLegal(client);
        boolean nameOk=Client.isNameOK(client);
        boolean mailFree=false;
        try {
            mailFree = Client.isMailFree(client, clientService);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        try {

            if(ageOk && nameOk && mailFree) {

                clientService.create(client);
                response.sendRedirect(request.getContextPath() + "/client");
            } if(!ageOk){
                response.getWriter().write("Erreur : vous devez etre majeur\n");

            } if(!nameOk){
                response.getWriter().write("Erreur : Nom/Prénom faisant moins de 3 caractères\n");

            } if(!mailFree) {
                response.getWriter().write("Erreur : e-mail indisponible\n");
            }

        }  catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}