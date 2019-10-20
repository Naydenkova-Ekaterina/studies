package shipping.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"waypoints"})
public class DriverInfoDTO {

    private int personalNumber;

    private String wagon_id;

    private List<Integer> codrivers;

    private String order_id;

    private List<WaypointDTO> waypoints;
}
