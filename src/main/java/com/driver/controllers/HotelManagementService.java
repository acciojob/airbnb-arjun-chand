package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelManagementService {
    @Autowired
    HotelManagementRepository repositoryLayerObj;
    public String addHotel(Hotel hotel){
        return repositoryLayerObj.addHotel(hotel);
    }
    public int addUser(User user){
        return repositoryLayerObj.addUser(user);
    }
    public String maxHotelFacility(){
       return repositoryLayerObj.maxHotelFacility();
    }
    public int bookRoom(Booking booking){
        return repositoryLayerObj.bookRoom(booking);
    }
    public int getBookingByAPerson(int aadharCard){
        return repositoryLayerObj.getBookingByAPerson(aadharCard);
    }
}
