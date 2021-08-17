package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shahbatech.apiwrapper.model.Booking;
import com.shahbatech.apiwrapper.model.Customer;
import com.lasirene.R;

import java.util.ArrayList;
import java.util.List;

import Activity.BookingDetailsActivity;
import Utilities.Helper.CustomDate;
import Utilities.Helper.Defaults;
import Utilities.Service.CustomerService;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder>
            implements IDataAdapter<Booking>, IProgressbarAdapter {


    private Context context;
    private List<Booking> OfferList = new ArrayList<>();


    public BookingsAdapter(Context context) {
        this.context = context;
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


        TextView customerName, bookingId, createdAt, location, statusText, date;
        LinearLayout status;

        public DataViewHolder(View view) {
            super(view);

            customerName = view.findViewById(R.id.customer_name);
            bookingId = view.findViewById(R.id.booking_id);
            createdAt = view.findViewById(R.id.created_at);
            date = view.findViewById(R.id.date);
            location = view.findViewById(R.id.location);
            status = view.findViewById(R.id.status);
            statusText = view.findViewById(R.id.status_text);
        }

    }

    @NonNull
    @Override
    public BookingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType == VIEW_TYPE_ITEM){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booking, parent, false);
            return new BookingsAdapter.DataViewHolder(itemView);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_more, parent, false);
            return new BookingsAdapter.ProgressViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingsAdapter.MyViewHolder myViewHolder, int position) {
        if(myViewHolder instanceof ProgressViewHolder)
            return;

        DataViewHolder holder = (DataViewHolder) myViewHolder;
        holder.setIsRecyclable(false);
        Booking item = OfferList.get(position);
        Customer customer = CustomerService.Create().getCustomer(context);

        holder.customerName.setText(customer.getFullName());
        holder.bookingId.setText(item.getId() + "");
        String date = CustomDate.format(item.getCreatedAt());
        holder.createdAt.setText(date);

        holder.date.setText(item.getDate());

        holder.location.setText(item.getLocation());

        if(!item.getIsApproved()){
            holder.statusText.setText(R.string.pending);
            holder.status.setBackground(context.getResources().getDrawable(R.drawable.yellow_rect));
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.status_anim);
            holder.status.startAnimation(animation);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(context, BookingDetailsActivity.class);
            String json = new Gson().toJson(item.getDetails());
            i.putExtra(Defaults.JSON, json);
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
    public void addAll(List<Booking> data) {
        OfferList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void renewData(List<Booking> data) {
        OfferList = data;
        notifyDataSetChanged();
    }

}


