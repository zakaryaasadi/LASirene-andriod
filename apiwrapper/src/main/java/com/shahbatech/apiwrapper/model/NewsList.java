package com.shahbatech.apiwrapper.model;

import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;
import java.util.List;


public class NewsList extends BaseModel
{
    @SerializedName(value = JsonConstants.NEWS)
    private List<News> news;


    public List<News> getNews() {
        return checkList(news);
    }
}
