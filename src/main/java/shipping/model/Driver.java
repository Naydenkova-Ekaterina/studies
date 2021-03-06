package shipping.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.DriverStatus;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "Driver")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"city", "wagon", "order", "user", "driverShiftSet"})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private LocalTime workedHours;

    @Enumerated(value = EnumType.STRING)
    private DriverStatus status;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @OneToOne
//    @JoinColumn(name = "driverShift_id")
//    private DriverShift driverShift;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
