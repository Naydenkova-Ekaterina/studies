package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.Cargo;

import java.util.List;

public interface CargoService {

    void addCargo(Cargo cargo) throws CustomServiceException;
    void updateCargo(Cargo cargo) throws CustomServiceException;
    List<Cargo> listCargoes() throws CustomServiceException;
    Cargo getCargoById(int id) throws CustomServiceException;
    void removeCargo(int id) throws CustomServiceException;
    List<Cargo> cargoesForWaypoint(int id) throws CustomServiceException;

}
