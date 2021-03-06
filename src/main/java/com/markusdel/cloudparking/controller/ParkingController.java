package com.markusdel.cloudparking.controller;

import com.markusdel.cloudparking.dto.ParkingCreateDTO;
import com.markusdel.cloudparking.dto.ParkingDTO;
import com.markusdel.cloudparking.mapper.ParkingMapper;
import com.markusdel.cloudparking.model.Parking;
import com.markusdel.cloudparking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    public ResponseEntity<List<ParkingDTO>> findAll(){
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id){
        ParkingDTO parkingDTO = parkingMapper.toParkingDTO(parkingService.findById(id));
        return ResponseEntity.ok(parkingDTO);
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto){
        Parking parking = parkingMapper.toParkingCreate(dto);
        ParkingDTO newParkingCreated = parkingMapper.toParkingDTO(parkingService.create(parking));
        return ResponseEntity.status(HttpStatus.CREATED).body(newParkingCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO dto){
        Parking parking = parkingMapper.toParkingCreate(dto);
        ParkingDTO parkingUpdate = parkingMapper.toParkingDTO(parkingService.update(id, parking));
        return ResponseEntity.status(HttpStatus.OK).body(parkingUpdate);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id){ ;
        ParkingDTO parkingCheckOut = parkingMapper.toParkingDTO(parkingService.checkOut(id));
        return ResponseEntity.ok().body(parkingCheckOut);
    }
}
