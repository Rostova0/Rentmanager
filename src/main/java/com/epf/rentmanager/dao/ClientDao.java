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

import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.except.DaoException;
import org.springframework.stereotype.Repository;


@Repository
public class ClientDao {

	private ClientDao() {
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String FIND_CLIENTS_MAIL = "SELECT id, nom, prenom, email, naissance FROM Client WHERE email=?;";
	private static final String FIND_CLIENT_BY_VEHICLES= "SELECT * FROM Client INNER JOIN Reservation ON Reservation.client_id=Client.id WHERE Reservation.vehicle_id=?;";
	private static final String FIND_CLIENT_BY_RESERVATION= "SELECT * FROM Client INNER JOIN Reservation ON Reservation.client_id=Client.id WHERE Reservation.id=?;";
	private static final String EDIT_CLIENT = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));

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

	public void edit(Client client) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(EDIT_CLIENT)) {


			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.setLong(5,client.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public long delete(int id) throws DaoException, SQLException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_CLIENT_QUERY)) {
			ps.setInt(1,id);
			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public Client findById(long id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstatement = connection.prepareStatement(FIND_CLIENT_QUERY)) {
			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String email = rs.getString("email");
			LocalDate date = rs.getDate("naissance").toLocalDate();
			return new Client((int) id, nom, prenom, email, date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	public Client findByEmail(String email) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_MAIL)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				LocalDate date = rs.getDate("naissance").toLocalDate();
				return new Client(id, nom, prenom, email, date);
			}
			else{
				ps.close();
				connection.close();
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();

		}

	}

	public List<Client> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);) {

			ps.execute();
			ResultSet e= ps.getResultSet();
			List<Client> lc=new ArrayList<>();
			while (e.next()){
				Client c = new Client(e.getInt(1), e.getString(2), e.getString(3), e.getString(4), e.getDate(5).toLocalDate());
				lc.add(c);
			}
			return lc;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public List<Client> findByVehicleId(long vehicle_id) throws DaoException {
		List<Client> clients = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstatement = connection.prepareStatement(FIND_CLIENT_BY_VEHICLES))
		{
			pstatement.setLong(1, vehicle_id);
			ResultSet resultSet = pstatement.executeQuery();
			while(resultSet.next())
				pstatement.setLong(1, vehicle_id);
			ResultSet rs = pstatement.executeQuery();
			while(rs.next())
				clients.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("email"),rs.getDate("naissance").toLocalDate()));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return clients;
	}

	public List<Client> findByReservationClient(long rent_id) throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_CLIENT_BY_RESERVATION)
		) {
			pstatement.setLong(1, rent_id);
			ResultSet resultSet = pstatement.executeQuery();
			while(resultSet.next())
				pstatement.setLong(1, rent_id);
			ResultSet rs = pstatement.executeQuery();
			while(rs.next())
				clients.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("email"),rs.getDate("naissance").toLocalDate()));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return clients;
	}
}



