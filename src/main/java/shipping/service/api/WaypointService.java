package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.Waypoint;

import java.util.List;

public interface WaypointService {

    List<Waypoint> listWaypoints() throws CustomServiceException;
    Waypoint getWaypointById(int id) throws CustomServiceException;
    List<Waypoint> waypointsForOrder(int id) throws CustomServiceException;

}
