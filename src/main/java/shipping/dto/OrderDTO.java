package shipping.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"wagon", "driverSet", "waypointSet", "routeDTO"})
public class OrderDTO {

    private int id;

    private boolean isCompleted;

    private WagonDTO wagon;

    private List<CargoDTO> cargoDTOS;

    private RouteDTO routeDTO;

    @JsonIgnore
    private Set<DriverDto> driverSet;

}
