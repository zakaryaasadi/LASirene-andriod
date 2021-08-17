package com.shahbatech.apiwrapper.model;

import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;

import java.util.List;


public class ServiceList extends BaseModel
{
    @SerializedName(value = JsonConstants.SERVICES)
    private List<Service> services;

    public List<Service> getServices() {
        return checkList(services);
    }
}
