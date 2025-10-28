package mapper;

import dto.bus.BusDetailsDTO;
import dto.bus.BusRequestDTO;
import dto.bus.BusResponseDTO;
import dto.route.RouteRequestDTO;
import entity.Bus;
import entity.Route;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",uses = {RouteMapper.class})
public interface BusMapper {

    Bus toEntity(BusRequestDTO busRequestDTO);

    BusResponseDTO toResponseDTO(Bus bus);

    BusDetailsDTO toDetailsDTO(Bus bus);

    List<BusDetailsDTO> toDetailsDTO(List<Bus> busList);

    List<BusResponseDTO> toListResponseDTO(List<Bus> bus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BusRequestDTO dto, @MappingTarget Bus entity);

}
