package org.example.tripmanagementservice.controller;

import org.example.tripmanagementservice.dto.bus.BusResponseDTO;
import org.example.tripmanagementservice.entity.Bus;
import org.example.tripmanagementservice.mapper.BusMapper;
import org.example.tripmanagementservice.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bus/")
public class BusController {

    private final BusMapper busMapper;
    private final BusService busService;

    @Autowired
    public BusController(BusMapper busMapper, BusService busService){
        this.busMapper = busMapper;
        this.busService = busService;
    }

    @GetMapping
    public ResponseEntity<List<BusResponseDTO>> getBuses(){
        List<Bus> buses = busService.getBuses();
        return ResponseEntity.ok(busMapper.toListResponseDTO(buses));
    }

    @GetMapping("/{busId}")
    public ResponseEntity<BusResponseDTO> getBus(@PathVariable UUID busId){
        Bus bus = busService.getBusById(busId);
        return ResponseEntity.ok(busMapper.toResponseDTO(bus));
    }
}
