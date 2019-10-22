package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user) throws CustomServiceException;
    void updateUser(User user) throws CustomServiceException;
    List<User> listUser() throws CustomServiceException;
    User getUserById(int id) throws CustomServiceException;
    void removeUser(int id) throws CustomServiceException;
    User findUserByEmail(String email) throws CustomServiceException;
    org.springframework.security.core.userdetails.User getAuthenticatedUser();

    }
