package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.OrderDAO;
import shipping.exception.CustomDAOException;
import shipping.model.Order;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public void addOrder(Order order) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(order);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(Order order) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(order);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<Order> listOrders() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Order> orderList = session.createQuery("from Order").list();
            return orderList;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public Order getOrder(int id) throws CustomDAOException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Order order = session.get(Order.class, id);
            return order;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }

    }

    @Override
    public void removeOrder(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Order order = session.get(Order.class, id);
            if (null != order) {
                session.delete(order);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
