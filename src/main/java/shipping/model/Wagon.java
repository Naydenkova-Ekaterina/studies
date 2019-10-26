package shipping.model;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(exclude = {"driverSet", "orderSet", "city"})
public class Wagon {

    @Id
    private String id;

    private LocalTime shiftSize;

    private double capacity;

    @Enumerated(value = EnumType.STRING)
    private WagonStatus status;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "wagon", fetch = FetchType.EAGER)
    private Set<Driver> driverSet;

//    @OneToMany(mappedBy = "wagon", fetch = FetchType.EAGER)
//    private Order order;


}
