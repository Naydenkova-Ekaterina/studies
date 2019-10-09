package shipping.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.WaypointDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Waypoint;
import shipping.service.api.WaypointService;

import java.util.List;

@Service
public class WaypointServiceImpl implements WaypointService {

    private WaypointDAO waypointDAO;

    public void setWaypointDAO(WaypointDAO waypointDAO) {
        this.waypointDAO = waypointDAO;
    }

    @Override
    @Transactional
    public List<Waypoint> listWaypoints() throws CustomServiceException {
        try {
            return waypointDAO.listWaypoints();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Waypoint getWaypointById(int id) throws CustomServiceException {
        try {
            return waypointDAO.getWaypoint(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
