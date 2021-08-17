package com.shahbatech.apiwrapper.model;


import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;


public class Service extends BaseModel
{
    @SerializedName(value = JsonConstants.NAME)
    private String name;

    @SerializedName(value = JsonConstants.IMAGE)
    private String image;

    public String getName() {
        return checkString(name);
    }


    public String getImage() {
        return checkString(image);
    }

}
