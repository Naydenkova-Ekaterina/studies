package shipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.UserRole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "User")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String password;

    private UserRole userRole;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Driver> driverSet;

}
