package shipping.service.api;

import shipping.dto.CargoDTO;
import shipping.dto.CityDTO;
import shipping.dto.DriverDto;
import shipping.dto.OrderDTO;
import shipping.dto.rest.OrderDTOrest;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Order;

import java.util.List;

public interface OrderService {

    void addOrder(OrderDTO order) throws CustomServiceException;
    void updateOrder(OrderDTO order) throws CustomServiceException;
    List<OrderDTO> listOrders() throws CustomServiceException;
    OrderDTO getOrderById(int id) throws CustomServiceException;
    void removeOrder(int id) throws CustomServiceException;
    OrderDTO getOrderByWagon(String id) throws CustomServiceException;
    double countOrderWeight(OrderDTO order) throws CustomServiceException;
    void updateOrderAfterChangingRoute(OrderDTO order) throws CustomServiceException;
    List<CityDTO> convertWayToList(OrderDTO order) throws CustomServiceException;
    List<CargoDTO> getOrderCargoes(int id) throws CustomServiceException;
    List<DriverDto> getOrderDrivers(int id) throws CustomServiceException;
    String addCargoToExistingOrder(int idOrder, int idCargo) throws CustomServiceException;
    List<OrderDTOrest> listOrdersDTOrest() throws CustomDAOException;

    }
