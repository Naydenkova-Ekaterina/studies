package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.Order;
import shipping.model.Waypoint;

import java.util.List;

public interface WaypointDAO {

    void addWaypint(Waypoint waypoint) throws CustomDAOException;
    void update(Waypoint waypoint) throws CustomDAOException;
    List<Waypoint> listWaypoints() throws CustomDAOException;
    Waypoint getWaypoint(int id) throws CustomDAOException;
    void removeWaypoint(int id) throws CustomDAOException;

}
