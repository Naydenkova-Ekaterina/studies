package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.DriverDto;
import shipping.dto.rest.DriverDTOrest;
import shipping.model.Driver;

import java.time.LocalTime;

public class DriverConverter {

    private ModelMapper modelMapper;

    public DriverConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DriverDto convertToDto(Driver driver) {
        DriverDto driverDto = modelMapper.map(driver, DriverDto.class);
        return driverDto;
    }

    public DriverDTOrest convertToDtoRest(Driver driver) {
        DriverDTOrest driverDTOrest = modelMapper.map(driver, DriverDTOrest.class);
        if (driver.getOrder() != null) {
            driverDTOrest.setOrder(String.valueOf(driver.getOrder().getId()));
        }
        return driverDTOrest;
    }

    public Driver convertToEntity(DriverDto driverDto) {
        Driver driver = modelMapper.map(driverDto, Driver.class);
        driver.setWorkedHours(LocalTime.parse(driverDto.getWorkedHours()));
        return driver;
    }

}
