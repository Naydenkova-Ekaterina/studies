package shipping.service.api;

import shipping.dto.*;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Road;
import shipping.model.Route;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface RouteService {

    void addRoute(Route route) throws CustomServiceException;
    void updateRoute(Route route) throws CustomServiceException;
    List<Route> listRoute() throws CustomServiceException;
    Route getRouteById(int id) throws CustomServiceException;
    void removeRoute(int id) throws CustomServiceException;

    LinkedList<City> getPath(int from, int to) throws CustomServiceException;

    RouteDTO remakeRoute(RouteDTO oldRoute, CargoDTO newCargo) throws CustomServiceException;
    String remakeRoute(String oldRoute, CargoDTO newCargo) throws CustomServiceException;

    double countDistanceForRoute(List<CityDTO> linkedList) throws CustomServiceException;

    LocalTime getRouteTime(List<City> cities) throws CustomServiceException;

}
