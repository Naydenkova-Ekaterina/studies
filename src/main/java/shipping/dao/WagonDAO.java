package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.Wagon;

import java.util.List;

public interface WagonDAO {
    void addWagon(Wagon wagon) throws CustomDAOException;
    void update(Wagon wagon) throws CustomDAOException;
    List<Wagon> listWagons() throws CustomDAOException;
    Wagon getWagon(String id) throws CustomDAOException;
    void removeWagon(String id) throws CustomDAOException;
    List<String> getSuitableWagons(double requiredCapacity) throws CustomDAOException;

}
