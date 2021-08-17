package Activity;

import android.content.Intent;
import android.os.Bundle;

import com.lasirene.R;

import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;

public class CustomerTypesActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_types);


        findViewById(R.id.ladies).setOnClickListener(v -> {
            Intent i = new Intent(CustomerTypesActivity.this, ServicesActivity.class);
            Cache.customerTypeId = Defaults.LADIES;
            startActivity(i);
        });

        findViewById(R.id.gents).setOnClickListener(v -> {
            Intent i = new Intent(CustomerTypesActivity.this, ServicesActivity.class);
            Cache.customerTypeId = Defaults.GENTS;
            startActivity(i);
        });
    }
}
