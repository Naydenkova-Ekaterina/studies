package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.DriverDAO;
import shipping.exception.CustomDAOException;
import shipping.model.City;
import shipping.model.Driver;

import javax.persistence.Query;
import java.util.List;

@Repository
public class DriverDAOImpl implements DriverDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addDriver(Driver driver) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(driver);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(Driver driver) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(driver);

        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<Driver> listDrivers() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Driver> driverList = session.createQuery("from Driver").list();
            return driverList;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public Driver getDriver(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Driver driver = session.get(Driver.class, id);
            return driver;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void removeDriver(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Driver driver = session.get(Driver.class, id);
            if (null != driver) {
                session.delete(driver);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<Driver> getAvailableDrivers(City city) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();

            Query query = session.createQuery("FROM Driver WHERE order_id IS NULL AND city = :city");
            query.setParameter("city", city);

            List<Driver> driverList = query.getResultList();

            return driverList;

        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

}
