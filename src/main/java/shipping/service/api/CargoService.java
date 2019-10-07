package shipping.service.api;

import shipping.model.Cargo;

import java.util.List;

public interface CargoService {

    void addCargo(Cargo cargo);
    void updateCargo(Cargo cargo);
    List<Cargo> listCargoes();
    Cargo getCargoById(int id);
    void removeCargo(int id);

}
