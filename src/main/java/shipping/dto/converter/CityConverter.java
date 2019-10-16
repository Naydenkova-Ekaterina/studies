package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.CityDTO;
import shipping.dto.DriverDto;
import shipping.model.City;
import shipping.model.Driver;

public class CityConverter {

    private ModelMapper modelMapper;

    public CityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CityDTO convertToDto(City city) {
        CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }

    public City convertToEntity(CityDTO cityDTO) {
        City city = modelMapper.map(cityDTO, City.class);
        return city;
    }
}
