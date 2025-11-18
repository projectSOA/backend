package org.example.tripmanagementservice.mapper;

import org.example.tripmanagementservice.dto.route.RouteDetailsDTO;
import org.example.tripmanagementservice.dto.route.RouteRequestDTO;
import org.example.tripmanagementservice.dto.route.RouteResponseDTO;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.entity.Stop;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {BusMapper.class, RouteStopMapper.class})
public interface RouteMapper {

    Route toEntity(RouteRequestDTO routeRequestDTO);

    @Mapping(target="startStationName", expression="java(getStartStationName(route))")
    @Mapping(target="endStationName", expression="java(getEndStationName(route))")
    RouteResponseDTO toResponseDto(Route route);

    RouteDetailsDTO toDetailsDto(Route route);

    List<RouteResponseDTO> toListResponseDto(List<Route> routes);

    List<RouteDetailsDTO> toListDetailsDto(List<Route> routes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(RouteRequestDTO dto, @MappingTarget Route entity);

    default String getStartStationName(Route route){
        Stop start = route.getRouteStops().stream().filter((stop)->stop.getStopOrder()==0).map(RouteStop::getStop).findFirst().orElse(null);
        return start!=null?start.getName():"";
    }

    default String getEndStationName(Route route){
        int numberOfStations = route.getRouteStops().size();
        Stop end = route.getRouteStops().stream().filter((stop)->stop.getStopOrder()==numberOfStations-1).map(RouteStop::getStop).findFirst().orElse(null);
        return end!=null?end.getName():"";
    }
}
