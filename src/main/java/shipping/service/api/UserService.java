package shipping.service.api;

import shipping.dto.UserDTO;
import shipping.exception.CustomServiceException;
import shipping.model.User;

import java.util.List;

public interface UserService {

    void addUser(UserDTO user) throws CustomServiceException;
   // void updateUser(User user) throws CustomServiceException;
    List<UserDTO> listUser() throws CustomServiceException;
    UserDTO getUserById(int id) throws CustomServiceException;
  //  void removeUser(int id) throws CustomServiceException;
    UserDTO findUserByEmail(String email) throws CustomServiceException;
    org.springframework.security.core.userdetails.User getAuthenticatedUser();

    }
