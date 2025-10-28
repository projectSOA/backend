package org.example.tripmanagementservice.mapper;

import org.example.tripmanagementservice.dto.route.RouteDetailsDTO;
import org.example.tripmanagementservice.dto.route.RouteRequestDTO;
import org.example.tripmanagementservice.dto.route.RouteResponseDTO;
import org.example.tripmanagementservice.entity.Route;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",uses = {BusMapper.class, RouteStopMapper.class})
public interface RouteMapper {

    Route toEntity(RouteRequestDTO routeRequestDTO);

    RouteResponseDTO toResponseDto(Route route);

    RouteDetailsDTO toDetailsDto(Route route);

    List<RouteResponseDTO> toListResponseDto(List<Route> routes);

    List<RouteDetailsDTO> toListDetailsDto(List<Route> routes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(RouteRequestDTO dto, @MappingTarget Route entity);
}
