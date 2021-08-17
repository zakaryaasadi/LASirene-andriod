package Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.novoda.merlin.Connectable;
import com.shahbatech.apiwrapper.connection.RequestHelper;
import com.lasirene.R;

import Utilities.Adapter.EndlessRecyclerViewScrollListener;

public class BaseRecycleViewActivity<T> extends BaseToolbarActivity implements Connectable {


    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipe;
    protected SpinKitView progressBarCenter;
    protected EndlessRecyclerViewScrollListener scrollListener;
    protected View noNetworkView;
    protected View noItemFoundView;
    protected LinearLayout tryAgain;
    protected RequestHelper<T> resultRequestHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initView();

        merlin.registerConnectable(this);
        
    }

    private void initView(){
        progressBarCenter = findViewById(R.id.progressBarCenter);
        swipe = findViewById(R.id.refresh_layout);
        noNetworkView = findViewById(R.id.no_network_view);
        noItemFoundView = findViewById(R.id.no_item_found_view);
        tryAgain = findViewById(R.id.try_again);
        recyclerView = findViewById(R.id.recycler_view);
    }


    @Override
    public void onConnect() {
        if(resultRequestHelper != null){
            resultRequestHelper.call
                    .clone()
                    .enqueue(resultRequestHelper.callback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        merlin.bind();
    }

    @Override
    public void onPause() {
        merlin.unbind();
        super.onPause();
    }
}
