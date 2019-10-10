package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "DriverShift")
@NoArgsConstructor
@Getter
@Setter
public class DriverShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime shiftStart;

    private LocalTime workedHours;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

}
