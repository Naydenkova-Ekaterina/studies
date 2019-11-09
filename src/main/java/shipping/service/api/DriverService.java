package shipping.service.api;

import shipping.dto.CargoDTO;
import shipping.dto.DriverDto;
import shipping.dto.DriverInfoDTO;
import shipping.exception.CustomServiceException;
import shipping.model.Driver;
import shipping.model.Order;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface DriverService {

    void addDriver(DriverDto driver) throws CustomServiceException;
    void updateDriver(DriverDto driver) throws CustomServiceException;
    List<DriverDto> listDrivers() throws CustomServiceException;
    DriverDto getDriverById(int id) throws CustomServiceException;
    void removeDriver(int id) throws CustomServiceException;
    List<Integer> driversByWagon(String id) throws CustomServiceException;
    Map<DriverDto, LocalTime> getSuitableDrivers(Order order, LocalTime orderTime) throws CustomServiceException;
    DriverDto getAuthenticatedDriver() throws CustomServiceException;
    void addUser(DriverDto driverDto, String email) throws CustomServiceException;
    DriverInfoDTO info() throws CustomServiceException;
    List<DriverDto> getSuitableDrivers(int id) throws CustomServiceException;
    void setOrder(int idDriver, int idOrder) throws CustomServiceException;
    void changeDriverStatus(String status) throws CustomServiceException;
    void startShift() throws CustomServiceException;
    void endShift() throws CustomServiceException;
    List<CargoDTO> getDriverCargoes() throws CustomServiceException;
    void updateDriverAfterSetOrder(DriverDto driverDto) throws CustomServiceException;

    }
