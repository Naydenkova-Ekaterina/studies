package shipping.service.impl;

import org.springframework.transaction.annotation.Transactional;
import shipping.dao.UserDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.User;
import shipping.service.api.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void addUser(User user) throws CustomServiceException {
        try {
            userDAO.addUser(user);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) throws CustomServiceException {
        try {
            userDAO.update(user);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<User> listUser() throws CustomServiceException {
        try {
            return userDAO.listUsers();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public User getUserById(int id) throws CustomServiceException {
        try {
            return userDAO.getUser(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeUser(int id) throws CustomServiceException {
        try {
            userDAO.removeUser(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
