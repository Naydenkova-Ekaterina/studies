package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.WagonStatus;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "Wagon")
@NoArgsConstructor
@Getter
@Setter
public class Wagon {

    @Id
    private String id;

    private LocalTime shiftSize;

    private double capacity;

    private WagonStatus status;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "wagon", fetch = FetchType.LAZY)
    private Set<Driver> driverSet;

    @OneToMany(mappedBy = "wagon", fetch = FetchType.LAZY)
    private Set<Order> orderSet;


}
