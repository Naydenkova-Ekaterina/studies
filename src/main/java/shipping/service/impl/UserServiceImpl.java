package shipping.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.UserDAO;
import shipping.dto.UserDTO;
import shipping.dto.converter.UserConverter;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.User;
import shipping.service.api.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    private ModelMapper modelMapper;

    private UserConverter userConverter;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void addUser(UserDTO user) throws CustomServiceException {
        try {
            userConverter = new UserConverter(modelMapper);
            String passwordEncoded = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(passwordEncoded);
            userDAO.addUser(userConverter.convertToEntity(user));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<UserDTO> listUser() throws CustomServiceException {
        try {
            userConverter = new UserConverter(modelMapper);
            return userDAO.listUsers().stream().map(user -> userConverter.convertToDto(user)).collect(Collectors.toList());
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public UserDTO getUserById(int id) throws CustomServiceException {
        try {
            userConverter = new UserConverter(modelMapper);
            return userConverter.convertToDto(userDAO.getUser(id));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public UserDTO findUserByEmail(String username) throws CustomServiceException {
        userConverter = new UserConverter(modelMapper);

        List<UserDTO> users = listUser();
        for (UserDTO user : users) {
            if (user.getEmail().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public org.springframework.security.core.userdetails.User getAuthenticatedUser() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
