package shipping.dijkstra;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Road;
import shipping.service.api.CityService;
import shipping.service.api.RoadService;

import java.util.*;

@Getter
public class Algorithm {

    private Graph graph;
    private Set<City> visitedVertexes;
    private Set<City> unvisitedVertexes;
    private Map<City, City> predecessors;
    private Map<City, Double> distances;

    @Autowired
    private CityService cityService;

    @Autowired
    private RoadService roadService;

    public Algorithm() throws CustomServiceException {
        graph = new Graph(cityService.listCities(), roadService.listRoads());
        visitedVertexes = new HashSet<>();
        unvisitedVertexes = new HashSet<>();
        distances = new HashMap<>();
        predecessors = new HashMap<>();
    }

    public void start(City src) {
        distances.put(src, 0.0);
        unvisitedVertexes.add(src);
        while (unvisitedVertexes.size() > 0) {
            City node = getMinimum(unvisitedVertexes);
            visitedVertexes.add(node);
            unvisitedVertexes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(City node) {
        List<City> adjacentNodes = getNeighbors(node);
        for (City target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distances.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unvisitedVertexes.add(target);
            }
        }
    }

    private double getDistance(City node, City target) {
        for (Road edge : graph.getEdges()) {
            if (edge.getSrc().equals(node)
                    && edge.getDst().equals(target)) {
                return edge.getDistance();
            }
        }
        throw new RuntimeException("Distance between cities wasn't found.");
    }

    private List<City> getNeighbors(City node) {
        List<City> neighbors = new ArrayList<>();
        for (Road edge : graph.getEdges()) {
            if (edge.getSrc().equals(node) && !visitedVertexes.contains((edge.getDst()))) {
                neighbors.add(edge.getDst());
            }
        }
        return neighbors;
    }

    private City getMinimum(Set<City> vertexes) {
        City minimum = null;
        for (City vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private double getShortestDistance(City destination) {
        Double d = distances.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<City> getPath(City target) {
        LinkedList<City> path = new LinkedList<>();
        if (predecessors.get(target) == null) {
            return null;
        }
        path.add(target);
        while (predecessors.get(target) != null) {
            target = predecessors.get(target);
            path.add(target);
        }
        Collections.reverse(path);
        return path;
    }

}
