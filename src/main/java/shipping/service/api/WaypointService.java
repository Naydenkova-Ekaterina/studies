package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.Waypoint;

import java.util.List;

public interface WaypointService {

    List<Waypoint> listWaypoints() throws CustomServiceException;
    List<Waypoint> listWaypointsSrc() throws CustomServiceException;
    List<Waypoint> listWaypointsDst() throws CustomServiceException;
    Waypoint getWaypointById(int id) throws CustomServiceException;
    List<Waypoint> waypointsForOrder(int id) throws CustomServiceException;

}
