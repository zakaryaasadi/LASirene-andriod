package Utilities.Service;

import com.shahbatech.apiwrapper.model.Service;

import java.util.List;

import Utilities.Helper.Cache;

public class ServiceService {

    private static ServiceService mObject;

    public static ServiceService Create(){
        if(mObject != null)
            return mObject;
        mObject = new ServiceService();
        return mObject;
    }

    public Service getServiceById(List<Service> serviceList, int Id){
        for (Service i : serviceList) {
            if(i.getId() == Id)
                return i;
        }

        return null;
    }
}
