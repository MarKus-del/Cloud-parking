package com.markusdel.cloudparking.service;

import com.markusdel.cloudparking.exceptions.ParkingNotFoundException;
import com.markusdel.cloudparking.model.Parking;
import com.markusdel.cloudparking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll(){
        return parkingRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(() ->
            new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parking) {
        var newId = getUUID();
        parking.setId(newId);
        parking.setEntryDate(LocalDateTime.now());
        return parkingRepository.save(parking);
    }

    @Transactional
    public void delete(String id) {
        Parking byId = findById(id);
        parkingRepository.delete(byId);
    }

    @Transactional
    public Parking update(String id, Parking parking) {
        Parking byId = findById(id);
        byId.setColor(parking.getColor());
        byId.setState(parking.getState());
        byId.setLicense(parking.getLicense());
        byId.setModel(parking.getModel());
        return parkingRepository.save(byId);
    }

    @Transactional
    public Parking checkOut(String id){
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        return parkingRepository.save(parking);
    }

}
