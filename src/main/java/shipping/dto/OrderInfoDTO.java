package shipping.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderInfoDTO {

    private boolean isCompleted;

    private String wagon_id;

    private List<String> waypointNames;

    private List<String> cargoNames;

}
