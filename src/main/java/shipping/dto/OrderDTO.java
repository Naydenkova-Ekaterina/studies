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
@EqualsAndHashCode(exclude = {"wagon", "driverSet", "routeDTO", "cargoDTOS"})
public class OrderDTO {

    private int id;

    private boolean isCompleted;

    private WagonDTO wagon;

    private Set<CargoDTO> cargoDTOS;

    private String cargoDTO_id;

    //private RouteDTO routeDTO;

    private String way;

    @JsonIgnore
    private List<DriverDto> driverSet;

}
