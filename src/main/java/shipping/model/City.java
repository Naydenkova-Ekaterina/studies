package shipping.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "City")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"wagonSet", "driverSet", "waypointSet"})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private Set<Wagon> wagonSet;

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private Set<Driver> driverSet;

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private Set<Waypoint> waypointSet;

}
