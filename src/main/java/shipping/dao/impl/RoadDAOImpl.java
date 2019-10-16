package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.RoadDAO;
import shipping.exception.CustomDAOException;
import shipping.model.Order;
import shipping.model.Road;

import java.util.List;

@Repository
public class RoadDAOImpl implements RoadDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public List<Road> listRoads() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Road> roads = session.createQuery("from Road").list();
            return roads;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
