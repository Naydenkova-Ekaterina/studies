package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.Wagon;

import java.util.List;

public interface WagonService {

    void addWagon(Wagon wagon) throws CustomServiceException;
    void updateWagon(Wagon wagon) throws CustomServiceException;
    List<Wagon> listWagons() throws CustomServiceException;
    Wagon getWagonById(String id) throws CustomServiceException;
    void removeWagon(String id) throws CustomServiceException;
    List<Wagon> getSuitableWagons(double requiredCapacity) throws CustomServiceException;

}
