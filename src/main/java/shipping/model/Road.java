package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Road")
@NoArgsConstructor
@Getter
@Setter
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "src")
    private City src;

    @OneToOne
    @JoinColumn(name = "dst")
    private City dst;

    private double distance;

}
