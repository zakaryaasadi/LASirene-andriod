package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lasirene.R;

import java.util.Locale;

import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;
import Utilities.Service.CustomerService;


public class StartupActivity extends AppCompatActivity {

    private static String TAG = StartupActivity.class.getName();
    private CustomerService customerService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        Cache.LANGUAGE_ID = Locale.getDefault().getLanguage().equals(Defaults.AR) ? 1 : 0;

        ImageView logo = findViewById(R.id.image);

        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.startup);

        logo.startAnimation(animation);


        ImageView brand = findViewById(R.id.brand);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.alpha);

        brand.startAnimation(animation);


        customerService = CustomerService.Create();
        // Start timer and launch main activity
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();


    }




    private class IntentLauncher extends Thread {

        /**
         * Sleep for some time and than start new activity.
         */
        @Override
        public void run() {
            try {
                // Sleeping
                Thread.sleep(2800);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            startMainActivity();
        }

    }

    private void startMainActivity() {
        if(customerService.getCustomer(this) != null){
            Intent i = new Intent(StartupActivity.this, CustomerTypesActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(StartupActivity.this, WelcomeActivity.class);
            startActivity(i);
        }
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
