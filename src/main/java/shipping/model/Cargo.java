package shipping.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.CargoStatus;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "Cargo")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"src", "dst", "route"})
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double weight;

    @Enumerated(value = EnumType.STRING)
    private CargoStatus status;

    @ManyToOne(cascade = {MERGE, PERSIST, DETACH, REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "src_id")
    private Waypoint src;

    @ManyToOne
    @JoinColumn(name = "dst_id")
    private Waypoint dst;

}
