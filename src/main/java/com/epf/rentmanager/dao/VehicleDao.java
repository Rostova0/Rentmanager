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
	private static final String FIND_VEHICLES_BY_CLIENT = "SELECT * FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id=Vehicle.id WHERE Reservation.client_id=?;";
	private static final String FIND_VEHICLE_BY_RENT= "SELECT * FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id=Vehicle.id WHERE Reservation.id=?;";
	private static final String EDIT_VEHICLE = "UPDATE Vehicle SET constructeur=?,modele=?, nb_places=? WHERE id=?;";



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

	public void edit(Vehicle vehicle) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(EDIT_VEHICLE)) {
			ps.setLong(1,vehicle.getId());
			ps.setString(2, vehicle.getConstructeur());
			ps.setString(3, vehicle.getModele());
			ps.setInt(4, vehicle.getNb_places());
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);

		}

	}

	public long delete(int id) throws DaoException, SQLException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			ps.setInt(1,id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public Vehicle findById(long id) throws DaoException {

		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstatement = connection.prepareStatement(FIND_VEHICLE_QUERY)) {

			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			String constructeur = rs.getString("constructeur");
			String modele = rs.getString("modele");

			int nb_places = rs.getInt("nb_places");
			return new Vehicle((int) id, constructeur,modele, nb_places);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
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

	public List<Vehicle> findByClientId(long client_id) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_VEHICLES_BY_CLIENT);
		) {
			pstatement.setLong(1, client_id);
			ResultSet resultSet = pstatement.executeQuery();
			while(resultSet.next())
				pstatement.setLong(1, client_id);
			ResultSet rs = pstatement.executeQuery();
			while(rs.next())
				vehicles.add(new Vehicle(rs.getInt("id"), rs.getString("constructeur"),rs.getString("modele"),rs.getInt("nb_places")));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return vehicles;
	}

	public List<Vehicle> findByReservationVehicle(long rent_id) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_BY_RENT);
		) {
			ps.setLong(1, rent_id);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next())
				ps.setLong(1, rent_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				vehicles.add(new Vehicle(rs.getInt("id"), rs.getString("constructeur"),rs.getString("modele"),rs.getInt("nb_places")));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return vehicles;
	}

}

