package shipping.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shipping.enums.CargoStatus;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"src", "dst", "orderDTO"})
public class CargoDTO {

    private int id;

    private String name;

    private double weight;

    private String status;

    private OrderDTO orderDTO;

    private String orderDTO_id;

    private WaypointDTO src;

    private String src_id;

    private WaypointDTO dst;

    private String dst_id;
}
