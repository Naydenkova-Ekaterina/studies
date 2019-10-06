package shipping.dao;

import shipping.model.Cargo;

import java.util.List;

public interface CargoDAO {

     void addCargo(Cargo cargo);
     void update(Cargo cargo);
     List<Cargo> listCargoes();
     Cargo getCargo(int id);
     void removeCargo(int id);
}
