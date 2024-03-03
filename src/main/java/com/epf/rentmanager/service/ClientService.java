package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;


public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(Client client) throws ServiceException{
		try {
			return clientDao.delete(client);
		}catch (DaoException e){
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
			}catch(DaoException e){
				throw new ServiceException(e.getMessage());
			}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	
}
