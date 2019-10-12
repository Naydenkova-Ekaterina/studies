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
@EqualsAndHashCode(exclude = {"driverSet", "orderSet", "city"})
public class WagonDTO {

    private String id;

    private String shiftSize;

    private double capacity;

    private String status;

    private String city_id;

    private CityDTO city;

    @JsonIgnore
    private Set<DriverDto> driverSet;

    @JsonIgnore
    private Set<OrderDTO> orderSet;

}
