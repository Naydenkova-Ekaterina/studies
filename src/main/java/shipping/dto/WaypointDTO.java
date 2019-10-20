package shipping.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.model.Cargo;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"city"})
public class WaypointDTO {

    private int id;

    private CityDTO city;

    private String type;

}
