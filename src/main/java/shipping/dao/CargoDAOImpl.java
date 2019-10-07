package shipping.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.model.Cargo;

import java.util.List;

@Repository
public class CargoDAOImpl implements CargoDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addCargo(Cargo cargo) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(cargo);
    }

    @Override
    public void update(Cargo cargo) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(cargo);
    }

    @Override
    public List<Cargo> listCargoes() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Cargo> cargoesList = session.createQuery("from Cargo").list();
        return cargoesList;
    }

    @Override
    public Cargo getCargo(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Cargo cargo = session.get(Cargo.class, id);
        return cargo;
    }

    @Override
    public void removeCargo(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Cargo cargo = session.load(Cargo.class, id);
        if (null != cargo) {
            session.delete(cargo);
        }
    }
}
