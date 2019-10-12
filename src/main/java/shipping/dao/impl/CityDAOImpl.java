package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.CityDAO;
import shipping.exception.CustomDAOException;
import shipping.model.City;

import java.util.List;

@Repository
public class CityDAOImpl implements CityDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public void addCity(City city) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(city);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(City city) throws CustomDAOException{
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(city);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<City> listCities() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<City> cityList = session.createQuery("from City").list();
            return cityList;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public City getCity(int id) throws CustomDAOException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            City city = session.get(City.class, id);
            return city;
        } catch (Exception e) {
        throw new CustomDAOException(e);
        }
    }

    @Override
    public void removeCity(int id) throws CustomDAOException{
        try {
            Session session = sessionFactory.getCurrentSession();
            City city = session.get(City.class, id);
            if (null != city) {
                session.delete(city);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
