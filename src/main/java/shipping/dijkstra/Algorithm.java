package shipping.dijkstra;

import lombok.Getter;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Road;

import java.util.*;

@Getter
public class Algorithm {

    private Graph graph;
    private Set<City> settledNodes;
    private Set<City> unSettledNodes;
    private Map<City, City> predecessors;
    private Map<City, Double> distances;

    public Algorithm(Graph graph) {
        this.graph = graph;
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distances = new HashMap<>();
        predecessors = new HashMap<>();
    }

    public void start(City src) {
        distances.put(src, 0.0);
        unSettledNodes.add(src);
        while (unSettledNodes.size() > 0) {
            City node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(City node) {
        List<City> adjacentNodes = getNeighbors(node);
        for (City target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distances.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
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
            if (edge.getSrc().equals(node) && !settledNodes.contains((edge.getDst()))) {
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
        path.add(target);
        while (predecessors.get(target) != null) {
            target = predecessors.get(target);
            path.add(target);
        }
        Collections.reverse(path);
        return path;
    }

}
