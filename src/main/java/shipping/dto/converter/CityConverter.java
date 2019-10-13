package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.CityDTO;
import shipping.model.City;

public class CityConverter {

    private ModelMapper modelMapper;

    public CityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CityDTO convertToDto(City city) {
        CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }
}
