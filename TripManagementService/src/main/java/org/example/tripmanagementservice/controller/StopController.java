package org.example.tripmanagementservice.controller;

import jakarta.validation.Valid;
import org.example.tripmanagementservice.dto.stop.StopRequestDTO;
import org.example.tripmanagementservice.dto.stop.StopResponseDTO;
import org.example.tripmanagementservice.entity.Stop;
import org.example.tripmanagementservice.mapper.StopMapper;
import org.example.tripmanagementservice.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stops")
public class StopController {

    private final StopMapper stopMapper;
    private final StopService stopService;

    @Autowired
    public StopController(StopMapper stopMapper, StopService stopService){
        this.stopMapper = stopMapper;
        this.stopService = stopService;
    }

    @GetMapping
    public ResponseEntity<List<StopResponseDTO>> getStops(){
        List<Stop> stops = stopService.getStops();
        return ResponseEntity.ok(stopMapper.toListResponseDTO(stops));
    }

    @GetMapping("/{stopId}")
    public ResponseEntity<StopResponseDTO> getStop(@PathVariable UUID stopId){
        Stop stop = stopService.getStop(stopId);
        return ResponseEntity.ok(stopMapper.toResponseDTO(stop));
    }

    @PostMapping
    public ResponseEntity<StopResponseDTO> addStop(@Valid @RequestBody StopRequestDTO stopRequestDTO){
        Stop stop = stopService.createStop(stopMapper.toEntity(stopRequestDTO));
        return ResponseEntity.ok(stopMapper.toResponseDTO(stop));
    }

    @PutMapping("/{stopId}")
    public ResponseEntity<StopResponseDTO> updateStop(@Valid @PathVariable UUID stopId, @RequestBody StopRequestDTO stopRequestDTO){
        Stop stop = stopService.updateStop(stopId,stopRequestDTO);
        return ResponseEntity.ok(stopMapper.toResponseDTO(stop));
    }
}
