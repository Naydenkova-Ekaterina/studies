package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.CargoDTO;
import shipping.model.Cargo;

public class CargoConverter {

    private ModelMapper modelMapper;

    public CargoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CargoDTO convertToDto(Cargo cargo) {
        CargoDTO cargoDTO = modelMapper.map(cargo, CargoDTO.class);
        if (cargo.getOrder() != null) {
            cargoDTO.setOrderDTO_id(String.valueOf(cargo.getOrder().getId()));
        }
        return cargoDTO;
    }

    public Cargo convertToEntity(CargoDTO cityDTO) {
        Cargo cargo = modelMapper.map(cityDTO, Cargo.class);
        return cargo;
    }
}
