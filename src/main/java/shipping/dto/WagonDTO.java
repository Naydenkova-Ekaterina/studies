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
public class WagonDTO {

    private String id;

    private String shiftSize;

    private double capacity;

    private String status;

    private CityDTO city;

    private Set<DriverDto> driverSet;

    private Set<OrderDTO> orderSet;

}
