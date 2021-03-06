package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.WagonDTO;
import shipping.dto.rest.WagonDTOrest;
import shipping.model.Wagon;

import java.time.LocalTime;

public class WagonConverter {

    private ModelMapper modelMapper;

    public WagonConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WagonDTO convertToDto(Wagon wagon) {
        WagonDTO wagonDTO = modelMapper.map(wagon, WagonDTO.class);
        return wagonDTO;
    }

    public WagonDTOrest convertToDtoRest(Wagon wagon) {
        WagonDTOrest wagonDTOrest = modelMapper.map(wagon, WagonDTOrest.class);
        return wagonDTOrest;
    }

    public Wagon convertToEntity(WagonDTO wagonDTO) {
        Wagon wagon = modelMapper.map(wagonDTO, Wagon.class);
        wagon.setShiftSize(LocalTime.parse(wagonDTO.getShiftSize()));
        return wagon;
    }

}
