package shipping.service.impl;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.CargoDAO;
import shipping.dto.CargoDTO;
import shipping.dto.converter.CargoConverter;
import shipping.dto.converter.OrderConverter;
import shipping.dto.converter.WaypointConverter;
import shipping.dto.rest.CargoDTOrest;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.model.Waypoint;
import shipping.service.api.CargoService;
import shipping.service.api.MqService;
import shipping.service.api.OrderService;
import shipping.service.api.WaypointService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements CargoService {

    private static final Logger logger = Logger.getLogger(CargoServiceImpl.class);

    private CargoDAO cargoDAO;

    private CargoConverter cargoConverter;

    private WaypointConverter waypointConverter;

    private OrderConverter orderConverter;

    private WaypointService waypointService;

    private OrderService orderService;

    private MqService mqService;

    @Autowired
    public void setMqService(MqService mqService) {
        this.mqService = mqService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setWaypointService(WaypointService waypointService) {
        this.waypointService = waypointService;
    }

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        cargoConverter = new CargoConverter(modelMapper);
    }

    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    @Override
    @Transactional
    public void addCargo(CargoDTO cargo) throws CustomServiceException {
        try {
            cargoConverter = new CargoConverter(modelMapper);
            waypointConverter = new WaypointConverter(modelMapper);

            Waypoint src = waypointService.getWaypointById(Integer.parseInt(cargo.getSrc_id()));
            cargo.setSrc(waypointConverter.convertToDto(src));

            Waypoint dst = waypointService.getWaypointById(Integer.parseInt(cargo.getDst_id()));
            cargo.setDst(waypointConverter.convertToDto(dst));

            cargoDAO.addCargo(cargoConverter.convertToEntity(cargo));

            mqService.sendMsg("New cargo was created");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        } catch (TimeoutException e) {
            logger.error("TimeoutException during MQ message sending: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException during MQ message sending: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateCargo(CargoDTO cargo) throws CustomServiceException {
        try {
            cargoConverter = new CargoConverter(modelMapper);
            waypointConverter = new WaypointConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);

            Waypoint src = waypointService.getWaypointById(Integer.parseInt(cargo.getSrc_id()));
            cargo.setSrc(waypointConverter.convertToDto(src));

            Waypoint dst = waypointService.getWaypointById(Integer.parseInt(cargo.getDst_id()));
            cargo.setDst(waypointConverter.convertToDto(dst));

            Cargo cargoEntity = cargoConverter.convertToEntity(cargo);

            if (!cargo.getOrderDTO_id().equals("")) {
                cargoEntity.setOrder(orderConverter.convertToEntity(orderService.getOrderById(Integer.parseInt(cargo.getOrderDTO_id()))));
            }

            cargoDAO.update(cargoEntity);

            mqService.sendMsg("A cargo with id = " + cargo.getId() + " was updated.");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        } catch (TimeoutException e) {
            logger.error("TimeoutException during MQ message sending: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException during MQ message sending: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public List<CargoDTO> listCargoes() throws CustomServiceException {
        try {
//            cargoConverter = new CargoConverter(modelMapper);
            return cargoDAO.listCargoes().stream().map(cargo -> cargoConverter.convertToDto(cargo)).collect(Collectors.toList());
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public CargoDTO getCargoById(int id) throws CustomServiceException {
        try {
            cargoConverter = new CargoConverter(modelMapper);

            return cargoConverter.convertToDto(cargoDAO.getCargo(id));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeCargo(int id) throws CustomServiceException {
        try {
            cargoDAO.removeCargo(id);
            mqService.sendMsg("A cargo with id = " + id + " was deleted.");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        } catch (TimeoutException e) {
            logger.error("TimeoutException during MQ message sending: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException during MQ message sending: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<CargoDTO> cargoesForWaypoint(int id) throws CustomServiceException {
        List<CargoDTO> result =new ArrayList<>();
        for (CargoDTO cargo: listCargoes()) {
            if (cargo.getDst().getId() == id || cargo.getSrc().getId() == id) result.add(cargo);
        }
        return result;
    }

    @Override
    @Transactional
    public List<CargoDTO> cargoesForSrcWaypoint(int id) throws CustomServiceException {
        List<CargoDTO> result =new ArrayList<>();
        for (CargoDTO cargo: listCargoes()) {
            if (cargo.getSrc().getId() == id) result.add(cargo);
        }
        return result;
    }

    @Override
    @Transactional
    public List<CargoDTO> cargoesForDstWaypoint(int id) throws CustomServiceException {
        List<CargoDTO> result =new ArrayList<>();
        for (CargoDTO cargo: listCargoes()) {
            if (cargo.getDst().getId() == id) result.add(cargo);
        }
        return result;
    }

    @Override
    @Transactional
    public List<CargoDTO> listFreeCargoes() throws CustomServiceException {
        cargoConverter = new CargoConverter(modelMapper);
        List<CargoDTO> result = new ArrayList<>();
        List<CargoDTO> cargos = listCargoes();
        for (CargoDTO cargo: cargos) {
            if (cargo.getOrderDTO_id() == null) {
                result.add(cargo);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public List<CargoDTOrest> listCargoesDTOrest() throws CustomDAOException {
        cargoConverter = new CargoConverter(modelMapper);
        List<CargoDTOrest> cargoDTOrests = new ArrayList<>();
        List<Cargo> cargoes = cargoDAO.listCargoes();

        for (Cargo cargo : cargoes) {
            CargoDTOrest cargoDTOrest = cargoConverter.convertToDtoRest(cargo);
            cargoDTOrests.add(cargoDTOrest);
        }
        return cargoDTOrests;
    }

    }
