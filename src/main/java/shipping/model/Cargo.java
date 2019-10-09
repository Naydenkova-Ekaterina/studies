package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.CargoStatus;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Cargo")
@NoArgsConstructor
@Getter
@Setter
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double weight;

    @Enumerated(value = EnumType.STRING)
    private CargoStatus status;

    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    private Set<Waypoint> waypointSet;
}
