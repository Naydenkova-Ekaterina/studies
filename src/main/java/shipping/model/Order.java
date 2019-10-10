package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Order")
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<Driver> driverSet;

    @OneToMany(mappedBy = "order")
    private Set<Waypoint> waypointSet;

}
