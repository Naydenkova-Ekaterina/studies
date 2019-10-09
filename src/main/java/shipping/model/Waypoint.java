package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.WaypointType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Waypoint")
@NoArgsConstructor
@Getter
@Setter
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne()
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    private WaypointType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Waypoint_Order", joinColumns = @JoinColumn(name = "waypoint_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orderSet;
}
