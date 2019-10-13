package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import shipping.dao.UserDAO;
import shipping.exception.CustomDAOException;
import shipping.model.User;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public void addUser(User user) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(user);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(User user) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(user);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<User> listUsers() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<User> userList = session.createQuery("from User").list();
            return userList;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public User getUser(int id) throws CustomDAOException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            User user = session.get(User.class, id);
            return user;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }    }

    @Override
    public void removeUser(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, id);
            if (null != user) {
                session.delete(user);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }
}
