package com.epf.rentmanager.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao =reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public long delete(Reservation reservation) throws ServiceException{
        try {
            return reservationDao.delete(reservation);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findResaByClientID(long id) throws ServiceException {
        try {
            List<Reservation> lr = reservationDao.findResaByClientId(id);
                return lr;
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            List<Reservation> lr = reservationDao.findResaByVehicleId(id);
            return lr;
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
