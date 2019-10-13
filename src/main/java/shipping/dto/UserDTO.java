package shipping.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Set<DriverDto> driverSet;

}
