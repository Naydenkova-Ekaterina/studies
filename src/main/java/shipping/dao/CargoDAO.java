package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.Cargo;

import java.util.List;

public interface CargoDAO {

     void addCargo(Cargo cargo) throws CustomDAOException;
     void update(Cargo cargo) throws CustomDAOException;
     List<Cargo> listCargoes() throws CustomDAOException;
     Cargo getCargo(int id) throws CustomDAOException;
     void removeCargo(int id) throws CustomDAOException;
}
