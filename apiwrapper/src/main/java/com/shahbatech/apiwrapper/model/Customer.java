package com.shahbatech.apiwrapper.model;

import com.google.gson.annotations.SerializedName;
import com.shahbatech.apiwrapper.json.JsonConstants;

import java.util.Date;

public class Customer extends BaseModel
{

    @SerializedName(value = JsonConstants.EMAIL)
    private String email;

    @SerializedName(value = JsonConstants.FULL_NAME)
    private String fullName;

    @SerializedName(value = JsonConstants.PHONE)
    private String phone;

    @SerializedName(value = JsonConstants.GENDER)
    private int gender;

    @SerializedName(value = JsonConstants.DATE_OF_BIRTH)
    private Date dateOfBirth;

    public String getEmail() {
        return checkString(email);
    }

    public String getFullName() {
        return checkString(fullName);
    }

    public String getPhone() {
        return checkString(phone);
    }

    public int getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
