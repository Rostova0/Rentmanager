package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

	@Autowired
	private VehicleDao vehicleDao;

	@Autowired
	private ReservationService reservationService;
	
	public VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public void edit(Vehicle vehicle) throws ServiceException {
		try {
			vehicleDao.edit(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public long delete(int id) throws ServiceException {
		try {
			List<Reservation> rentVehicle = reservationService.findResaByVehicleId(id);
			for(Reservation reservation : rentVehicle) {
				reservationService.delete((int) reservation.getId());
			}
			return vehicleDao.delete(id);
		} catch (DaoException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public Vehicle findById(long id) throws ServiceException {
		if(id<0) {
			throw new ServiceException();

		}
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Vehicle> findByClientId(long client_id) throws ServiceException {
		if(client_id<0) {
			throw new ServiceException();

		}
		try {
			return vehicleDao.findByClientId(client_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Vehicle> findByReservationVehicle(long rent_id) throws ServiceException {
		try {
			return vehicleDao.findByReservationVehicle(rent_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
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
