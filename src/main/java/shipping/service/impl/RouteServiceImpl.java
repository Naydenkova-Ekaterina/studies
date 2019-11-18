package shipping.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.RouteDAO;
import shipping.dijkstra.Algorithm;
import shipping.dijkstra.Graph;
import shipping.dto.*;
import shipping.dto.converter.CityConverter;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Route;
import shipping.service.api.CityService;
import shipping.service.api.RoadService;
import shipping.service.api.RouteService;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private RouteDAO routeDAO;

    public void setRouteDAO(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    @Autowired
    private CityService cityService;

    @Autowired
    private RoadService roadService;

    CityConverter cityConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void addRoute(Route route) throws CustomServiceException {
        try {
            routeDAO.addRoute(route);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateRoute(Route route) throws CustomServiceException {
        try {
            routeDAO.update(route);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Route> listRoute() throws CustomServiceException {
        try {
            return routeDAO.listRoutes();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }    }

    @Override
    @Transactional
    public Route getRouteById(int id) throws CustomServiceException {
        try {
            return routeDAO.getRoute(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }    }

    @Override
    @Transactional
    public void removeRoute(int id) throws CustomServiceException {
        try {
            routeDAO.removeRoute(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    public LinkedList<City> getPath(int src, int dst) throws CustomServiceException {
        cityConverter = new CityConverter(modelMapper);

        Graph graph = new Graph(cityService.listCities().stream().map(cityDTO -> cityConverter.convertToEntity(cityDTO)).collect(Collectors.toList()), roadService.listRoads());
        Algorithm algorithm = new Algorithm(graph);
        algorithm.start(graph.getVertexes().get(src));
        List<City> cities = algorithm.getGraph().getVertexes();
        City city = cities.get(dst);


        for (City c: algorithm.getPath(city)) {
            System.out.println(c.getName());
        }

        return algorithm.getPath(city);
    }

    public RouteDTO remakeRoute(RouteDTO oldRoute, CargoDTO newCargo) throws CustomServiceException {
        cityConverter = new CityConverter(modelMapper);
        if (!oldRoute.getCityDTOList().contains(newCargo.getSrc().getCity()) ||
                (oldRoute.getCityDTOList().contains(newCargo.getSrc().getCity()) && oldRoute.getCityDTOList().contains(newCargo.getDst().getCity()))) {
            System.out.println("old route");
            return oldRoute;
        }
        RouteDTO newRoute = new RouteDTO();
        int citiesCount = oldRoute.getCityDTOList().size();
        Map<Double, List<City>> map = new HashMap<>();

        List<City> oldStartRoute = oldRoute.getCityDTOList().stream().map(cityDTO -> cityConverter.convertToEntity(cityDTO)).collect(Collectors.toList());

        System.out.println("old route " );
        for (City c:oldStartRoute) {
            System.out.println(c.getName());
        }

        int index = oldStartRoute.size()-1;
        int startChangingIndexForRoute = oldRoute.getCityDTOList().indexOf(newCargo.getSrc().getCity());

        for (int i = index; i>startChangingIndexForRoute; i--) {
            oldStartRoute.remove(i);
        }

        oldStartRoute.stream().forEach(cityDTO -> System.out.println(cityDTO.getId() + " " + cityDTO.getName()));


        for (int i = startChangingIndexForRoute; i < citiesCount; i++) {
            List<City> finalRoute = new LinkedList<>();
            CityDTO current = oldRoute.getCityDTOList().get(i);
            int  from1 = cityService.listCities().indexOf(cityConverter.convertToEntity(current));
            int to1 = cityService.listCities().indexOf(cityConverter.convertToEntity(newCargo.getDst().getCity()));
            LinkedList<City> newSubRouteNewSrcToNewDst = getPath(from1, to1);


            int from2 = cityService.listCities().indexOf(cityConverter.convertToEntity(newCargo.getDst().getCity()));
            int to2 = cityService.listCities().indexOf(cityConverter.convertToEntity(oldRoute.getCityDTOList().get(citiesCount-1)));
            LinkedList<City> newSubRouteNewDstToOldDst = getPath(from2, to2);
            newSubRouteNewDstToOldDst.removeFirst();
            newSubRouteNewSrcToNewDst.addAll(newSubRouteNewDstToOldDst);
            finalRoute.addAll(oldStartRoute);
            finalRoute.addAll(newSubRouteNewSrcToNewDst);
            System.out.println("route " + i );
            for (City c:finalRoute) {
                System.out.println(c.getName());
            }
            map.put(countDistanceForRoute(newSubRouteNewSrcToNewDst.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList())), finalRoute);
        }

        double min = Double.MAX_VALUE;
        for (double key: map.keySet()) {
            if (key < min) {
                min = key;
            }
        }

        List<CityDTO> listConverted = new LinkedList<>(map.get(min).stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList()));
        newRoute.setCityDTOList(listConverted);
        return newRoute;
    }


    public String remakeRoute(String oldRoute, CargoDTO newCargo) throws CustomServiceException {
        cityConverter = new CityConverter(modelMapper);
        oldRoute.trim();
        ArrayList<String> oldRouteArray = new ArrayList<>(Arrays.asList(oldRoute.split(" ")));
        ArrayList<String> oldRouteArrayForAdd = new ArrayList<>(oldRouteArray);
        String dstName = oldRouteArray.get(oldRouteArray.size()-1);
        if (!oldRouteArray.contains(newCargo.getSrc().getCity().getName()) ||
                (oldRouteArray.contains(newCargo.getSrc().getCity().getName()) && oldRouteArray.contains(newCargo.getDst().getCity().getName()))) {
            System.out.println("old route");
            return oldRoute;
        }
        String newRoute = "";
        int citiesCount = oldRouteArray.size();
        Map<Double, List<String>> map = new HashMap<>();


        int index = oldRouteArray.size()-1;
        int startChangingIndexForRoute = oldRouteArray.indexOf(newCargo.getSrc().getCity().getName());

        for (int i = index; i>startChangingIndexForRoute; i--) {
            oldRouteArrayForAdd.remove(i);
        }


        for (int i = startChangingIndexForRoute; i < citiesCount; i++) {
            List<String> finalRoute = new ArrayList<>();
            CityDTO current = cityService.getCityByName(oldRouteArray.get(i));
            if (i != startChangingIndexForRoute) {
                oldRouteArrayForAdd.add(current.getName());
            }
            int  from1 = cityService.listCities().indexOf((current));
            int to1 = cityService.listCities().indexOf(newCargo.getDst().getCity());
            LinkedList<City> newSubRouteNewSrcToNewDst = getPath(from1, to1);

            List<String> newSubRouteNewSrcToNewDstArray = newSubRouteNewSrcToNewDst.stream().map(city -> city.getName()).collect(Collectors.toList());

            if (newSubRouteNewSrcToNewDstArray.contains(dstName) && newSubRouteNewSrcToNewDstArray.contains(newCargo.getDst().getCity().getName())) {
                if (i==startChangingIndexForRoute) {

                    newSubRouteNewSrcToNewDstArray.remove(0);
                    newSubRouteNewSrcToNewDst.removeFirst();
                } else {
                    oldRouteArrayForAdd.remove(dstName);

                }

                finalRoute.addAll(oldRouteArrayForAdd);
                finalRoute.addAll(newSubRouteNewSrcToNewDstArray);


                map.put(countDistanceForRoute(newSubRouteNewSrcToNewDst.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList())), finalRoute);

                continue;
            }
            if (i==startChangingIndexForRoute) {
                newSubRouteNewSrcToNewDstArray.remove(0);
                newSubRouteNewSrcToNewDst.removeFirst();
            }

            CityDTO oldRouteDst = cityService.getCityByName(dstName);
            int from2 = cityService.listCities().indexOf(newCargo.getDst().getCity());
            int to2 = cityService.listCities().indexOf(oldRouteDst);
            LinkedList<City> newSubRouteNewDstToOldDst = getPath(from2, to2);
            newSubRouteNewDstToOldDst.removeFirst();
            List<String> newSubRouteNewDstToOldDstArray = newSubRouteNewDstToOldDst.stream().map(city -> city.getName()).collect(Collectors.toList());

            newSubRouteNewSrcToNewDst.addAll(newSubRouteNewDstToOldDst);
            newSubRouteNewSrcToNewDstArray.addAll(newSubRouteNewDstToOldDstArray);

            finalRoute.addAll(oldRouteArrayForAdd);
            finalRoute.addAll(newSubRouteNewSrcToNewDstArray);

            map.put(countDistanceForRoute(newSubRouteNewSrcToNewDst.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList())), finalRoute);
        }

        double min = Double.MAX_VALUE;
        for (double key: map.keySet()) {
            if (key < min) {
                min = key;
            }
        }

        List<String> listConverted = new LinkedList<>(map.get(min));

        for (List<String> list: map.values()) {
            list.forEach(System.out::println);
            System.out.println();
        }

        newRoute = correctRoute(listConverted);

        return newRoute;
    }

    public String correctRoute(List<String> route){
        String result = "";
        for (int i = 0; i < route.size()-1; i++) {
            if (route.get(i).equals(route.get(i+1))) {
                route.remove(i);
            }
        }
        for (String str: route) {
            result += str + " ";
        }

        return result;
    }



    public double countDistanceForRoute(List<CityDTO> linkedList) throws CustomServiceException{
        cityConverter = new CityConverter(modelMapper);
        double result = 0;
        List<CityDTO> cityDTOList = new LinkedList<>(linkedList);
        CityDTO prev;
        CityDTO cur;

        for (int i = 0; i < cityDTOList.size()-1; i++) {
            prev = cityDTOList.get(i);
            cur = cityDTOList.get(i+1);
            result += roadService.getDistanceBetweenCities(cityConverter.convertToEntity(prev), cityConverter.convertToEntity(cur));
        }

        return result;
    }

    private LocalTime calculateRouteTime(double distance, List<CityDTO> cities) {

        LocalTime routeTime ;
        int countRoutePoints = cities.size();
        int hours = (int)distance / 60;
        int min = (int) distance % 60;
        routeTime = LocalTime.of(hours+countRoutePoints, min, 0);
        return routeTime;
    }

    @Override
    public LocalTime getRouteTime(List<CityDTO> cities) throws CustomServiceException  {

        double distance = countDistanceForRoute(cities);
        LocalTime time = calculateRouteTime(distance, cities);
        return time;
    }

}
