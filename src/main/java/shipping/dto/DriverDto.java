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

    private String city_id;

    private CityDTO city;

    private String wagon_id;

    private WagonDTO wagon;

    private String order_id;

    private OrderDTO order;

    private UserDTO user;

    private Set<DriverShiftDTO> driverShiftSet;

}
