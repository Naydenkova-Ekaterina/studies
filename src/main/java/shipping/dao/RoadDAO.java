package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.Road;
import shipping.model.User;

import java.util.List;

public interface RoadDAO {

    List<Road> listRoads() throws CustomDAOException;

}
