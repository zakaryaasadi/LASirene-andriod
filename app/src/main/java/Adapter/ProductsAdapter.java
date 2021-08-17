package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shahbatech.apiwrapper.model.Product;
import com.shahbatech.apiwrapper.model.ProductDetail;
import com.lasirene.R;

import java.util.ArrayList;
import java.util.List;

import Activity.ProductDetailsActivity;
import Utilities.Helper.Cache;
import Utilities.Helper.CurrencyRound;
import Utilities.Helper.CustomDate;
import Utilities.Helper.Defaults;
import Utilities.Helper.ImageProcessing;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>
            implements IDataAdapter<Product>, IProgressbarAdapter {


    private Context context;
    private List<Product> OfferList = new ArrayList<>();
    private ImageProcessing mImageProcessing;


    public ProductsAdapter(Context context) {
        this.context = context;
        mImageProcessing = new ImageProcessing(context);
    }


    public class MyViewHolder  extends RecyclerView.ViewHolder{
        public MyViewHolder(View view) {
            super(view);
        }
    }

    public class ProgressViewHolder extends MyViewHolder {
        public ProgressViewHolder(View view) {
            super(view);
        }
    }

    public class DataViewHolder extends MyViewHolder {


        TextView name, price, time, newPrice;

        public DataViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            newPrice = view.findViewById(R.id.new_price);
            time = view.findViewById(R.id.time);
        }

    }

    @NonNull
    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType == VIEW_TYPE_ITEM){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product, parent, false);
            return new ProductsAdapter.DataViewHolder(itemView);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_more, parent, false);
            return new ProductsAdapter.ProgressViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsAdapter.MyViewHolder myViewHolder, int position) {
        if(myViewHolder instanceof ProgressViewHolder)
            return;

        DataViewHolder holder = (DataViewHolder) myViewHolder;
        holder.setIsRecyclable(false);

        Product item = OfferList.get(position);

        holder.name.setText(item.getName());

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

        String time = CustomDate.time( item.getFromTime() ) + " - " + CustomDate.time( item.getToTime() );
        holder.time.setText(time);

        holder.itemView.setOnClickListener(view -> {
            int id = item.getId();
            String name = item.getName();

            Cache.PRODUCT_ID = id;

            List<ProductDetail> productDetails = item.getProductDetails();
            String strJson = new Gson().toJson(productDetails);

            Intent i = new Intent(context, ProductDetailsActivity.class);
            i.putExtra(Defaults.ID, id);
            i.putExtra(Defaults.NAME, name);
            i.putExtra(Defaults.OFFER, item.getOffer());
            i.putExtra(Defaults.RATING, item.getRating());
            i.putExtra(Defaults.JSON, strJson);
            context.startActivity(i);

        });

    }


    @Override
    public int getItemCount() {
        return OfferList.size();

    }


    @Override
    public int getItemViewType(int position) {
        if (OfferList.get(position) != null)
            return VIEW_TYPE_ITEM;
        else
            return VIEW_TYPE_LOADING;
    }

    @Override
    public void showLoadingMore() {
        if(OfferList.get(OfferList.size() - 1) != null){
            OfferList.add(null);
            notifyItemInserted(OfferList.size() - 1);
        }
    }

    @Override
    public void hideLoadingMore() {
        if(OfferList.get(OfferList.size() - 1) == null){
            OfferList.remove(OfferList.size() - 1);
            notifyItemRemoved(OfferList.size());
        }
    }

    @Override
    public void addAll(List<Product> data) {
        Cache.productList.addAll(data);
        OfferList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void renewData(List<Product> data) {
        Cache.productList = data;
        OfferList = data;
        notifyDataSetChanged();
    }

}


