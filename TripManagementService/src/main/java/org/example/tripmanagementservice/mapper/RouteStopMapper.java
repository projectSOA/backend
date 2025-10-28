package org.example.tripmanagementservice.mapper;

import org.example.tripmanagementservice.dto.routeStop.RouteStopDetailsDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopRequestDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopResponseDTO;
import org.example.tripmanagementservice.entity.RouteStop;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",uses = {StopMapper.class})
public interface RouteStopMapper {

    RouteStop toEntity(RouteStopRequestDTO routeStopDTO);

    RouteStopResponseDTO toResponseDTO(RouteStop routeStop);

    RouteStopDetailsDTO toDetailsDTO(RouteStop routeStop);

    List<RouteStopResponseDTO> toListResponseDTO(List<RouteStop> routeStops);

    List<RouteStopDetailsDTO> toListDetailsDTO(List<RouteStop> routeStops);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(RouteStopRequestDTO dto, @MappingTarget RouteStop entity);
}
