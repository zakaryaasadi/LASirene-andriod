package Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shahbatech.apiwrapper.connection.RequestHelper;
import com.shahbatech.apiwrapper.model.BookingsList;
import com.shahbatech.apiwrapper.model.NewsList;
import com.lasirene.R;

import Adapter.BookingsAdapter;
import Utilities.Adapter.EndlessRecyclerViewScrollListener;
import Utilities.Service.CustomerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends BaseRecycleViewActivity<NewsList>
        implements IFetchService<NewsList>, SwipeRefreshLayout.OnRefreshListener {

    private BookingsAdapter bAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_booking);
        super.onCreate(savedInstanceState);

        setToolbarTitle( R.string.my_booking);

        swipe.setOnRefreshListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new BookingsAdapter(this);
        recyclerView.setAdapter(bAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchDataFromApi(page);
                bAdapter.showLoadingMore();
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        firstFetchDataFromApi();


        tryAgain.setOnClickListener(v -> {
            firstFetchDataFromApi();
        });

    }

    @Override
    public void firstFetchDataFromApi() {
        progressBarCenter.setVisibility(View.VISIBLE);
        noNetworkView.setVisibility(View.GONE);

        Call<BookingsList> call = getCall(1);


        call.enqueue(new Callback<BookingsList>() {
            @Override
            public void onResponse(Call<BookingsList> call, Response<BookingsList> response) {
                progressBarCenter.setVisibility(View.GONE);
                BookingsList resp = response.body();
                if(resp != null){
                    if(!resp.getBookings().isEmpty()){
                        bAdapter.addAll(resp.getBookings());
                    }else{
                        noItemFoundView.setVisibility(View.VISIBLE);
                    }
                }
            }


            @Override
            public void onFailure(Call<BookingsList> call, Throwable t) {
                progressBarCenter.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void refreshFetchDataFromApi() {
        Call<BookingsList> call = getCall(1);

        call.enqueue(new Callback<BookingsList>() {
            @Override
            public void onResponse(Call<BookingsList> call, Response<BookingsList> response) {
                swipe.setRefreshing(false);
                BookingsList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getBookings().isEmpty()){
                    bAdapter.renewData(resp.getBookings());
                }
            }


            @Override
            public void onFailure(Call<BookingsList> call, Throwable t) {
                swipe.setRefreshing(false);
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(BookingActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void fetchDataFromApi(int page) {
        Call<BookingsList> call = getCall(page);

        call.enqueue(new Callback<BookingsList>() {
            @Override
            public void onResponse(Call<BookingsList> call, Response<BookingsList> response) {
                bAdapter.hideLoadingMore();
                BookingsList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getBookings().isEmpty()){
                    bAdapter.addAll(resp.getBookings());
                }
            }


            @Override
            public void onFailure(Call<BookingsList> call, Throwable t) {
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(BookingActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        scrollListener.resetState();
        refreshFetchDataFromApi();
    }


    private Call<BookingsList> getCall(int page) {
        return api.getBookings(CustomerService.Create().getCustomer(this).getId(), page);
    }
}
