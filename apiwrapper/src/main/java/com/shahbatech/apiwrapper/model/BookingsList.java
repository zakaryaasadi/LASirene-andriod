package com.shahbatech.apiwrapper.model;


import java.util.List;


public class BookingsList extends BaseModel
{
    private List<Booking> bookings;


    public List<Booking> getBookings() {
        return checkList(bookings);
    }
}
