package shipping.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DriverShiftDTO {

    private int id;

    private String shiftStart;

    private String workedHours;

    private DriverDto driver;

}
