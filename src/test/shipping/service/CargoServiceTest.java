package shipping.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shipping.dao.CargoDAO;
import shipping.dto.CargoDTO;
import shipping.dto.CityDTO;
import shipping.dto.WaypointDTO;
import shipping.dto.converter.CargoConverter;
import shipping.enums.CargoStatus;
import shipping.enums.WaypointType;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.model.City;
import shipping.model.Waypoint;
import shipping.service.impl.CargoServiceImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static  org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoServiceImpl cargoService;

    @Mock
    private CargoDAO cargoDAO;

    private List<CargoDTO> cargoDTOS = new ArrayList<>();

    private List<Cargo> cargoes = new ArrayList<>();

    private CargoDTO cargoDTO1;
    private CargoDTO cargoDTO2;
    private Cargo cargo1;
    private Cargo cargo2;

    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private CargoConverter cargoConverter = new CargoConverter(modelMapper);

    @Before
    public void initEntity() {
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

        cargo1 = new Cargo();
        cargo1.setId(1);
        cargo1.setName("cargo1");
        cargo1.setStatus(CargoStatus.prepared);
        cargo1.setSrc(src1);
        cargo1.setDst(dst1);

        cargo2 = new Cargo();
        cargo2.setId(2);
        cargo2.setName("cargo2");
        cargo2.setStatus(CargoStatus.prepared);
        cargo2.setSrc(src2);
        cargo2.setDst(dst2);

        cargoes.add(cargo1);
        cargoes.add(cargo2);
    }

    @Before
    public void initDto(){
        CityDTO city1 = new CityDTO();
        city1.setId(1);
        city1.setName("Moscow");

        CityDTO city2 = new CityDTO();
        city2.setId(2);
        city2.setName("Saint-Petersburg");

        WaypointDTO src1 = new WaypointDTO();
        src1.setId(1);
        src1.setType(String.valueOf(WaypointType.loading));
        src1.setCity(city1);

        WaypointDTO dst1 = new WaypointDTO();
        dst1.setId(2);
        dst1.setType(String.valueOf(WaypointType.unloading));
        dst1.setCity(city2);

        WaypointDTO src2 = new WaypointDTO();
        src2.setId(3);
        src2.setType(String.valueOf(WaypointType.loading));
        src2.setCity(city2);

        WaypointDTO dst2 = new WaypointDTO();
        dst2.setId(4);
        dst2.setType(String.valueOf(WaypointType.unloading));
        dst2.setCity(city1);

        cargoDTO1 = new CargoDTO();
        cargoDTO1.setId(1);
        cargoDTO1.setName("cargo1");
        cargoDTO1.setStatus(String.valueOf(CargoStatus.prepared));
        cargoDTO1.setSrc(src1);
        cargoDTO1.setDst(dst1);

        cargoDTO2 = new CargoDTO();
        cargoDTO2.setId(2);
        cargoDTO2.setName("cargo2");
        cargoDTO2.setStatus(String.valueOf(CargoStatus.prepared));
        cargoDTO2.setSrc(src2);
        cargoDTO2.setDst(dst2);

        cargoDTOS.add(cargoDTO1);
        cargoDTOS.add(cargoDTO2);


    }

    @Test
    public void cargoesListTest() throws CustomDAOException, CustomServiceException {

        when(cargoConverter.convertToDto(any(Cargo.class))).thenReturn(cargoDTO1);

        when(cargoDAO.listCargoes()).thenReturn(cargoes);

        cargoService.listCargoes();
        verify(cargoDAO).listCargoes();

    }


    @Test
    public void cargoesForSrcWaypointTest() throws CustomServiceException, CustomDAOException {

        when(cargoConverter.convertToDto(any(Cargo.class))).thenReturn(cargoDTO1);

        when(cargoDAO.listCargoes()).thenReturn(cargoes);

        for (CargoDTO cargo:cargoService.cargoesForSrcWaypoint(1)) {
            assertEquals("Moscow", cargo.getSrc().getCity().getName());
        }

    }

    @Test
    public void cargoesForDstWaypointTest() throws CustomServiceException, CustomDAOException {

        when(cargoConverter.convertToDto(any(Cargo.class))).thenReturn(cargoDTO2);

        when(cargoDAO.listCargoes()).thenReturn(cargoes);

        for (CargoDTO cargo:cargoService.cargoesForDstWaypoint(4)) {
            assertEquals("Moscow", cargo.getDst().getCity().getName());
        }

    }

    @Test
    public void cargoesForWaypointTest() throws CustomServiceException, CustomDAOException {

        when(cargoConverter.convertToDto(any(Cargo.class))).thenReturn(cargoDTO1);

        when(cargoDAO.listCargoes()).thenReturn(cargoes);

        for (CargoDTO cargo:cargoService.cargoesForWaypoint(1)) {
            System.out.println(cargo.getName());
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(cargo.getSrc().getCity().getName());
            arrayList.add(cargo.getDst().getCity().getName());
            assertTrue(arrayList.contains("Moscow"));
        }
    }

    @Test
    public void listFreeCargoesTest() throws CustomDAOException, CustomServiceException {
        when(cargoConverter.convertToDto(any(Cargo.class))).thenReturn(cargoDTO1);

        when(cargoDAO.listCargoes()).thenReturn(cargoes);

        List<CargoDTO> cargoDTOS = cargoService.listCargoes();

        assertTrue(cargoDTOS.get(0).getOrderDTO_id() == null);
    }

}
