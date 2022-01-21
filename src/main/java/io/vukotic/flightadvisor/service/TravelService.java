package io.vukotic.flightadvisor.service;

import io.vukotic.flightadvisor.persistence.model.Airport;
import io.vukotic.flightadvisor.persistence.repository.AirportRepository;
import io.vukotic.flightadvisor.persistence.repository.CityRepository;
import io.vukotic.flightadvisor.persistence.repository.RouteRepository;
import io.vukotic.flightadvisor.dto.ShortestPathDto;
import io.vukotic.flightadvisor.dto.ShortestPathRouteDto;
import io.vukotic.flightadvisor.error.exception.CityNotFoundException;
import io.vukotic.flightadvisor.error.exception.NoFlightPathException;
import lombok.RequiredArgsConstructor;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.CHManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final RouteRepository routeRepository;

    public ShortestPathDto findCheapestFlight(Long sourceCityId, Long destinationCityId) {

        var sourceCity = cityRepository.findById(sourceCityId).orElseThrow(CityNotFoundException::new);
        var destinationCity = cityRepository.findById(destinationCityId).orElseThrow(CityNotFoundException::new);

        var airportRouteGraph = constructGraph();

        var sourceAirports = sourceCity.getAirports().stream().map(Airport::getAirportId).collect(Collectors.toSet());
        var destinationAirports = destinationCity.getAirports().stream().map(Airport::getAirportId).collect(Collectors.toSet());

        //https://en.wikipedia.org/wiki/Contraction_hierarchies CH Many-To-Many shortest path algorithm
        var chShortestPathAlgorithm = new CHManyToManyShortestPaths<>(airportRouteGraph);
        var shortestPathSet = chShortestPathAlgorithm.getManyToManyPaths(sourceAirports, destinationAirports);

        var shortestPath = getShortestPathFromPathSet(shortestPathSet, sourceAirports, destinationAirports);
        if (shortestPath == null) throw new NoFlightPathException();
        var shortestPathRoutes = shortestPath.getEdgeList().stream().map(
                edge -> new ShortestPathRouteDto(
                        getCityNameForAirportId(airportRouteGraph.getEdgeSource(edge)),
                        getCityNameForAirportId(airportRouteGraph.getEdgeTarget(edge)),
                        airportRouteGraph.getEdgeWeight(edge))
        ).collect(Collectors.toList());
        var totalLength = shortestPath.getLength();
        var totalPrice = shortestPath.getWeight();
        return new ShortestPathDto(totalPrice, totalLength, shortestPathRoutes);

    }

    private DirectedWeightedMultigraph<Long, DefaultWeightedEdge> constructGraph() {
        var allAirports = airportRepository.findAll();
        var allRoutes = routeRepository.findAll();
        var airportRouteGraph = new DirectedWeightedMultigraph<Long, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        allAirports.forEach(airport -> airportRouteGraph.addVertex(airport.getAirportId()));

        allRoutes.forEach(route -> {
            var edge = airportRouteGraph.addEdge(route.getSourceAirport().getAirportId(), route.getDestinationAirport().getAirportId());
            airportRouteGraph.setEdgeWeight(edge, route.getPrice());
        });

        return airportRouteGraph;
    }

    private String getCityNameForAirportId(Long id) {
        var airport = airportRepository.findById(id).orElseThrow(CityNotFoundException::new);
        return airport.getCity().getName();
    }

    private GraphPath<Long, DefaultWeightedEdge> getShortestPathFromPathSet(ManyToManyShortestPaths<Long, DefaultWeightedEdge> shortestPathSet,
                                                                            Set<Long> sourceAirports, Set<Long> destinationAirports) {

        var allPaths = new ArrayList<GraphPath<Long, DefaultWeightedEdge>>();
        for (Long sourceId : sourceAirports)
            for (Long destinationId : destinationAirports)
                allPaths.add(shortestPathSet.getPath(sourceId, destinationId));

        return allPaths.stream().filter(Objects::nonNull).min(Comparator.comparing(GraphPath::getWeight)).orElse(null);
    }

}
