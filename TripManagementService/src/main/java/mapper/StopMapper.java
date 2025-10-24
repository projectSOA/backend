package mapper;

import dto.stop.StopDetailsDTO;
import dto.stop.StopRequestDTO;
import dto.stop.StopResponseDTO;
import entity.Stop;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {RouteStopMapper.class})
public interface StopMapper {

    Stop toEntity(StopRequestDTO stopRequestDTO);

    StopResponseDTO toResponseDTO(Stop stop);

    StopDetailsDTO toDetailsDTO(Stop stop);

    List<StopResponseDTO> toListResponseDTO(List<Stop> stops);

    List<StopDetailsDTO> toDetailsDTO(List<Stop> stops);
}
