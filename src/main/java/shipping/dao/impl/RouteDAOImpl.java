package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.RouteDAO;
import shipping.exception.CustomDAOException;
import shipping.model.Route;

import java.util.List;

@Repository
public class RouteDAOImpl implements RouteDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public void addRoute(Route route) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(route);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(Route route) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(route);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<Route> listRoutes() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Route> routes = session.createQuery("from Route").list();
            return routes;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public Route getRoute(int id) throws CustomDAOException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Route route = session.get(Route.class, id);
            return route;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void removeRoute(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Route route = session.get(Route.class, id);
            if (null != route) {
                session.delete(route);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
