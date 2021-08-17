package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahbatech.apiwrapper.model.Service;
import com.lasirene.R;
import java.util.ArrayList;
import java.util.List;

import Activity.ProductsActivity;
import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;
import Utilities.Helper.ImageProcessing;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder>
            implements IDataAdapter<Service>, IProgressbarAdapter {


    private Context context;
    private List<Service> OfferList = new ArrayList<>();
    private ImageProcessing mImageProcessing;


    public ServicesAdapter(Context context) {
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


        TextView name;
        ImageView image;

        public DataViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            image = view.findViewById(R.id.image);
        }

    }

    @NonNull
    @Override
    public ServicesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType == VIEW_TYPE_ITEM){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_service, parent, false);
            return new ServicesAdapter.DataViewHolder(itemView);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_more, parent, false);
            return new ServicesAdapter.ProgressViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ServicesAdapter.MyViewHolder myViewHolder, int position) {
        if(myViewHolder instanceof ProgressViewHolder)
            return;

        DataViewHolder holder = (DataViewHolder) myViewHolder;
        holder.setIsRecyclable(false);
        Service item = OfferList.get(position);

        holder.name.setText(item.getName());

        if (!item.getImage().equals(""))
            mImageProcessing.load(holder.image, item.getImage());
        else
            holder.image.setImageResource(R.drawable.no_image_available);


        holder.itemView.setOnClickListener(view -> {
            int id = item.getId();
            String name = item.getName();

            Cache.SERVICE_ID = id;

            Intent i = new Intent(context, ProductsActivity.class);
            i.putExtra(Defaults.ID, id);
            i.putExtra(Defaults.NAME, name);
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
    public void addAll(List<Service> data) {
        Cache.serviceList.addAll(data);
        OfferList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void renewData(List<Service> data) {
        Cache.serviceList = data;
        OfferList = data;
        notifyDataSetChanged();
    }

}


