package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.CargoDAO;
import shipping.exception.CustomDAOException;
import shipping.model.Cargo;

import java.util.List;

@Repository
public class CargoDAOImpl implements CargoDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) throws CustomDAOException {
        sessionFactory = sf;
    }

    @Override
    public void addCargo(Cargo cargo) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(cargo);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(Cargo cargo) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(cargo);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<Cargo> listCargoes() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Cargo> cargoesList = session.createQuery("from Cargo").list();
            return cargoesList;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public Cargo getCargo(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Cargo cargo = session.get(Cargo.class, id);
            return cargo;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void removeCargo(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Cargo cargo = session.load(Cargo.class, id);
            if (null != cargo) {
                session.delete(cargo);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
