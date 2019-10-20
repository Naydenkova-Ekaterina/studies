package shipping.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;

@Entity
@Table(name = "Route")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "cityList")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade={MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Route_City",
            joinColumns = { @JoinColumn(name = "route_id") },
            inverseJoinColumns = { @JoinColumn(name = "city_id") }
    )
    private Set<City> cityList = new HashSet<>();

    private int currentCity_id;
}
