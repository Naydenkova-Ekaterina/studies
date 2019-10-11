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
public class OrderDTO {

    private int id;

    private boolean isCompleted;

    private WagonDTO wagon;

    private Set<DriverDto> driverSet;

    private Set<WaypointDTO> waypointSet;

}
