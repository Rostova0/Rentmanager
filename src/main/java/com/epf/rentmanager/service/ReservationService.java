package com.epf.rentmanager.service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

import static com.epf.rentmanager.utils.IOUtils.print;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao =reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        if (isVehicleReservedForMoreThanThirtyDays(reservation.getVehicle_id(), reservation.getDebut())) {
            print("La voiture ne peut pas être réservée pendant plus de 30 jours consécutifs sans pause.");
        }
        if (isVehicleReservedForMoreThanSevenDays(reservation.getClient_id(), reservation.getVehicle_id(), reservation.getDebut())) {
            print("The vehicle cannot be reserved for more than 7 consecutive days by the same user.");
        }

        if (isCarAlreadyReservedOnDate(reservation.getVehicle_id(), reservation.getDebut())) {
            print("La voiture est déjà réservée pour cette date.");
        }

        try {
            return reservationDao.create(reservation);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    private boolean isCarAlreadyReservedOnDate(long vehicleId, LocalDate date) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findByVehicleAndDate(vehicleId, date);
            return !reservations.isEmpty();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private boolean isVehicleReservedForMoreThanSevenDays(long clientId, long vehicleId, LocalDate startDate) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findByClientAndVehicle(clientId, vehicleId);

            int consecutiveDays = 0;
            for (Reservation r : reservations) {
                if (!r.getDebut().isAfter(startDate) && !r.getFin().isBefore(startDate)) {
                    consecutiveDays++;
                    if (consecutiveDays >= 7) {
                        return true;
                    }
                } else {
                    consecutiveDays = 0;
                }
            }

            return false;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
    private boolean isVehicleReservedForMoreThanThirtyDays(long vehicleId, LocalDate startDate) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findResaByVehicleId(vehicleId);

            int consecutiveDays = 0;
            for (Reservation r : reservations) {
                if (!r.getDebut().isAfter(startDate)) {
                    if (!r.getFin().isBefore(startDate)) {
                        consecutiveDays += (int) Math.min(30, ChronoUnit.DAYS.between(startDate, r.getFin()) + 1);
                        if (consecutiveDays >= 30) {
                            return true;
                        }
                    }
                } else {
                    consecutiveDays = 0;
                }
            }
            return false;
        } catch (DaoException e) {
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

    public void deleteReservationsByVehicleId(long vehicleId) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findResaByVehicleId(vehicleId);
            for (Reservation reservation : reservations) {
                reservationDao.delete(reservation);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteReservationsByClientId(long clientId) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findResaByClientId(clientId);
            for (Reservation reservation : reservations) {
                reservationDao.delete(reservation);
            }
        } catch (DaoException e) {
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
