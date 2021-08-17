package com.shahbatech.apiwrapper.model;

import java.util.List;

public class Booking extends BaseModel
{
    public int customer_id;

    public String location;

    public String date;

    public int is_approved;

    public List<BookingDetail> details;


    public int getCustomer_id() {
        return customer_id;
    }

    public String getLocation() {
        return checkString(location);
    }

    public String getDate() {
        return checkString(date);
    }

    public boolean getIsApproved() {
        return is_approved == 1;
    }

    public List<BookingDetail> getDetails() {
        return checkList(details);
    }
}
