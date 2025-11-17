package org.example.tripmanagementservice.mapper;

import org.example.tripmanagementservice.dto.stop.StopDetailsDTO;
import org.example.tripmanagementservice.dto.stop.StopRequestDTO;
import org.example.tripmanagementservice.dto.stop.StopResponseDTO;
import org.example.tripmanagementservice.entity.Stop;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",uses = {RouteStopMapper.class})
public interface StopMapper {

    Stop toEntity(StopRequestDTO stopRequestDTO);

    StopResponseDTO toResponseDTO(Stop stop);

    StopDetailsDTO toDetailsDTO(Stop stop);

    List<StopResponseDTO> toListResponseDTO(List<Stop> stops);

    List<StopDetailsDTO> toDetailsDTO(List<Stop> stops);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StopRequestDTO dto, @MappingTarget Stop entity);
}
