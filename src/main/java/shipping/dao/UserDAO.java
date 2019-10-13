package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.User;

import java.util.List;

public interface UserDAO {

    void addUser(User user) throws CustomDAOException;
    void update(User user) throws CustomDAOException;
    List<User> listUsers() throws CustomDAOException;
    User getUser(int id) throws CustomDAOException;
    void removeUser(int id) throws CustomDAOException;

}
