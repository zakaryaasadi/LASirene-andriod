package Utilities.Service;

import com.shahbatech.apiwrapper.model.Product;
import com.shahbatech.apiwrapper.model.ProductDetail;
import com.shahbatech.apiwrapper.model.Service;


import Model.CartModel;
import Utilities.Helper.Cache;

public class CartService {

    private static CartService mObject;

    public static CartService Create(){
        if(mObject != null)
            return mObject;
        mObject = new CartService();
        return mObject;
    }

    public void addToCart(ProductDetail productDetail){
        if(isExist(productDetail))
            return;

        ServiceService serviceService = ServiceService.Create();
        Service service = serviceService.getServiceById(Cache.serviceList, Cache.SERVICE_ID);

        ProductService productService = ProductService.Create();
        Product product = productService.getProductById(Cache.productList, Cache.PRODUCT_ID);

        Cache.cartList.add(new CartModel(service, product, productDetail));
    }

    public boolean isExist(int Id){
        for(CartModel i : Cache.cartList){
            if(i.getProductDetail() != null && i.getProductDetail().getId() == Id)
                return true;
        }
        return false;
    }

    public boolean isExist(ProductDetail productDetail){
        return isExist(productDetail.getId());
    }


    public void removeFromCart(ProductDetail item) {
        for(CartModel i : Cache.cartList) {
            if (i.getProductDetail() != null && i.getProductDetail().getId() == item.getId()){
                Cache.cartList.remove(i);
                break;
            }
        }
    }
}
