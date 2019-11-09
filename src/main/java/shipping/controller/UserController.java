package shipping.controller;

import com.jcabi.aspects.Loggable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/user/add")
    public ResponseEntity<Void> addUser(@RequestBody UserDTO userDTO) {
        try {
            validate(userDTO);
            userService.addUser(userDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);

        }
    }

    public void validate(UserDTO userDTO) throws CustomServiceException{
        if (userDTO.getEmail().equals("")) {
            throw new CustomServiceException("Incorrect email.");
        } else if (userDTO.getPassword().equals("")) {
            throw new CustomServiceException("Incorrect password.");
        }
    }

}
