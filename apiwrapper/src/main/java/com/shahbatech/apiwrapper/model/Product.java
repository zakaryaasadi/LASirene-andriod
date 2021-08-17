package com.shahbatech.apiwrapper.model;


import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;

import java.util.ArrayList;
import java.util.List;


public class Product extends BaseModel
{
    @SerializedName(value = JsonConstants.NAME)
    private String name;

    @SerializedName(value = JsonConstants.DESCRIPTION)
    private String description;

    @SerializedName(value = JsonConstants.FROM_TIME)
    private int fromTime;

    @SerializedName(value = JsonConstants.TO_TIME)
    private int toTime;

    @SerializedName(value = JsonConstants.FROM_PRICE)
    private float fromPrice;

    @SerializedName(value = JsonConstants.TO_PRICE)
    private float toPrice;

    @SerializedName(value = JsonConstants.RATING)
    private int rating;

    @SerializedName(value = JsonConstants.OFFER)
    private int offer;

    @SerializedName(value = JsonConstants.IMAGE)
    private String image;


    @SerializedName(value = JsonConstants.PRODUCT_DETAILS)
    private List<ProductDetail> productDetails;


    public String getName() {
        return checkString(name);
    }

    public String getImage() {
        return checkString(image);
    }


    public int getRating(){
        return rating > 0 && rating < 6 ? rating : 5;
    }

    public String getDescription() {
        return  checkString(description);
    }

    public int getFromTime() {
        return fromTime;
    }

    public int getToTime() {
        return toTime;
    }

    public float getFromPrice() {
        return fromPrice;
    }

    public float getToPrice() {
        return toPrice;
    }

    public int getOffer(){ return offer; }


    public List<ProductDetail> getProductDetails() {
        if(productDetails != null)
            return productDetails;

        return new ArrayList<>();
    }
}
