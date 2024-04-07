package com.epf.rentmanager.servlet;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epf.rentmanager.utils.IOUtils.print;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ReservationService reservationService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Client> clientList;
		List<Vehicle> vehicleList;
		List<Reservation> reservationList;

		try {
			clientList=clientService.findAll();
			vehicleList=vehicleService.findAll();
			reservationList=reservationService.findAll();

			request.setAttribute("numberOfUsers", clientList.size());
			request.setAttribute("numberOfCars", vehicleList.size());
			request.setAttribute("numberOfReservations", reservationList.size());
		}catch (ServiceException e){
			print("Une erreur a été rencontrer");
		}


		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
