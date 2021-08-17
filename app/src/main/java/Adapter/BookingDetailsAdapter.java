package Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahbatech.apiwrapper.model.BookingDetail;
import com.lasirene.R;

import java.util.ArrayList;
import java.util.List;

import Utilities.Helper.CurrencyRound;


public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.MyViewHolder>{

    private Context context;
    private List<BookingDetail> OfferList = new ArrayList<>();


    public BookingDetailsAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<BookingDetail> categories) {
        OfferList = categories;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView serviceName, productName, productDetailName, price, newPrice;

        public MyViewHolder(View view) {
            super(view);

            serviceName = view.findViewById(R.id.service_name);
            price = view.findViewById(R.id.price);
            newPrice = view.findViewById(R.id.new_price);
            productName = view.findViewById(R.id.product_name);
            productDetailName = view.findViewById(R.id.product_detail_name);
        }

    }



    @Override
    public BookingDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking_detail, parent, false);

        return new BookingDetailsAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final BookingDetailsAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        BookingDetail item = OfferList.get(position);

        holder.serviceName.setText(item.getServiceName());

        holder.productName.setText(item.getProductName());

        holder.productDetailName.setText(item.getProductDetailName());

        String price = "";
        if(item.getFromPrice() == item.getToPrice())
            price = CurrencyRound.rounded(item.getFromPrice());
        else
            price = CurrencyRound.rounded(item.getFromPrice()) + " - " + CurrencyRound.rounded(item.getToPrice());

        holder.price.setText(price);

        if(item.getOffer() > 0){
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if(item.getFromPrice() == item.getToPrice())
                price = CurrencyRound.rounded(item.getFromPrice() * (1 - item.getOffer() / 100.0));
            else
                price = CurrencyRound.rounded(item.getFromPrice() * (1 - item.getOffer() / 100.0)) + " - " + CurrencyRound.rounded(item.getToPrice() * (1 - item.getOffer() / 100.0));
            holder.newPrice.setText(price);
            holder.newPrice.setVisibility(View.VISIBLE);
        }else{
            holder.newPrice.setVisibility(View.GONE);
        }

    }



    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}


