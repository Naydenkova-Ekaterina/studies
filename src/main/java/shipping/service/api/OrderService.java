package shipping.service.api;

import shipping.dto.OrderDTO;
import shipping.exception.CustomServiceException;
import shipping.model.Order;

import java.util.List;

public interface OrderService {

    void addOrder(Order order) throws CustomServiceException;
    void updateOrder(Order order) throws CustomServiceException;
    List<Order> listOrders() throws CustomServiceException;
    Order getOrderById(int id) throws CustomServiceException;
    void removeOrder(int id) throws CustomServiceException;
    Order getOrderByWagon(String id) throws CustomServiceException;
    double countOrderWeight(Order order);

}
