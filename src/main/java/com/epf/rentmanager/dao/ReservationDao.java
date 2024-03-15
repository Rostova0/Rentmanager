package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.except.DaoException;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private ReservationDao() {}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, client_id,vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id,vehicle_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_BY_VEHICLE_AND_DATE_QUERY = "SELECT * FROM reservations WHERE vehicle_id = ? AND date = ?";
	private static final String FIND_BY_CLIENT_AND_VEHICLE_QUERY = "SELECT * FROM reservations WHERE client_id = ? AND vehicle_id = ?";


	public long create(Reservation reservation) throws DaoException {
		try (Connection connection=ConnectionManager.getConnection();
			 PreparedStatement ps= connection.prepareStatement(CREATE_RESERVATION_QUERY,Statement.RETURN_GENERATED_KEYS);){
			ps.setInt(1,reservation.getClient_id());
			ps.setInt(2,reservation.getVehicle_id());
			ps.setDate(3,Date.valueOf(reservation.getDebut()));
			ps.setDate(4,Date.valueOf(reservation.getFin()));

			ps.execute();
			ResultSet e=ps.getGeneratedKeys();
			if (e.next()) {
				return e.getLong(1);
			}
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	public long delete(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
			ps.setLong(1, reservation.getId());

			ps.execute();
			return reservation.getId();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);) {
			ps.setLong(1, clientId);

			/*ps.execute();

			 */
			ResultSet e = ps.executeQuery();
			List<Reservation> lr=new ArrayList<>();
				while (e.next()) {
					Reservation r = new Reservation(e.getInt(1),e.getInt(2), e.getInt(3), e.getDate(4).toLocalDate(), e.getDate(5).toLocalDate());
					lr.add(r);
				}
			return lr;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);) {
			ps.setLong(1, vehicleId);

			ResultSet e = ps.executeQuery();
			List<Reservation> lr=new ArrayList<>();
				while (e.next()){
					Reservation r = new Reservation(e.getInt(1),e.getInt(2),e.getInt(3),e.getDate(4).toLocalDate(),e.getDate(5).toLocalDate());
					lr.add(r);
				}
				return lr;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);) {

			ps.execute();
			ResultSet e= ps.getResultSet();
			List<Reservation> lr=new ArrayList<>();
			while (e.next()){
				Reservation r = new Reservation(e.getInt(1), e.getInt(2), e.getInt(3), e.getDate(4).toLocalDate(),e.getDate(5).toLocalDate());
				lr.add(r);
			}
			return lr;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public List<Reservation> findByVehicleAndDate(long vehicleId, LocalDate date) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_BY_VEHICLE_AND_DATE_QUERY)) {
			ps.setLong(1, vehicleId);
			ps.setDate(2, java.sql.Date.valueOf(date));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Reservation reservation = new Reservation(
							rs.getInt("id"),
							rs.getInt("vehicle_id"),
							rs.getInt("client_id"),
							rs.getDate("date").toLocalDate(),
							rs.getDate("end_date").toLocalDate()
					);
					reservations.add(reservation);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}

	public List<Reservation> findByClientAndVehicle(long clientId, long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_BY_CLIENT_AND_VEHICLE_QUERY)) {
			ps.setLong(1, clientId);
			ps.setLong(2, vehicleId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Reservation reservation = new Reservation(
							rs.getInt("client_id"),
							rs.getInt("vehicle_id"),
							rs.getDate("debut").toLocalDate(),
							rs.getDate("fin").toLocalDate()
					);
					reservations.add(reservation);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}
}

