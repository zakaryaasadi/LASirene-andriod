package Activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shahbatech.apiwrapper.model.ProductDetail;
import com.lasirene.R;

import java.util.Arrays;
import java.util.List;

import Adapter.ProductDetailsAdapter;
import Utilities.Helper.Defaults;

public class ProductDetailsActivity extends BaseToolbarActivity {

    private ProductDetailsAdapter bAdapter;
    private RecyclerView recyclerView;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_product_details);
        super.onCreate(savedInstanceState);
        rating = findViewById(R.id.rating);
        int r = getIntent().getIntExtra(Defaults.RATING, 5);
        rating.setRating(r);


        setToolbarTitle( getIntent().getStringExtra(Defaults.NAME));
        int offer = getIntent().getIntExtra(Defaults.OFFER, 0);
        String strJson = getIntent().getStringExtra(Defaults.JSON);

        ProductDetail[] arr = new Gson().fromJson(strJson, ProductDetail[].class);
        List<ProductDetail> list = Arrays.asList(arr);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new ProductDetailsAdapter(this);
        recyclerView.setAdapter(bAdapter);
        bAdapter.addAll(list, offer);

    }


}
