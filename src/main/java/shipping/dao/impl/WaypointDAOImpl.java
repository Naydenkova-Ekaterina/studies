package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.WaypointDAO;
import shipping.exception.CustomDAOException;
import shipping.model.Waypoint;

import java.util.List;

@Repository
public class WaypointDAOImpl implements WaypointDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public void addWaypint(Waypoint waypoint) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(waypoint);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(Waypoint waypoint) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(waypoint);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<Waypoint> listWaypoints() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Waypoint> waypointList = session.createQuery("from Waypoint").list();
            return waypointList;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public Waypoint getWaypoint(int id) throws CustomDAOException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Waypoint waypoint = session.get(Waypoint.class, id);
            return waypoint;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void removeWaypoint(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Waypoint waypoint = session.get(Waypoint.class, id);
            if (null != waypoint) {
                session.delete(waypoint);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
