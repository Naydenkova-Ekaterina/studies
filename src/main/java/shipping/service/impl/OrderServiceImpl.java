package shipping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.CargoDAO;
import shipping.dao.OrderDAO;
import shipping.dao.RouteDAO;
import shipping.dao.impl.CargoDAOImpl;
import shipping.dto.CargoDTO;
import shipping.dto.CityDTO;
import shipping.dto.OrderDTO;
import shipping.enums.WaypointType;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.model.City;
import shipping.model.Order;
import shipping.model.Waypoint;
import shipping.service.api.*;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    private CargoDAO cargoDAO;

    private RouteDAO routeDAO;

    @Autowired
    private WaypointService waypointService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private CityService cityService;

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
    public void addOrder(Order order) throws CustomServiceException {
        try {
            orderDAO.addOrder(order);
            for (Cargo cargo :order.getCargoSet()){
                cargo.setOrder(order);
                cargoDAO.update(cargo);
            }
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateOrder(Order order) throws CustomServiceException {
        try {
            //routeDAO.addRoute(order.getRoute());
            orderDAO.update(order);

            for (Cargo cargo :order.getCargoSet()){
                cargo.setOrder(order);
                cargoDAO.update(cargo);
            }
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateOrderAfterChangingRoute(Order order) throws CustomServiceException {
        try {
            //routeDAO.addRoute(order.getRoute());
            orderDAO.update(order);

            for (Cargo cargo :order.getCargoSet()){
                cargo.setOrder(order);
                cargoDAO.update(cargo);
            }
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Order> listOrders() throws CustomServiceException {
        try {
            return orderDAO.listOrders();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Order getOrderById(int id) throws CustomServiceException {
        try {
            return orderDAO.getOrder(id);
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
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Order getOrderByWagon(String id) throws CustomServiceException {
        for (Order order: listOrders() ) {
            if (order.getWagon().getId().equals(id)) return order;
        }
        return null;
    }

    @Override
    @Transactional
    public double countOrderWeight(Order order) throws CustomServiceException {
        double weight = 0;
        double maxWeight = 0;

        Set<Cargo> cargoes = order.getCargoSet();
        List<City> cities = convertWayToList(order);

        for (City city: cities) {
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

    public List<City> convertWayToList(Order order) throws CustomServiceException {
        List<City> cities = new ArrayList<>();
        String way = order.getWay().trim();
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(way.split(" ")));
        for (String str: stringArrayList) {
            cities.add(cityService.getCityByName(str));
        }

        return cities;
    }

}
