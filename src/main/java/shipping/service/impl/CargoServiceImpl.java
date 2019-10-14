package shipping.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.CargoDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.service.api.CargoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    private CargoDAO cargoDAO;

    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    @Override
    @Transactional
    public void addCargo(Cargo cargo) throws CustomServiceException {
        try {
            cargoDAO.addCargo(cargo);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateCargo(Cargo cargo) throws CustomServiceException {
        try {
            cargoDAO.update(cargo);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Cargo> listCargoes() throws CustomServiceException {
        try {
            return cargoDAO.listCargoes();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Cargo getCargoById(int id) throws CustomServiceException {
        try {
            return cargoDAO.getCargo(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeCargo(int id) throws CustomServiceException {
        try {
            cargoDAO.removeCargo(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Cargo> cargoesForWaypoint(int id) throws CustomServiceException {
        List<Cargo> result =new ArrayList<>();
        for (Cargo cargo: listCargoes()) {
            if (cargo.getDst().getId() == id || cargo.getSrc().getId() == id) result.add(cargo);
        }
        return result;
    }
}
