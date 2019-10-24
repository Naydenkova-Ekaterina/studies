package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shipping.dto.UserDTO;
import shipping.dto.converter.UserConverter;
import shipping.exception.CustomServiceException;
import shipping.service.api.UserService;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private UserConverter userConverter;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public void addUser(@RequestBody UserDTO userDTO) {
        try {
            userConverter = new UserConverter(modelMapper);
            System.out.println(userDTO.getEmail());
            userService.addUser(userConverter.convertToEntity(userDTO));

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
