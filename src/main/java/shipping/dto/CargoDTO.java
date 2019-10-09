package shipping.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.CargoStatus;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CargoDTO {

    private int id;

    private String name;

    private double weight;

    private CargoStatus status;

    //private Set<WaypointDTO> waypointSet;

}
