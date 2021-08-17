package com.shahbatech.apiwrapper.model;


import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;


public class ProductDetail extends BaseModel
{
    @SerializedName(value = JsonConstants.NAME)
    private String name;

    @SerializedName(value = JsonConstants.FROM_TIME)
    private int fromTime;

    @SerializedName(value = JsonConstants.TO_TIME)
    private int toTime;

    @SerializedName(value = JsonConstants.FROM_PRICE)
    private float fromPrice;

    @SerializedName(value = JsonConstants.TO_PRICE)
    private float toPrice;


    public String getName() {
        return checkString(name);
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
}
