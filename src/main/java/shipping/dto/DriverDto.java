package shipping.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DriverDto {

    private int id;

    private String name;

    private String surname;

    private String status;

    private CityDTO city;

    private WagonDTO wagon;

    private OrderDTO order;

    private UserDTO user;

    private Set<DriverShiftDTO> driverShiftSet;

}
