package shipping.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.dto.RouteDTO;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;

@Entity
@Table(name = "My_Order")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"wagon", "driverSet", "route", "cargoSet"})
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

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {MERGE})
    private Set<Cargo> cargoSet;

    @OneToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "route_id")
    private Route route;

}
