package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.WagonDAO;
import shipping.exception.CustomDAOException;
import shipping.model.Wagon;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WagonDAOImpl implements WagonDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public void addWagon(Wagon wagon) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(wagon);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(Wagon wagon) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(wagon);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<Wagon> listWagons() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Wagon> wagonList = session.createQuery("from Wagon").list();
            return wagonList;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public Wagon getWagon(String id) throws CustomDAOException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Wagon wagon = session.get(Wagon.class, id);
            return wagon;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void removeWagon(String id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Wagon wagon = session.get(Wagon.class, id);
            if (null != wagon) {
                session.delete(wagon);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<String> getSuitableWagons(double requiredCapacity) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();

            Query query = session.createSQLQuery(" select w.id from shipping.Wagon w, shipping.My_Order o where w.status='serviceable' and w.capacity >= :requiredCapacity and o.wagon_id is null " );
            query.setParameter("requiredCapacity", requiredCapacity);
            List<String> ids = query.getResultList();

            return ids;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
