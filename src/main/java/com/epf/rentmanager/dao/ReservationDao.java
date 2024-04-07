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
	private static final String FIND_RESERVATION_QUERY = "SELECT * FROM Reservation WHERE id=?;";

	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String EDIT_RESERVATION = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";




		
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

	public void edit(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(EDIT_RESERVATION)) {

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.setLong(5,reservation.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public long delete(int id) throws DaoException, SQLException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_RESERVATION_QUERY);
			ps.setInt(1,id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);) {
			ps.setLong(1, clientId);

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

	public Reservation findResaById(long rent_id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_QUERY)) {

			ps.setLong(1,rent_id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int client_id = rs.getInt("client_id");
			int vehicle_id = rs.getInt("vehicle_id");
			LocalDate debut = rs.getDate("debut").toLocalDate();
			LocalDate fin = rs.getDate("fin").toLocalDate();
			return new Reservation((int) rent_id, client_id, vehicle_id, debut, fin);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
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


}

