package Utilities.Service;


import android.app.Activity;
import android.content.Context;

import com.shahbatech.apiwrapper.model.Customer;

import Utilities.Helper.JsonStorageHelper;

public class CustomerService {
    private static CustomerService mObject;
    private static Customer customer;

    public static CustomerService Create(){
        if(mObject != null)
            return mObject;
        mObject = new CustomerService();
        return mObject;
    }


    public void save(Customer customer, Context context){
        JsonStorageHelper jsonStorageHelper = new JsonStorageHelper(context);
        jsonStorageHelper.save(customer);
        this.customer = customer;
    }

    public Customer getCustomer(Activity activity){
        return getCustomer((Context) activity);
    }

    public Customer getCustomer(Context context){

        if(customer != null){
            return customer;
        }

        JsonStorageHelper jsonStorageHelper = new JsonStorageHelper(context);
        customer = jsonStorageHelper.load(Customer.class);
        if(customer != null){
            return customer;
        }

        return null;
    }

    public void remove(Context context) {
        JsonStorageHelper jsonStorageHelper = new JsonStorageHelper(context);
        jsonStorageHelper.remove(Customer.class);
        if(customer != null){
            customer = null;
        }
    }
}
