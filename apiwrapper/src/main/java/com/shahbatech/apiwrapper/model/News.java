package com.shahbatech.apiwrapper.model;

import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class News extends BaseModel
{
    @SerializedName(value = JsonConstants.TEXT)
    private String text;

    @SerializedName(value = JsonConstants.IMAGES)
    private List<String> images;


    public String getText() {
        return checkString(text);
    }

    public List<String> getImages() {
        return checkList(images);
    }
}
