package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

import static com.epf.rentmanager.utils.IOUtils.print;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ReservationService reservationService;

	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	public long create(Client client) throws ServiceException {
		if (isNameLengthValid(client.getNom()) || isNameLengthValid(client.getPrenom())) {
			print("Le nom et le prénom doivent comporter au moins 3 caractères.");
		}
		if (!isClientAboveEighteen(client.getNaissance())) {
			print("Le client doit avoir au moins 18 ans pour être créé.");
		}
		if (isEmailAlreadyTaken(client.getEmail())) {
			print("L'adresse e-mail est déjà utilisée par un autre client.");
		}
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	private boolean isNameLengthValid(String name) {
		return name == null || name.length() < 3;
	}

	private boolean isEmailAlreadyTaken(String email) throws ServiceException {
		try {
			List<Client> existingClients = clientDao.findByEmail(email);
			return !existingClients.isEmpty();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	private boolean isClientAboveEighteen(LocalDate naiss) {
		LocalDate now = LocalDate.now();
		return naiss.plusYears(18).isBefore(now) || naiss.plusYears(18).isEqual(now);
	}


	public long delete(Client client) throws ServiceException {
		try {
			reservationService.deleteReservationsByClientId(client.getId());
			return clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			Optional<Client> c = clientDao.findById(id);
			if (c.isPresent()) {
				return c.get();
			}
			throw new ServiceException();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
