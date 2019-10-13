package shipping.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"driver"})
public class UserDTO {

    private int id;

    private String email;

    private String password;

    private String userRole;

    private Set<DriverDto> driverSet;

}
