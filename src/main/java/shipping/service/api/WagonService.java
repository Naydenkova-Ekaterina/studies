package shipping.service.api;

import shipping.dto.WagonDTO;
import shipping.dto.rest.WagonDTOrest;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Wagon;

import java.util.List;

public interface WagonService {

    void addWagon(WagonDTO wagon) throws CustomServiceException;
    void updateWagon(WagonDTO wagon) throws CustomServiceException;
    List<WagonDTO> listWagons() throws CustomServiceException;
    WagonDTO getWagonById(String id) throws CustomServiceException;
    void removeWagon(String id) throws CustomServiceException;
    List<WagonDTO> getSuitableWagons(int id) throws CustomServiceException;
    void setOrder(String idWagon, int idOrder) throws CustomServiceException;
    List<WagonDTOrest> listWagonDTOrest() throws CustomDAOException;

    }
