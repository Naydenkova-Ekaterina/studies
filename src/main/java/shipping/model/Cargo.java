package shipping.model;

import shipping.CargoStatus;

import javax.persistence.*;

@Entity
@Table(name = "Cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;

    private double weight;

    @Enumerated(value = EnumType.STRING)
    private CargoStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public CargoStatus getStatus() {
        return status;
    }

    public void setStatus(CargoStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "name = " + name + "weight = " + weight + "status = " + status;
    }
}
