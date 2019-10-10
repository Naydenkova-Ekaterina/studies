package shipping.model;

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
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Wagon> wagonSet;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Driver> driverSet;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Waypoint> waypointSet;

    /*@ManyToMany
    @JoinTable(name="Map",
            joinColumns=@JoinColumn(name="city1_id"),
            inverseJoinColumns=@JoinColumn(name="city2_id")
    )
    private Set<City> citySet1;

    @ManyToMany
    @JoinTable(name="Map",
            joinColumns=@JoinColumn(name="city2_id"),
            inverseJoinColumns=@JoinColumn(name="city1_id")
    )
    private Set<City> citySet2;

     */

}
