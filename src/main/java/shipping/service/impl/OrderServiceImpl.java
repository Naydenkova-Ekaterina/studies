package shipping.service.impl;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.CargoDAO;
import shipping.dao.OrderDAO;
import shipping.dao.RouteDAO;
import shipping.dto.*;
import shipping.dto.converter.CargoConverter;
import shipping.dto.converter.CityConverter;
import shipping.dto.converter.OrderConverter;
import shipping.dto.rest.OrderDTOrest;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.model.City;
import shipping.model.Order;
import shipping.service.api.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private OrderDAO orderDAO;

    private CargoDAO cargoDAO;

    private RouteDAO routeDAO;

    private ModelMapper modelMapper;

    private OrderConverter orderConverter;

    private CargoConverter cargoConverter;

    private CargoService cargoService;

    private CityConverter cityConverter;

    private RouteService routeService;

    private MqService mqService;

    @Autowired
    public void setMqService(MqService mqService) {
        this.mqService = mqService;
    }

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    public void setRouteDAO(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    @Override
    @Transactional
    public void addOrder(OrderDTO order) throws CustomServiceException {
        try {
            orderConverter = new OrderConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            cargoConverter = new CargoConverter(modelMapper);

            CargoDTO cargo = cargoService.getCargoById(Integer.valueOf(order.getCargoDTO_id()));

            int from = cityService.listCities().indexOf(cargo.getSrc().getCity());
            int to = cityService.listCities().indexOf(cargo.getDst().getCity());

            List<City> cities = routeService.getPath(from, to);
            String way = "";
            for (City c: cities) {
                way += c.getName() + " ";
            }
            logger.debug("WAY = " + way);

            order.setWay(way);

            Set<CargoDTO> cargoDTOS = new HashSet<>();
            cargoDTOS.add(cargo);

            order.setCargoSet(cargoDTOS);



            Order orderEntity = orderConverter.convertToEntity(order);

            for (Cargo item :orderEntity.getCargoSet()){
                item.setOrder(orderEntity);
                cargoDAO.update(item);
            }
       //     orderDAO.addOrder(orderConverter.convertToEntity(order));

//            for (Cargo item :orderEntity.getCargoSet()){
//                item.setOrder(orderEntity);
//                cargoDAO.update(item);
//            }

            mqService.sendMsg("New order was created");

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
    public void updateOrder(OrderDTO order) throws CustomServiceException {
        try {
            orderConverter = new OrderConverter(modelMapper);
            Order orderEntity = orderConverter.convertToEntity(order);

            orderDAO.update(orderConverter.convertToEntity(order));

            for (Cargo cargo :orderEntity.getCargoSet()){
                cargo.setOrder(orderEntity);
                cargoDAO.update(cargo);
            }
            mqService.sendMsg("An order with id = " + order.getId() + " was updated.");

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
    public void updateOrderAfterChangingRoute(OrderDTO order) throws CustomServiceException {
        try {
            orderConverter = new OrderConverter(modelMapper);
            Order orderEntity = orderConverter.convertToEntity(order);

            orderDAO.update(orderEntity);

            for (Cargo cargo :orderEntity.getCargoSet()){
                cargo.setOrder(orderEntity);
                cargoDAO.update(cargo);
            }
            mqService.sendMsg("An order with id = " + order.getId() + " was updated.");

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
    public List<OrderDTO> listOrders() throws CustomServiceException {
        try {
            orderConverter = new OrderConverter(modelMapper);
            return orderDAO.listOrders().stream().map(order -> orderConverter.convertToDto(order)).collect(Collectors.toList());
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(int id) throws CustomServiceException {
        try {
            orderConverter = new OrderConverter(modelMapper);
            return orderConverter.convertToDto(orderDAO.getOrder(id));
        } catch (CustomDAOException e) {
            e.printStackTrace();
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeOrder(int id) throws CustomServiceException {
        try {
            orderDAO.removeOrder(id);
            mqService.sendMsg("An order with id = " + id + " was deleted.");

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
    public OrderDTO getOrderByWagon(String id) throws CustomServiceException {
        orderConverter = new OrderConverter(modelMapper);
        for (OrderDTO order: listOrders() ) {
            if (order.getWagon().getId().equals(id)) return order;
        }
        return null;
    }

    @Override
    @Transactional
    public double countOrderWeight(OrderDTO order) throws CustomServiceException {
        orderConverter = new OrderConverter(modelMapper);

        double weight = 0;
        double maxWeight = 0;
        Order orderEntity = orderConverter.convertToEntity(order);

        Set<Cargo> cargoes = orderEntity.getCargoSet();
        List<CityDTO> cities = convertWayToList(order);

        for (CityDTO city: cities) {
            for (Cargo cargo: cargoes) {
                if (cargo.getSrc().getCity().getName().equals(city.getName())) {
                    weight += cargo.getWeight();
                    if (weight > maxWeight) maxWeight = weight;
                } else if (cargo.getDst().getCity().getName().equals(city.getName())) {
                    weight -= cargo.getWeight();
                }
            }
        }

        return maxWeight;
    }

    @Override
    @Transactional
    public List<CityDTO> convertWayToList(OrderDTO order) throws CustomServiceException {
        orderConverter = new OrderConverter(modelMapper);

        List<CityDTO> cities = new ArrayList<>();
        String way = order.getWay().trim();
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(way.split(" ")));
        for (String str: stringArrayList) {
            cities.add(cityService.getCityByName(str));
        }

        return cities;
    }

    @Override
    @Transactional
    public List<CargoDTO> getOrderCargoes(int id) throws CustomServiceException {
        orderConverter = new OrderConverter(modelMapper);
        cargoConverter = new CargoConverter(modelMapper);
        OrderDTO order = getOrderById(id);
        Set<CargoDTO> cargos = order.getCargoSet();
        List<CargoDTO> list = cargos.stream().collect(Collectors.toList());
        return list;
    }

    @Override
    @Transactional
    public List<DriverDto> getOrderDrivers(int id) throws CustomServiceException {
        orderConverter = new OrderConverter(modelMapper);

        OrderDTO orderDTO = getOrderById(id);
        List<DriverDto> list = orderDTO.getDriverSet();
        return list;
    }

    @Override
    @Transactional
    public String addCargoToExistingOrder(int idOrder, int idCargo) throws CustomServiceException {
        cityConverter = new CityConverter(modelMapper);

        OrderDTO order = getOrderById(idOrder);
        CargoDTO cargo = cargoService.getCargoById(idCargo);

        Set<CargoDTO> cargos = order.getCargoSet();

        order.setCargoSet(cargos);

        if (!convertWayToList(order).contains(cargo.getSrc().getCity())) {
            return "Make a new order for this cargo.";
        }

        String way = routeService.remakeRoute(order.getWay(), cargo);

        order.getCargoSet().add(cargo);
        order.setWay(way);

        updateOrderAfterChangingRoute(order);
        return "cargo was added";
    }

    @Override
    @Transactional
    public List<OrderDTOrest> listOrdersDTOrest() throws CustomDAOException {
        orderConverter = new OrderConverter(modelMapper);
        List<OrderDTOrest> orderDTOrests = new ArrayList<>();
        List<Order> orders = orderDAO.listOrders();

        for (Order order : orders) {
            OrderDTOrest orderDTOrest = orderConverter.convertToDtoRest(order);
            orderDTOrests.add(orderDTOrest);
        }
        return orderDTOrests;
    }


    }
