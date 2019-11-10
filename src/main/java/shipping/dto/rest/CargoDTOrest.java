package shipping.dto.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CargoDTOrest {

    private int id;

    private String name;

    private double weight;

    private String status;

}
