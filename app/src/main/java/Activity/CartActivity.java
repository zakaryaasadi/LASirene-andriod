package Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lasirene.R;

import Adapter.CartAdapter;
import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;

public class CartActivity extends BaseToolbarActivity {

    private CartAdapter bAdapter;
    private RecyclerView recyclerView;
    private View noItemFoundView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_cart);
        super.onCreate(savedInstanceState);

        setToolbarTitle(R.string.cart);

        noItemFoundView = findViewById(R.id.no_item_found_view);
        findViewById(R.id.next).setOnClickListener(v -> {
            Intent i = new Intent(CartActivity.this, SubmitActivity.class);
            startActivity(i);
            finish();
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new CartAdapter(this);
        recyclerView.setAdapter(bAdapter);

        statusNoItemView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showAlert() {
        SubmitAlertDialog alertDialog = new SubmitAlertDialog(this);
        alertDialog.create().show();
    }



    public void statusNoItemView(){
        noItemFoundView.setVisibility(Cache.cartList.isEmpty() ? View.VISIBLE : View.GONE);
        findViewById(R.id.layout_submit).setVisibility(Cache.cartList.isEmpty() ? View.GONE : View.VISIBLE);
    }




}
