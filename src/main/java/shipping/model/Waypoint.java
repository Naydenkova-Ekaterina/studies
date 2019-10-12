package shipping.model;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private WaypointType type;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    /*@ManyToOne()
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;*/

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "src", fetch = FetchType.EAGER)
    private Set<Cargo> srcCargoes;

    @OneToMany(mappedBy = "dst", fetch = FetchType.EAGER)
    private Set<Cargo> dstCargoes;

}
