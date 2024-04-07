package epf.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import com.epf.rentmanager.service.ClientService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
public class ClientServiceTest {
    private ClientDao clientDaoMock;
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        clientDaoMock = mock(ClientDao.class);
        clientService = new ClientService(clientDaoMock);
    }

    @Test
    public void testCreateClient() throws DaoException, ServiceException {

        long generatedClientId = 1L;
        when(clientDaoMock.create(Mockito.any(Client.class))).thenReturn(generatedClientId);

        Client client = new Client("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));

        long clientId = clientService.create(client);

        assertEquals(generatedClientId, clientId);
    }
}
