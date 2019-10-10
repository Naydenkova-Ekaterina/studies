package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Map")
@NoArgsConstructor
@Getter
@Setter
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "first")
    private City first;

    @OneToOne
    @JoinColumn(name = "second")
    private City second;

    private double distance;

}
