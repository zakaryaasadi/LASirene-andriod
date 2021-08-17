package com.shahbatech.apiwrapper.model;

import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;

import java.util.List;


public class ProductList extends BaseModel
{
    @SerializedName(value = JsonConstants.PRODUCTS)
    private List<Product> products;

    public List<Product> getProducts() {
        return checkList(products);
    }
}
