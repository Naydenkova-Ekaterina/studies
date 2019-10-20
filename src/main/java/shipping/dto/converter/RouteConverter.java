package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.RouteDTO;
import shipping.model.Route;

public class RouteConverter {

    private ModelMapper modelMapper;

    public RouteConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RouteDTO convertToDto(Route route) {
        RouteDTO routeDTO = modelMapper.map(route, RouteDTO.class);
        return routeDTO;
    }

    public Route convertToEntity(RouteDTO routeDTO) {
        Route route = modelMapper.map(routeDTO, Route.class);
        return route;
    }

}
