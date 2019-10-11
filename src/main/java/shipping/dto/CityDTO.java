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
public class CityDTO {

    private int id;

    private String name;

    private Set<WagonDTO> wagonSet;

    private Set<DriverDto> driverSet;

    private Set<WaypointDTO> waypointSet;

}
