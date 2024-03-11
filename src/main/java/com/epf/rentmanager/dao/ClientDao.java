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

	public long delete(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);) {
			ps.setInt(1, client.getId());

			ps.execute();
			/*ResultSet e = ps.getResultSet();
			e.next();
			return e.getLong(0);
			*/
			return client.getId();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public Optional<Client> findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);) {
			ps.setLong(1, id);

			ps.execute();
			ResultSet e = ps.getResultSet();
			if (e.next()) {
				Client c = new Client(e.getInt(1), e.getString(2), e.getString(3), e.getString(4), e.getDate(5).toLocalDate());
				return Optional.of(c);
			} else {
				return Optional.empty();
			}

		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
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
}



