package Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lasirene.R;

import PopUp.MenuPopup;
import Utilities.Helper.Cache;

public class BaseToolbarActivity extends BaseActivity {

    private TextView toolbarTitle;
    private ImageView dotCart;
    private ImageView menu, btnBack;
    private RelativeLayout cart;

    BaseToolbarActivity(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbarTitle = findViewById(R.id.title);
        cart = findViewById(R.id.cart);
        dotCart = findViewById(R.id.dot_cart);
        menu = findViewById(R.id.menu);
        btnBack = findViewById(R.id.btn_back);

        if(cart != null){
            cart.setOnClickListener(v->{
                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(i);
            });
        }


        if(menu != null){
            menu.setOnClickListener(v ->{
                new MenuPopup(BaseToolbarActivity.this, v);
            });
        }

        if(btnBack != null){
            btnBack.setOnClickListener(v ->{
                finish();
            });
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        showDotCart();
    }

    protected void setToolbarTitle(int id){
        toolbarTitle.setText(id);
    }

    protected void setToolbarTitle(String title){
        toolbarTitle.setText(title);
    }


    public void showDotCart(){
        if(dotCart != null)
            dotCart.setVisibility(Cache.cartList.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
