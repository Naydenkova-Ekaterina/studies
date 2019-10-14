package shipping.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.OrderDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Order;
import shipping.service.api.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

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
}
