package shipping.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import shipping.dao.WagonDAO;
import shipping.enums.WagonStatus;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Driver;
import shipping.model.Wagon;
import shipping.service.impl.WagonServiceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static  org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WagonServiceTest {

    @InjectMocks
    private WagonServiceImpl wagonService;

    @Mock
    private WagonDAO wagonDAO;

    private List<Wagon> wagonList = new ArrayList<>();
    private List<String> suitableWagons = new ArrayList<>();


    @Before
    public void init() {

        Wagon wagon = new Wagon();
        wagon.setId("ab12345");
        wagon.setShiftSize(LocalTime.MIDNIGHT);
        wagon.setCapacity(1000);
        wagon.setStatus(WagonStatus.serviceable);

        Wagon wagon2 = new Wagon();
        wagon2.setId("ab12346");
        wagon2.setShiftSize(LocalTime.MIDNIGHT);
        wagon2.setCapacity(1000);
        wagon2.setStatus(WagonStatus.faulty);

        Wagon wagon3 = new Wagon();
        wagon3.setId("ab12347");
        wagon3.setShiftSize(LocalTime.MIDNIGHT);
        wagon3.setCapacity(10);
        wagon3.setStatus(WagonStatus.serviceable);

        wagonList.add(wagon);
        wagonList.add(wagon2);
        wagonList.add(wagon3);

        suitableWagons.add(wagon.getId());

    }


    @Test(expected = CustomServiceException.class)
    public void removeWagonWithDriversTest() throws CustomServiceException, CustomDAOException {
        Wagon wagon = new Wagon();
        wagon.setId("ab12345");
        wagon.setShiftSize(LocalTime.MIDNIGHT);
        wagon.setCapacity(1000);
        wagon.setStatus(WagonStatus.serviceable);
        Driver driver = new Driver();
        driver.setId(1);
        Set<Driver> drivers = new HashSet<>();
        drivers.add(driver);
        wagon.setDriverSet(drivers);

        when(wagonDAO.getWagon("ab12345")).thenReturn(wagon);

        wagonService.removeWagon("ab12345");
    }

    @Test
    public void getSuitableWagons() throws CustomServiceException, CustomDAOException {
        when(wagonDAO.getSuitableWagons(500)).thenReturn(suitableWagons);
        when(wagonDAO.getWagon("ab12345")).thenReturn(wagonList.get(0));

        for (Wagon w: wagonService.getSuitableWagons(500)) {
            System.out.println(w.getId());
            assertEquals(WagonStatus.serviceable, w.getStatus());
            assertTrue(w.getCapacity() >= 500);
            assertEquals(null, w.getDriverSet());
        }


    }



}
