package Utilities.Service;

import com.shahbatech.apiwrapper.model.Product;
import com.shahbatech.apiwrapper.model.Service;

import java.util.List;

public class ProductService {

    private static ProductService mObject;

    public static ProductService Create(){
        if(mObject != null)
            return mObject;
        mObject = new ProductService();
        return mObject;
    }

    public Product getProductById(List<Product> productList, int Id){
        for (Product i : productList) {
            if(i.getId() == Id)
                return i;
        }

        return null;
    }
}
