package Model;

import com.shahbatech.apiwrapper.model.Product;
import com.shahbatech.apiwrapper.model.ProductDetail;
import com.shahbatech.apiwrapper.model.Service;

public class CartModel {
    private Service service;
    private Product product;
    private ProductDetail productDetail;

    public CartModel(Service service, Product product, ProductDetail productDetail) {
        this.service = service;
        this.product = product;
        this.productDetail = productDetail;
    }

    public Service getService() {
        return service;
    }

    public Product getProduct() {
        return product;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }
}
