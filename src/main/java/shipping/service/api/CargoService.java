package shipping.service.api;

import shipping.dto.CargoDTO;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;

import java.util.List;

public interface CargoService {

    void addCargo(CargoDTO cargo) throws CustomServiceException;
    void updateCargo(CargoDTO cargo) throws CustomServiceException;
    List<CargoDTO> listCargoes() throws CustomServiceException;
    CargoDTO getCargoById(int id) throws CustomServiceException;
    void removeCargo(int id) throws CustomServiceException;
    List<CargoDTO> cargoesForWaypoint(int id) throws CustomServiceException;
    List<CargoDTO> cargoesForSrcWaypoint(int id) throws CustomServiceException;
    List<CargoDTO> cargoesForDstWaypoint(int id) throws CustomServiceException;
    List<CargoDTO> listFreeCargoes() throws CustomServiceException;


}
