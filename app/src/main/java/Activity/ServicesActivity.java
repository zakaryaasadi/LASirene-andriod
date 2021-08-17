package Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shahbatech.apiwrapper.connection.RequestHelper;
import com.shahbatech.apiwrapper.model.ServiceList;
import com.lasirene.R;

import Adapter.ServicesAdapter;
import Utilities.Adapter.EndlessRecyclerViewScrollListener;
import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesActivity extends BaseRecycleViewActivity<ServiceList>
        implements IFetchService<ServiceList>, SwipeRefreshLayout.OnRefreshListener {

    private ServicesAdapter bAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_services);
        super.onCreate(savedInstanceState);

        setToolbarTitle(R.string.services);

        swipe.setOnRefreshListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new ServicesAdapter(this);
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

        Call<ServiceList> call = getCall(1);


        call.enqueue(new Callback<ServiceList>() {
            @Override
            public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                progressBarCenter.setVisibility(View.GONE);
                ServiceList resp = response.body();
                if(resp != null){
                    if(!resp.getServices().isEmpty()){
                        bAdapter.addAll(resp.getServices());
                    }else{
                        noItemFoundView.setVisibility(View.VISIBLE);
                    }
                }
            }


            @Override
            public void onFailure(Call<ServiceList> call, Throwable t) {
                progressBarCenter.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void refreshFetchDataFromApi() {
        Call<ServiceList> call = getCall(1);

        call.enqueue(new Callback<ServiceList>() {
            @Override
            public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                swipe.setRefreshing(false);
                ServiceList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getServices().isEmpty()){
                    bAdapter.renewData(resp.getServices());
                }
            }


            @Override
            public void onFailure(Call<ServiceList> call, Throwable t) {
                swipe.setRefreshing(false);
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(ServicesActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void fetchDataFromApi(int page) {
        Call<ServiceList> call = getCall(page);

        call.enqueue(new Callback<ServiceList>() {
            @Override
            public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                bAdapter.hideLoadingMore();
                ServiceList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getServices().isEmpty()){
                    bAdapter.addAll(resp.getServices());
                }
            }


            @Override
            public void onFailure(Call<ServiceList> call, Throwable t) {
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(ServicesActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        scrollListener.resetState();
        refreshFetchDataFromApi();
    }


    private Call<ServiceList> getCall(int page) {
        return Cache.customerTypeId ==  Defaults.GENTS ? api.getGentsServices(Cache.LANGUAGE_ID, page)
                : api.getLadiesServices(Cache.LANGUAGE_ID, page);
    }
}
