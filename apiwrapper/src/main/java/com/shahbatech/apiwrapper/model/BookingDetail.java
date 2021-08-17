package com.shahbatech.apiwrapper.model;

public class BookingDetail extends BaseModel
{
    public int service_id;

    public int product_id;

    public int product_detail_id;

    private String service_name;

    private String product_name;

    private  String product_detail_name;

    private double from_price;

    private double to_price;

    private int offer;

    public String getServiceName() {
        return checkString(service_name);
    }

    public String getProductName() {
        return checkString(product_name);
    }

    public String getProductDetailName() {
        return checkString(product_detail_name);
    }

    public double getFromPrice() {
        return from_price;
    }

    public double getToPrice() {
        return to_price;
    }

    public int getOffer(){ return offer; }
}
