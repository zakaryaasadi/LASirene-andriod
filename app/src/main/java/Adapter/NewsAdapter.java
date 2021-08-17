package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahbatech.apiwrapper.model.News;
import com.lasirene.R;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import Utilities.Helper.CustomDate;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>
            implements IDataAdapter<News>, IProgressbarAdapter {


    private Context context;
    private List<News> OfferList = new ArrayList<>();


    public NewsAdapter(Context context) {
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


        SliderAdapter bAdapter;
        TextView text, date, seeMore;
        SliderView sliderView;

        public DataViewHolder(View view) {
            super(view);

            text = view.findViewById(R.id.text);
            date = view.findViewById(R.id.date);
            seeMore = view.findViewById(R.id.see_more);
            sliderView = view.findViewById(R.id.slider);
        }

    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType == VIEW_TYPE_ITEM){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new NewsAdapter.DataViewHolder(itemView);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_more, parent, false);
            return new NewsAdapter.ProgressViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.MyViewHolder myViewHolder, int position) {
        if(myViewHolder instanceof ProgressViewHolder)
            return;

        DataViewHolder holder = (DataViewHolder) myViewHolder;
        holder.setIsRecyclable(false);

        News item = OfferList.get(position);
        String date = CustomDate.format(item.getCreatedAt());
        holder.date.setText(date);

        holder.seeMore.setVisibility(wordsCount(item.getText()) > 10 ? View.VISIBLE : View.GONE);
        if(wordsCount(item.getText()) > 10){
            holder.text.setText(firstWords(item.getText()));
        }else{
            holder.text.setText(item.getText());
        }



        if(item.getImages().size() > 0) {
            holder.bAdapter = new SliderAdapter(context);
            holder.sliderView.setSliderAdapter(holder.bAdapter);
            holder.bAdapter.renewItems(item.getImages());
        }



        holder.seeMore.setOnClickListener(v -> {
            String txt = holder.seeMore.getText().toString();
            if(txt.equals(context.getString(R.string.see_more))){
                holder.text.setText(item.getText());
                holder.seeMore.setText(R.string.see_less);
            }else{
                holder.text.setText(firstWords(item.getText()));
                holder.seeMore.setText(R.string.see_more);
            }
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
    public void addAll(List<News> data) {
        OfferList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void renewData(List<News> data) {
        OfferList = data;
        notifyDataSetChanged();
    }


    private int wordsCount(String s){
        return getWords(s).length;
    }

    private String[] getWords(String s){
        String trim = s.trim();
        if (trim.isEmpty())
            return new String[0];
        return trim.split("\\s+");
    }

    private String firstWords(String s){
        String[] words = getWords(s);
        String str = "";
        for(int i = 0; i < 10; i++){
            if(i == 9)
                str += words[i];
            else
                str += words[i] + " ";
        }

        return str + "...";
    }

}


