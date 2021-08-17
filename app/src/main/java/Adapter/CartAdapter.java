package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lasirene.R;

import Activity.CartActivity;
import Model.CartModel;
import Utilities.Helper.Cache;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private Context context;


    public CartAdapter(Context context) {
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView serviceName, productName, productDetailName, productDescription, delete;
        RatingBar ratingBar;


        public MyViewHolder(View view) {
            super(view);

            serviceName = view.findViewById(R.id.service_name);
            productName = view.findViewById(R.id.product_name);
            productDetailName = view.findViewById(R.id.product_detail_name);
            productDescription = view.findViewById(R.id.product_description);
            delete = view.findViewById(R.id.delete);
            ratingBar = view.findViewById(R.id.rating);
        }

    }



    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);

        return new CartAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        CartModel item = Cache.cartList.get(position);

        holder.serviceName.setText(item.getService().getName());

        holder.productName.setText(item.getProduct().getName());

        holder.productDetailName.setText(item.getProductDetail().getName());

        holder.productDescription.setText(item.getProduct().getDescription());

        holder.ratingBar.setRating(item.getProduct().getRating());

        holder.delete.setOnClickListener(v ->{
            Cache.cartList.remove(item);
            notifyDataSetChanged();
            ((CartActivity) context).statusNoItemView();
        });
    }



    @Override
    public int getItemCount() {
        return Cache.cartList.size();

    }

}


