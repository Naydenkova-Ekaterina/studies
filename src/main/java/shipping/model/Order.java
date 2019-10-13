package shipping.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "My_Order")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"wagon", "driverSet", "waypointSet"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Driver> driverSet;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Waypoint> waypointSet;

}
