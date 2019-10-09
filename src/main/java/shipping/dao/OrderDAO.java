package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.Order;

import java.util.List;

public interface OrderDAO {

    void addOrder(Order order) throws CustomDAOException;
    void update(Order order) throws CustomDAOException;
    List<Order> listOrders() throws CustomDAOException;
    Order getOrder(int id) throws CustomDAOException;
    void removeOrder(int id) throws CustomDAOException;

}
