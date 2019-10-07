package shipping.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.CargoDAO;
import shipping.model.Cargo;
import shipping.service.api.CargoService;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    private CargoDAO cargoDAO;

    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    @Override
    @Transactional
    public void addCargo(Cargo cargo) {
        cargoDAO.addCargo(cargo);
    }

    @Override
    @Transactional
    public void updateCargo(Cargo cargo) {
        cargoDAO.update(cargo);
    }

    @Override
    @Transactional
    public List<Cargo> listCargoes() {
        return cargoDAO.listCargoes();
    }

    @Override
    @Transactional
    public Cargo getCargoById(int id) {
        return cargoDAO.getCargo(id);
    }

    @Override
    @Transactional
    public void removeCargo(int id) {
        cargoDAO.removeCargo(id);
    }
}
