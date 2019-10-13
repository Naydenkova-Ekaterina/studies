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
@EqualsAndHashCode(exclude = {"wagon", "driverSet", "waypointSet"})
public class OrderDTO {

    private int id;

    private boolean isCompleted;

    private WagonDTO wagon;

    @JsonIgnore
    private Set<DriverDto> driverSet;

    @JsonIgnore
    private Set<WaypointDTO> waypointSet;

}
