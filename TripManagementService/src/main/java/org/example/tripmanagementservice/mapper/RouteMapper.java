package org.example.tripmanagementservice.mapper;

import org.example.tripmanagementservice.dto.route.RouteDetailsDTO;
import org.example.tripmanagementservice.dto.route.RouteRequestDTO;
import org.example.tripmanagementservice.dto.route.RouteResponseDTO;
import org.example.tripmanagementservice.dto.route.TrajectoryDTO;
import org.example.tripmanagementservice.dto.stop.StationDTO;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.entity.Stop;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {BusMapper.class, RouteStopMapper.class, StopMapper.class})
public interface RouteMapper {


    Route toEntity(RouteRequestDTO routeRequestDTO);

    @Mapping(target="startStationName", expression="java(getStartStationName(route))")
    @Mapping(target="endStationName", expression="java(getEndStationName(route))")
    RouteResponseDTO toResponseDto(Route route);

    @Mapping(target="startStation", expression="java(getStartStation(route,stopMapper))")
    @Mapping(target="endStation", expression="java(getEndStation(route,stopMapper))")
    TrajectoryDTO toTrajectoryDTO(Route route, @Context StopMapper stopMapper);

    RouteDetailsDTO toDetailsDto(Route route);

    List<RouteResponseDTO> toListResponseDto(List<Route> routes);

    List<RouteDetailsDTO> toListDetailsDto(List<Route> routes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(RouteRequestDTO dto, @MappingTarget Route entity);

    default String getStartStationName(Route route) {
        if (route.getRouteStops() == null || route.getRouteStops().isEmpty()) {
            return "";
        }
        return route.getRouteStops().stream()
                .sorted((a, b) -> Integer.compare(a.getStopOrder(), b.getStopOrder()))
                .map(RouteStop::getStop)
                .map(Stop::getName)
                .findFirst()
                .orElse("");
    }

    default String getEndStationName(Route route) {
        if (route.getRouteStops() == null || route.getRouteStops().isEmpty()) {
            return "";
        }
        return route.getRouteStops().stream()
                .sorted((a, b) -> Integer.compare(a.getStopOrder(), b.getStopOrder()))
                .map(RouteStop::getStop)
                .map(Stop::getName)
                .reduce((first, second) -> second) // gets last element
                .orElse("");
    }


    default StationDTO getStartStation(Route route, @Context StopMapper stopMapper) {
        if (route.getRouteStops() == null || route.getRouteStops().isEmpty()) {
            return null;
        }
        return route.getRouteStops().stream()
                .sorted((a, b) -> Integer.compare(a.getStopOrder(), b.getStopOrder()))
                .map(RouteStop::getStop)
                .findFirst()
                .map(stopMapper::toStationDTO)
                .orElse(null);
    }

    default StationDTO getEndStation(Route route, @Context StopMapper stopMapper) {
        if (route.getRouteStops() == null || route.getRouteStops().isEmpty()) {
            return null;
        }
        return route.getRouteStops().stream()
                .sorted((a, b) -> Integer.compare(a.getStopOrder(), b.getStopOrder()))
                .map(RouteStop::getStop)
                .reduce((first, second) -> second)
                .map(stopMapper::toStationDTO)
                .orElse(null);
    }
}
