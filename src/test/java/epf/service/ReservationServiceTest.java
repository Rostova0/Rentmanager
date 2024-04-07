package epf.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class ReservationServiceTest {
    private ReservationDao reservationDaoMock;
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        reservationDaoMock = mock(ReservationDao.class);
        reservationService = new ReservationService(reservationDaoMock);
    }

    @Test
    public void testFindAllReservations() throws DaoException, ServiceException {

        List<Reservation> mockReservations = new ArrayList<>();
        mockReservations.add(new Reservation(1, 1, 1, LocalDate.now(), LocalDate.now()));
        mockReservations.add(new Reservation(2, 2, 2, LocalDate.now(), LocalDate.now()));

        when(reservationDaoMock.findAll()).thenReturn(mockReservations);

        List<Reservation> reservations = reservationService.findAll();

        assertEquals(mockReservations.size(), reservations.size());
        for (int i = 0; i < mockReservations.size(); i++) {
            assertEquals(mockReservations.get(i), reservations.get(i));
        }
    }
}
