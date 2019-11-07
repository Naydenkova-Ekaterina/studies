package shipping.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import shipping.dao.CargoDAO;
import shipping.enums.CargoStatus;
import shipping.enums.WaypointType;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.model.City;
import shipping.model.Waypoint;
import shipping.service.impl.CargoServiceImpl;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import static  org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoServiceImpl cargoService;

    @Mock
    private CargoDAO cargoDAO;

    private List<Cargo> cargos = new ArrayList<>();


    @Before
    public void init(){
        City city1 = new City();
        city1.setId(1);
        city1.setName("Moscow");

        City city2 = new City();
        city2.setId(2);
        city2.setName("Saint-Petersburg");

        Waypoint src1 = new Waypoint();
        src1.setId(1);
        src1.setType(WaypointType.loading);
        src1.setCity(city1);

        Waypoint dst1 = new Waypoint();
        dst1.setId(2);
        dst1.setType(WaypointType.unloading);
        dst1.setCity(city2);

        Waypoint src2 = new Waypoint();
        src2.setId(3);
        src2.setType(WaypointType.loading);
        src2.setCity(city2);

        Waypoint dst2 = new Waypoint();
        dst2.setId(4);
        dst2.setType(WaypointType.unloading);
        dst2.setCity(city1);

        Cargo cargo1 = new Cargo();
        cargo1.setId(1);
        cargo1.setName("cargo1");
        cargo1.setStatus(CargoStatus.prepared);
        cargo1.setSrc(src1);
        cargo1.setDst(dst1);

        Cargo cargo2 = new Cargo();
        cargo2.setId(2);
        cargo2.setName("cargo2");
        cargo2.setStatus(CargoStatus.prepared);
        cargo2.setSrc(src2);
        cargo2.setDst(dst2);

        cargos.add(cargo1);
        cargos.add(cargo2);


    }

    @Test
    public void cargoesForSrcWaypointTest() throws CustomServiceException, CustomDAOException {

        when(cargoDAO.listCargoes()).thenReturn(cargos);

        for (Cargo cargo:cargoService.cargoesForSrcWaypoint(1)) {
            System.out.println(cargo.getName());
            assertEquals("Moscow", cargo.getSrc().getCity().getName());
        }

    }

    @Test
    public void cargoesForDstWaypointTest() throws CustomServiceException, CustomDAOException {

        when(cargoDAO.listCargoes()).thenReturn(cargos);

        for (Cargo cargo:cargoService.cargoesForDstWaypoint(4)) {
            System.out.println(cargo.getName());
            assertEquals("Moscow", cargo.getDst().getCity().getName());
        }

    }

    @Test
    public void cargoesForWaypointTest() throws CustomServiceException, CustomDAOException {

        when(cargoDAO.listCargoes()).thenReturn(cargos);

        for (Cargo cargo:cargoService.cargoesForWaypoint(1)) {
            System.out.println(cargo.getName());
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(cargo.getSrc().getCity().getName());
            arrayList.add(cargo.getDst().getCity().getName());
            assertTrue(arrayList.contains("Moscow"));
        }


    }

}
