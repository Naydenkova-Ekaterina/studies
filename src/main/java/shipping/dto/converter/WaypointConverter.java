package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.WaypointDTO;
import shipping.model.Waypoint;

public class WaypointConverter {

    private ModelMapper modelMapper;

    public WaypointConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WaypointDTO convertToDto(Waypoint waypoint) {
        WaypointDTO waypointDTO = modelMapper.map(waypoint, WaypointDTO.class);
        return waypointDTO;
    }

    public Waypoint convertToEntity(WaypointDTO waypointDTO) {
        Waypoint waypoint = modelMapper.map(waypointDTO, Waypoint.class);
        return waypoint;
    }
}
