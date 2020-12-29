package com.markusdel.cloudparking.service;

import com.markusdel.cloudparking.dto.ParkingDTO;
import com.markusdel.cloudparking.exceptions.ParkingNotFoundException;
import com.markusdel.cloudparking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private static Map<String, Parking> parkingMap = new HashMap();

    static  {
        var id = getUUID();
        var id1 = getUUID();
        Parking parking = new Parking(id, "DMS- 1111", "SC", "CELTA", "PRETO");
        Parking parking1 = new Parking(id1, "WAS- 1234", "SP", "VW GOl", "VERMELHO");
        parkingMap.put(id, parking);
        parkingMap.put(id1, parking1);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public List<Parking> findAll(){
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    public Parking findById(String id) {
        Parking parking = parkingMap.get(id);

        if(parking == null) {
            throw new ParkingNotFoundException(id);
        }
        return parking;
    }

    public Parking create(Parking parking) {
        var newId = getUUID();
        parking.setId(newId);
        parking.setEntryDate(LocalDateTime.now());
        parkingMap.put(newId, parking);
        return parkingMap.get(newId);
    }

    public void delete(String id) {
        Parking byId = findById(id);
        parkingMap.remove(id);
    }

    public Parking update(String id, Parking parking) {
        Parking byId = findById(id);
        byId.setColor(parking.getColor());
        byId.setState(parking.getState());
        byId.setLicense(parking.getLicense());
        byId.setModel(parking.getModel());
        parkingMap.replace(id, byId);
        return byId;
    }

}
