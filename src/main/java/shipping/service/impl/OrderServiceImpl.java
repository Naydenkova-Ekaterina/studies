package shipping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.OrderDAO;
import shipping.dto.CargoDTO;
import shipping.dto.CityDTO;
import shipping.dto.OrderDTO;
import shipping.enums.WaypointType;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.model.Order;
import shipping.model.Waypoint;
import shipping.service.api.CargoService;
import shipping.service.api.OrderService;
import shipping.service.api.WaypointService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    @Autowired
    private WaypointService waypointService;

    @Autowired
    private CargoService cargoService;

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    @Transactional
    public void addOrder(Order order) throws CustomServiceException {
        try {
            orderDAO.addOrder(order);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateOrder(Order order) throws CustomServiceException {
        try {
            orderDAO.update(order);
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
    public double countOrderWeight(OrderDTO orderDTO) {
        double weight = 0;
        List<CargoDTO> cargoDTOS = orderDTO.getCargoDTOS();
        for (CityDTO cityDTO: orderDTO.getRouteDTO().getCityDTOList()) {
            for (CargoDTO cargo: cargoDTOS) {
                if (cargo.getSrc().getCity().getName().equals(cityDTO.getName())) {
                    weight += cargo.getWeight();
                } else if (cargo.getDst().getCity().getName().equals(cityDTO.getName())) {
                    weight -= cargo.getWeight();
                }
            }
        }
        return weight;
    }

}
