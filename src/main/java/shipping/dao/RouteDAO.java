package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.Route;

import java.util.List;

public interface RouteDAO {

    void addRoute(Route route) throws CustomDAOException;
    void update(Route route) throws CustomDAOException;
    List<Route> listRoutes() throws CustomDAOException;
    Route getRoute(int id) throws CustomDAOException;
    void removeRoute(int id) throws CustomDAOException;
}
