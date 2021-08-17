package Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shahbatech.apiwrapper.connection.RequestHelper;
import com.shahbatech.apiwrapper.model.NewsList;
import com.lasirene.R;

import Adapter.NewsAdapter;
import Utilities.Adapter.EndlessRecyclerViewScrollListener;
import Utilities.Helper.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends BaseRecycleViewActivity<NewsList>
        implements IFetchService<NewsList>, SwipeRefreshLayout.OnRefreshListener {

    private NewsAdapter bAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_news);
        super.onCreate(savedInstanceState);

        setToolbarTitle( R.string.news);

        swipe.setOnRefreshListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new NewsAdapter(this);
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

        Call<NewsList> call = getCall(1);


        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                progressBarCenter.setVisibility(View.GONE);
                NewsList resp = response.body();
                if(resp != null){
                    if(!resp.getNews().isEmpty()){
                        bAdapter.addAll(resp.getNews());
                    }else{
                        noItemFoundView.setVisibility(View.VISIBLE);
                    }
                }
            }


            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                progressBarCenter.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void refreshFetchDataFromApi() {
        Call<NewsList> call = getCall(1);

        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                swipe.setRefreshing(false);
                NewsList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getNews().isEmpty()){
                    bAdapter.renewData(resp.getNews());
                }
            }


            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                swipe.setRefreshing(false);
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(NewsActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void fetchDataFromApi(int page) {
        Call<NewsList> call = getCall(page);

        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                bAdapter.hideLoadingMore();
                NewsList resp = response.body();
                resultRequestHelper = null;
                if(resp != null && !resp.getNews().isEmpty()){
                    bAdapter.addAll(resp.getNews());
                }
            }


            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                resultRequestHelper = new RequestHelper(this, call);
                Toast.makeText(NewsActivity.this, R.string.cannot_refresh, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        scrollListener.resetState();
        refreshFetchDataFromApi();
    }


    private Call<NewsList> getCall(int page) {
        return api.getNews(Cache.LANGUAGE_ID, page);
    }
}
