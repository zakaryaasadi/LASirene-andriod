package Activity;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shahbatech.apiwrapper.model.BookingDetail;
import com.lasirene.R;

import java.util.Arrays;
import java.util.List;

import Adapter.BookingDetailsAdapter;
import Utilities.Helper.Defaults;

public class BookingDetailsActivity extends BaseToolbarActivity {

    private BookingDetailsAdapter bAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_booking_details);
        super.onCreate(savedInstanceState);
        setToolbarTitle( R.string.booking_details);

        String strJson = getIntent().getStringExtra(Defaults.JSON);

        BookingDetail[] arr = new Gson().fromJson(strJson, BookingDetail[].class);
        List<BookingDetail> list = Arrays.asList(arr);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new BookingDetailsAdapter(this);
        recyclerView.setAdapter(bAdapter);
        bAdapter.addAll(list);

    }


}
