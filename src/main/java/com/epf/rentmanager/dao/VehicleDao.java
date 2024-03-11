package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.except.DaoException;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES(?, ?,?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle;";
	/*private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle";

	 */


	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY,Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());

			ps.execute();
			ResultSet e = ps.getGeneratedKeys();
			if (e.next()) {
				return e.getLong(1);
			}

		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);) {
			ps.setInt(1, vehicle.getId());
			ps.execute();
			return vehicle.getId();

		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public Optional<Vehicle> findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);) {
			ps.setLong(1, id);

			ps.execute();
			ResultSet e = ps.getResultSet();
			if (e.next()) {
				Vehicle v = new Vehicle(e.getInt(1), e.getString(2), e.getString(3), e.getInt(4));
				return Optional.of(v);
			} else {
				return Optional.empty();
			}

		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);) {
			ps.execute();
			ResultSet e= ps.getResultSet();
			List<Vehicle> lv=new ArrayList<>();
			while (e.next()){
				Vehicle v = new Vehicle(e.getInt(1), e.getString(2), e.getString(3), e.getInt(4));
				lv.add(v);
			}
			return lv;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	/*public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLES_QUERY);) {
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	 */

}

