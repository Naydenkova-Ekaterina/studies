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
public class WaypointDTO {

    private int id;

    private CityDTO city;

    private CargoDTO cargo;

    private String type;

    private Set<OrderDTO> orderSet;

}
