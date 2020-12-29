package com.markusdel.cloudparking.service;

import com.markusdel.cloudparking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ParkingCheckOut {

    public final static int ONE_HOUR = 60;
    public final static int TWENTY_FOUR_HOUR = 24 * ONE_HOUR;
    public final static Double ONE_HOURS_VALUE = 5.00;
    public final static Double ADDITIONAL_PER_HOUR_VALUE = 2.0;
    public final static Double DAY_VALUE = 20.00;


    public static Double getBill(Parking parking){
        return getBill(parking.getEntryDate(), parking.getExitDate());
    }

    private static Double getBill(LocalDateTime entryDate, LocalDateTime exitDate) {
        long minutes = entryDate.until(exitDate, ChronoUnit.MINUTES);
        Double bill = 0.0;
        if(bill <= ONE_HOUR) {
            return ONE_HOURS_VALUE;
        }

        if(bill <= TWENTY_FOUR_HOUR) {
            bill = ONE_HOURS_VALUE;
            int hours = (int) (minutes/ONE_HOUR);
            System.out.println(hours);
            for(int i = 0; i < hours; i++) {
                bill += ADDITIONAL_PER_HOUR_VALUE;
            }

            return bill;
        }

        int days = (int) (minutes / TWENTY_FOUR_HOUR);
        System.out.println(days);
        for (int i = 0; i < days; i++) {
            bill += DAY_VALUE;
        }
        return  bill;
    }
}
