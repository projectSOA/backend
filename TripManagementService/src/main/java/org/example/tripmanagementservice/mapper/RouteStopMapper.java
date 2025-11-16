package org.example.tripmanagementservice.mapper;

import org.example.tripmanagementservice.dto.routeStop.RouteStopDetailsDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopRequestDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopResponseDTO;
import org.example.tripmanagementservice.entity.RouteStop;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StopMapper.class})
public interface RouteStopMapper {

    @Mapping(target = "route", ignore = true)
    @Mapping(target = "stop", ignore = true)
    RouteStop toEntity(RouteStopRequestDTO dto);

    @Mapping(target = "routeId", source = "route.id")
    @Mapping(target = "stopId", source = "stop.id")
    @Mapping(target = "id", source = "id")
    @Mapping(target="name", source = "stop.name")
    @Mapping(target="latitude", source = "stop.latitude")
    @Mapping(target="longitude", source = "stop.longitude")
    @Mapping(target="address", source = "stop.address")
    RouteStopResponseDTO toResponseDTO(RouteStop routeStop);

    RouteStopDetailsDTO toDetailsDTO(RouteStop routeStop);

    List<RouteStopResponseDTO> toListResponseDTO(List<RouteStop> routeStops);

    List<RouteStopDetailsDTO> toListDetailsDTO(List<RouteStop> routeStops);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "stop", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(RouteStopRequestDTO dto, @MappingTarget RouteStop entity);
}