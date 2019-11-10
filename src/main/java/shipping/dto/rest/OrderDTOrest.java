package shipping.dto.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderDTOrest {

    private int id;

    private boolean isCompleted;

}
