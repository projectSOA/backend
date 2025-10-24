package mapper;

import dto.route.RouteDetailsDTO;
import dto.route.RouteRequestDTO;
import dto.route.RouteResponseDTO;
import entity.Route;
import entity.RouteStop;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    Route toEntity(RouteRequestDTO routeRequestDTO);

    RouteResponseDTO toResponseDto(Route route);

    RouteDetailsDTO toDetailsDto(Route route);

    List<RouteResponseDTO> toListResponseDto(List<Route> routes);

    List<RouteDetailsDTO> toListDetailsDto(List<Route> routes);
}
