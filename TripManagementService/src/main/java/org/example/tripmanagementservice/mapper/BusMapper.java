package org.example.tripmanagementservice.mapper;

import org.example.tripmanagementservice.dto.bus.BusDetailsDTO;
import org.example.tripmanagementservice.dto.bus.BusRequestDTO;
import org.example.tripmanagementservice.dto.bus.BusResponseDTO;
import org.example.tripmanagementservice.entity.Bus;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusMapper {

    Bus toEntity(BusRequestDTO busRequestDTO);

    BusResponseDTO toResponseDTO(Bus bus);

    BusDetailsDTO toDetailsDTO(Bus bus);

    List<BusDetailsDTO> toDetailsDTO(List<Bus> busList);

    List<BusResponseDTO> toListResponseDTO(List<Bus> bus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BusRequestDTO dto, @MappingTarget Bus entity);

}
