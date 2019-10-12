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
@EqualsAndHashCode(exclude = {"wagonSet", "driverSet", "waypointSet"})
public class CityDTO {

    private int id;

    private String name;

    @JsonIgnore
    private Set<WagonDTO> wagonSet;
    @JsonIgnore
    private Set<DriverDto> driverSet;
    @JsonIgnore
    private Set<WaypointDTO> waypointSet;

}
