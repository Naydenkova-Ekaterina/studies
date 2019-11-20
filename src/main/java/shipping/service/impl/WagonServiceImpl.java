package shipping.service.impl;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import shipping.controller.MainController;
import shipping.dao.WagonDAO;
import shipping.dto.CityDTO;
import shipping.dto.OrderDTO;
import shipping.dto.WagonDTO;
import shipping.dto.converter.CityConverter;
import shipping.dto.converter.OrderConverter;
import shipping.dto.converter.WagonConverter;
import shipping.dto.rest.WagonDTOrest;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Order;
import shipping.model.Wagon;
import shipping.service.api.CityService;
import shipping.service.api.MqService;
import shipping.service.api.OrderService;
import shipping.service.api.WagonService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class WagonServiceImpl implements WagonService {

    private static final Logger logger = Logger.getLogger(WagonServiceImpl.class);

    private WagonDAO wagonDAO;

    private OrderConverter orderConverter;

    private WagonConverter wagonConverter;

    private ModelMapper modelMapper;

    private CityService cityService;

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
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        wagonConverter = new WagonConverter(modelMapper);

    }

    public void setWagonDAO(WagonDAO wagonDAO) {
        this.wagonDAO = wagonDAO;
    }

    @Override
    @Transactional
    public void addWagon(WagonDTO wagon) throws CustomServiceException {

        try {
            wagonConverter = new WagonConverter(modelMapper);
            CityDTO city = cityService.getCityById(Integer.valueOf(wagon.getCity_id()));
            wagon.setCity(city);

            Wagon checkWagon = wagonDAO.getWagon(wagon.getId());

            if (checkWagon != null) {
                throw new CustomServiceException("Selected id already exists");
            }

            wagonDAO.addWagon(wagonConverter.convertToEntity(wagon));

            logger.debug("New wagon with id = " + wagon.getId() + " was created.");

            mqService.sendMsg("New wagon was created");

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
    public void updateWagon(WagonDTO wagon) throws CustomServiceException {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            CityDTO city = cityService.getCityById(Integer.valueOf(wagon.getCity_id()));
            wagon.setCity(city);

            wagonDAO.update(wagonConverter.convertToEntity(wagon));
            logger.debug("Wagon with id = " + wagon.getId() + " was updated.");

            mqService.sendMsg("A wagon with id = " + wagon.getId() + " was updated.");

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
    public List<WagonDTO> listWagons() throws CustomServiceException {
        try {
           // wagonConverter = new WagonConverter(modelMapper);
            return wagonDAO.listWagons().stream().map(wagon -> wagonConverter.convertToDto(wagon)).collect(Collectors.toList());
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public WagonDTO getWagonById(String id) throws CustomServiceException {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            return wagonConverter.convertToDto(wagonDAO.getWagon(id));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeWagon(String id) throws CustomServiceException {
        try {
            Wagon wagon = wagonDAO.getWagon(id);

            if (wagon == null) {
                throw new CustomServiceException("This wagon doesn't exist.");
            } /*else if (!wagon.getOrder().isEmpty() ) {
                throw new CustomServiceException("Wagon can't be removed, because it has an order."); // need check !
            } */else if (!wagon.getDriverSet().isEmpty()) {
                throw new CustomServiceException("Wagon can't be removed, because it has drivers."); // need check !
            }
            wagonDAO.removeWagon(id);
            logger.debug("Wagon with id = " + wagon.getId() + " was removed.");

            mqService.sendMsg("A driver with id = " + id + " was deleted.");

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
    public List<WagonDTO> getSuitableWagons(int id) throws CustomServiceException {
        try {
//            wagonConverter = new WagonConverter(modelMapper);

            OrderDTO order = orderService.getOrderById(id);
            double requiredCapacity = orderService.countOrderWeight(order);
            List<String> ids = wagonDAO.getSuitableWagons(requiredCapacity);
            List<Wagon> suitableWagons = new ArrayList<>();
            for (String item: ids) {
                suitableWagons.add(wagonDAO.getWagon(item));
            }
            logger.debug("All suitable wagons were found.");

            return suitableWagons.stream().map(wagon -> wagonConverter.convertToDto(wagon)).collect(Collectors.toList());
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void setOrder(String idWagon, int idOrder) throws CustomServiceException {
        //wagonConverter = new WagonConverter(modelMapper);
        orderConverter = new OrderConverter(modelMapper);
        Order order = orderConverter.convertToEntity(orderService.getOrderById(idOrder));
        Wagon wagon = wagonConverter.convertToEntity(getWagonById(idWagon));

        order.setWagon(wagon);
        orderService.updateOrder(orderConverter.convertToDto(order));
    }

    @Override
    @Transactional
    public List<WagonDTOrest> listWagonDTOrest() throws CustomDAOException {
        wagonConverter = new WagonConverter(modelMapper);
        List<WagonDTOrest> wagonDTOrests = new ArrayList<>();
        List<Wagon> wagons = wagonDAO.listWagons();

        for (Wagon wagon : wagons) {
            WagonDTOrest wagonDTOrest = wagonConverter.convertToDtoRest(wagon);
            wagonDTOrests.add(wagonDTOrest);
        }
        return wagonDTOrests;
    }

    }
