package Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shahbatech.apiwrapper.model.ProductDetail;
import com.lasirene.R;

import java.util.ArrayList;
import java.util.List;

import Activity.BaseToolbarActivity;
import Utilities.Helper.CurrencyRound;
import Utilities.Helper.CustomDate;
import Utilities.Service.CartService;


public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.MyViewHolder>{

    private Context context;
    private List<ProductDetail> OfferList = new ArrayList<>();
    private int Offer = 0;


    public ProductDetailsAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<ProductDetail> categories, int offer) {
        Offer = offer;
        OfferList = categories;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        CheckBox chkChoice;
        TextView name, price, time, newPrice;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            newPrice = view.findViewById(R.id.new_price);
            time = view.findViewById(R.id.time);
            chkChoice = view.findViewById(R.id.chk_choice);
        }

    }



    @Override
    public ProductDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_detail, parent, false);

        return new ProductDetailsAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final ProductDetailsAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        ProductDetail item = OfferList.get(position);

        holder.name.setText(item.getName());

        String price = "";
        if(item.getFromPrice() == item.getToPrice())
            price = CurrencyRound.rounded(item.getFromPrice());
        else
            price = CurrencyRound.rounded(item.getFromPrice()) + " - " + CurrencyRound.rounded(item.getToPrice());

        holder.price.setText(price);

        if(Offer > 0){
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if(item.getFromPrice() == item.getToPrice())
                price = CurrencyRound.rounded(item.getFromPrice() * (1 - Offer / 100.0));
            else
                price = CurrencyRound.rounded(item.getFromPrice() * (1 - Offer / 100.0)) + " - " + CurrencyRound.rounded(item.getToPrice() * (1 - Offer / 100.0));
            holder.newPrice.setText(price);
            holder.newPrice.setVisibility(View.VISIBLE);
        }else{
            holder.newPrice.setVisibility(View.GONE);
        }

        String time = CustomDate.time( item.getFromTime() ) + " - " + CustomDate.time( item.getToTime() );
        holder.time.setText(time);


        holder.chkChoice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            CartService cartService = CartService.Create();

            if(isChecked){
                cartService.addToCart(item);
            }else{
                cartService.removeFromCart(item);
            }

            ((BaseToolbarActivity) context).showDotCart();
        });

        holder.itemView.setOnClickListener(view -> {
            holder.chkChoice.toggle();
        });

    }



    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}


