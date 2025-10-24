package mapper;

import dto.bus.BusDetailsDTO;
import dto.bus.BusRequestDTO;
import dto.bus.BusResponseDTO;
import entity.Bus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {RouteMapper.class})
public interface BusMapper {

    Bus toEntity(BusRequestDTO busRequestDTO);

    BusResponseDTO toResponseDTO(Bus bus);

    BusDetailsDTO toDetailsDTO(Bus bus);

    List<BusDetailsDTO> toDetailsDTO(List<Bus> busList);

    List<BusResponseDTO> toListResponseDTO(List<Bus> bus);

}
