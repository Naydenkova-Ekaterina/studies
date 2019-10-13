package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.DriverDto;
import shipping.model.Driver;

public class DriverConverter {

    private ModelMapper modelMapper;

    public DriverConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DriverDto convertToDto(Driver driver) {
        DriverDto driverDto = modelMapper.map(driver, DriverDto.class);
        return driverDto;
    }

    public Driver convertToEntity(DriverDto driverDto) {
        Driver driver = modelMapper.map(driverDto, Driver.class);
        return driver;
    }

}
