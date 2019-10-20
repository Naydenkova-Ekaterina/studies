package shipping.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"cityDTOList"})
public class RouteDTO {

    private int id;

    private List<CityDTO> cityDTOList;

    private int currentCity_id;

}
