package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private ReservationService reservationService;
	
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(int id) throws ServiceException {
		try {
			List<Reservation> rentClient = reservationService.findResaByClientID(id);
			for(Reservation reservation : rentClient) {
				reservationService.delete((int) reservation.getId());
			}
			return clientDao.delete(id);
		} catch (DaoException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public Client findById(long id) throws ServiceException {
		if(id<0) {
			throw new ServiceException();
		}
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();

		}


	}

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public Client findByEmail(String mail) throws DaoException {
		try {
			return clientDao.findByEmail(mail);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public void edit(Client client) throws DaoException {
		try {
			clientDao.edit(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public List<Client> findByVehicleId(long vehicle_id) {
		try {
			return clientDao.findByVehicleId(vehicle_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Client> findByReservationClient(long rent_id) {
		try {
			return clientDao.findByReservationClient(rent_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
