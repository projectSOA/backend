package mapper;

import dto.routeStop.RouteStopDetailsDTO;
import dto.routeStop.RouteStopRequestDTO;
import dto.routeStop.RouteStopResponseDTO;
import entity.RouteStop;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {StopMapper.class, RouteMapper.class})
public interface RouteStopMapper {

    RouteStop toEntity(RouteStopRequestDTO routeStopDTO);

    RouteStopResponseDTO toResponseDTO(RouteStop routeStop);

    RouteStopDetailsDTO toDetailsDTO(RouteStop routeStop);

    List<RouteStopResponseDTO> toListResponseDTO(List<RouteStop> routeStops);

    List<RouteStopDetailsDTO> toListDetailsDTO(List<RouteStop> routeStops);
}
