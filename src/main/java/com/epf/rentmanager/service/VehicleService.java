package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	
	
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			Optional<Vehicle> v=vehicleDao.findById(id);
			if(v.isPresent()){
				return v.get();
			}
			throw new ServiceException();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	/*public int count() throws ServiceException {
		try {
			return VehicleDao.getInstance().count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	 */
	
}
