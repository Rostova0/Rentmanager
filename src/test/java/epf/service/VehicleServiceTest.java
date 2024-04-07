package epf.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class VehicleServiceTest {
    private VehicleDao vehicleDaoMock;
    private VehicleService vehicleService;


    @BeforeEach
    public void setUp() {
        vehicleDaoMock = mock(VehicleDao.class);
        vehicleService = new VehicleService(vehicleDaoMock);
    }

    @Test
    public void testFindVehicleById() throws DaoException, ServiceException {
        long vehicleId = 1L;
        Vehicle mockVehicle = new Vehicle(1, "Toyota", "Camry", 2020);

        when(vehicleDaoMock.findById(vehicleId)).thenReturn(mockVehicle);

        try {
            Vehicle vehicle = vehicleService.findById(vehicleId);

            assertEquals(mockVehicle, vehicle);
        } catch (ServiceException e) {
            fail("ServiceException should not be thrown.");
        }
    }
}
