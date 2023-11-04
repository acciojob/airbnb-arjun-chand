package com.driver.controllers;


import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer, User> userDb = new HashMap<>();
    HashMap<String,Booking> bookingDb = new HashMap<>();
    HashMap<Integer,Integer> countOfBookings = new HashMap<>();




    public String addHotel(Hotel hotel){
        if (hotel == null || hotel.getHotelName() == null){
            return "FAILURE";
        }
        if(hotelDb.containsKey(hotel.getHotelName())){
            return"FAILURE";
        }
        hotelDb.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public int addUser(User user){
        userDb.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String maxHotelFacility(){
        int hotelFacility = 0;
        String hotelName = "";
        for (Hotel hotel : hotelDb.values()){
            if(hotel.getFacilities().size() > hotelFacility){
                hotelFacility = hotel.getFacilities().size();
                hotelName = hotel.getHotelName();
            }else if(hotel.getFacilities().size() == hotelFacility){
                if (hotel.getHotelName().compareTo(hotelName) < 0){
                    hotelName  = hotel.getHotelName();
                }
            }
        }
        return hotelName;
    }

    public int bookRoom(Booking booking){
        String key = UUID.randomUUID().toString();

        booking.setBookingId(key);

        String hotelName = booking.getHotelName();

        Hotel hotel = hotelDb.get(hotelName);

        int availableRooms = hotel.getAvailableRooms();

        if(availableRooms<booking.getNoOfRooms()){
            return -1;
        }

        int amountToBePaid = hotel.getPricePerNight()*booking.getNoOfRooms();
        booking.setAmountToBePaid(amountToBePaid);

        //Make sure we check this part of code as well
        hotel.setAvailableRooms(hotel.getAvailableRooms()-booking.getNoOfRooms());

        bookingDb.put(key,booking);

        hotelDb.put(hotelName,hotel);

        int aadharCard = booking.getBookingAadharCard();
        Integer currentBookings = countOfBookings.get(aadharCard);
        countOfBookings.put(aadharCard, Objects.nonNull(currentBookings)?1+currentBookings:1);
        return amountToBePaid;

    }

    public int getBookingByAPerson(int aadharCard){
        return countOfBookings.get(aadharCard);
    }
    public Hotel updateFacility(List<Facility> newFacilities, String hotelName){
        List<Facility> oldFacilities = hotelDb.get(hotelName).getFacilities();

        for(Facility facility: newFacilities){

            if(oldFacilities.contains(facility)){
                continue;
            }else{
                oldFacilities.add(facility);
            }
        }

        Hotel hotel = hotelDb.get(hotelName);
        hotel.setFacilities(oldFacilities);

        hotelDb.put(hotelName,hotel);

        return hotel;

    }
}
