package shipping.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;

@Entity
@Table(name = "City")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"wagonSet", "driverSet", "waypointSet", "routeList"})
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

    @ManyToMany(mappedBy = "cityList")
    private Set<Route> routeList = new HashSet<>();

}
