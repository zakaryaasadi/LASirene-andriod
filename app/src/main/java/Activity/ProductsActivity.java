package Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shahbatech.apiwrapper.connection.RequestHelper;
import com.shahbatech.apiwrapper.model.ProductList;
import com.lasirene.R;

import Adapter.ProductsAdapter;
import Utilities.Adapter.EndlessRecyclerViewScrollListener;
import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends BaseRecycleViewActivity<ProductList>
        implements IFetchService<ProductList>, SwipeRefreshLayout.OnRefreshListener {

    private int serviceId;
    private ProductsAdapter bAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_products);
        super.onCreate(savedInstanceState);

        setToolbarTitle( getIntent().getStringExtra(Defaults.NAME));
        serviceId = getIntent().getIntExtra(Defaults.ID, 0);

        swipe.setOnRefreshListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new ProductsAdapter(this);
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

        Call<ProductList> call = getCall(1);


        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                progressBarCenter.setVisibility(View.GONE);
                ProductList resp = response.body();
                if(resp != null){
                    if(!resp.getProducts().isEmpty()){
                        bAdapter.addAll(resp.getProducts());
                    }else{
                        noItemFoundView.setVisibility(View.VISIBLE);
                    }
                }
            }


            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                progressBarCenter.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void refreshFetchDataFromApi() {
        Call<ProductList> call = getCall(1);

        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                swipe.setRefreshing(false);
                ProductList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getProducts().isEmpty()){
                    bAdapter.renewData(resp.getProducts());
                }
            }


            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                swipe.setRefreshing(false);
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(ProductsActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void fetchDataFromApi(int page) {
        Call<ProductList> call = getCall(page);

        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                bAdapter.hideLoadingMore();
                ProductList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getProducts().isEmpty()){
                    bAdapter.addAll(resp.getProducts());
                }
            }


            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(ProductsActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        scrollListener.resetState();
        refreshFetchDataFromApi();
    }


    private Call<ProductList> getCall(int page) {
        return api.getProducts(serviceId, Cache.LANGUAGE_ID, page);
    }
}
