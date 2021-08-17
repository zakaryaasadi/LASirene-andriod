package Utilities.Helper;

import com.shahbatech.apiwrapper.model.Product;
import com.shahbatech.apiwrapper.model.Service;

import java.util.ArrayList;
import java.util.List;

import Model.CartModel;


public class Cache {
    public static int SERVICE_ID = 0 ;
    public static int PRODUCT_ID = 0;
    // language
    public static int LANGUAGE_ID = 0;

  //  public static int CUSTOMER_ID = 1;

    public static List<Service> serviceList = new ArrayList<>();
    public static List<Product> productList = new ArrayList<>();
    public static List<CartModel> cartList = new ArrayList<>();
    public static int customerTypeId = 0;
}
