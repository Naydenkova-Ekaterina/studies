package shipping.dijkstra;

import lombok.*;
import shipping.model.City;
import shipping.model.Road;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Graph {

    private List<City> vertexes;
    private List<Road> edges;

}
