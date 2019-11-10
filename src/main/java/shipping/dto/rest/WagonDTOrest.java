package shipping.dto.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WagonDTOrest {

    private String id;

    private String shiftSize;

    private double capacity;

    private String status;

}
