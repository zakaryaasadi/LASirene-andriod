package com.shahbatech.apiwrapper.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseModel<T> {
    @SerializedName(value = JsonConstants.ID)
    private int id;

    @SerializedName(value = JsonConstants.CREATED_AT)
    private Date createdAt;

    public int getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    protected String checkString(String str){
        if(!TextUtils.isEmpty(str))
            return str;

        return "";
    }

    protected List<T> checkList(List<T> list){
        if(list != null)
            return list;

        return new ArrayList<>();
    }
}
