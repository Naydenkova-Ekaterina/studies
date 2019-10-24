package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.UserDTO;
import shipping.model.User;

public class UserConverter {

    private ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
}
