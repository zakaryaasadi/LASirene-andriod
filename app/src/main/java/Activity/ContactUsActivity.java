package Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lasirene.R;

import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;
import Utilities.Service.CustomerService;

public class ContactUsActivity extends BaseActivity {

    private static final String PHONE_NUMBER_JBR = "+97143994612";
    private static final String PHONE_NUMBER_4_SEASONS= "+97142707706";
    private static final String PHONE_NUMBER__BURJ_AL_ARAB = "+97143386518";
    private static final String PHONE_NUMBER_AL_WASL = "+97143999173";

    private static final String LOCATION_JBR = "https://www.google.com/maps/place/La+Sirene+Beauty+%26+Spa+JBR/@25.07966,55.136592,13z/data=!4m5!3m4!1s0x0:0xe01492ce8c97f9cc!8m2!3d25.0796852!4d55.1365004?hl=en";
    private static final String LOCATION_4_SEASONS= "https://www.google.com/maps/place/Four+Seasons+Resort+Dubai+at+Jumeirah+Beach/@25.202449,55.239653,20z/data=!4m8!3m7!1s0x0:0xaa5a79bf915ce189!5m2!4m1!1i2!8m2!3d25.20238!4d55.2397672?hl=en";
    private static final String LOCATION_BURJ_AL_ARAB = "https://www.google.com/maps/place/La+Sirene+gents+villa/@25.137732,55.189292,13z/data=!4m5!3m4!1s0x0:0x33572b6acc989b05!8m2!3d25.1377399!4d55.1892799?hl=en&shorturl=1";
    private static final String LOCATION_AL_WASL = "https://www.google.com/maps/place/La+Sirene+Salon+-+Al+Wasl/@25.21899,55.261557,11z/data=!4m5!3m4!1s0x0:0xc00a744a8c65b8bc!8m2!3d25.2189904!4d55.2615567?hl=en&shorturl=1";

    private static final String WEB = "https://lasirenegroup.ae";
    private static final String EMAIL = "info@lasirenegroup.ae";
    private static final String FACEBOOK = "https://www.facebook.com/lasirenegroup";
    private static final String INSTAGRAM = Cache.customerTypeId == Defaults.LADIES ?
            "https://www.instagram.com/lasirenegroup" : "https://www.instagram.com/lasirenegentsgroup/";
    private  static final  String SNAPCHAT = "https://www.snapchat.com/add/lasirene477?share_id=NzlCRjIz&locale=en_AE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        findViewById(R.id.jbr).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + PHONE_NUMBER_JBR));
            startActivity(intent);
        });

        findViewById(R.id.loc_jbr).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LOCATION_JBR));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });

        findViewById(R.id.four_season).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + PHONE_NUMBER_4_SEASONS));
            startActivity(intent);
        });

        findViewById(R.id.loc_four_seasons).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LOCATION_4_SEASONS));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });

        findViewById(R.id.al_wasl).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + PHONE_NUMBER_AL_WASL));
            startActivity(intent);
        });

        findViewById(R.id.loc_al_wasl).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LOCATION_AL_WASL));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });

        findViewById(R.id.burj_al_arab).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + PHONE_NUMBER__BURJ_AL_ARAB));
            startActivity(intent);
        });

        findViewById(R.id.loc_burj_al_arab).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LOCATION_BURJ_AL_ARAB));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });


        findViewById(R.id.burj_al_arab).setVisibility(Cache.customerTypeId == Defaults.GENTS ? View.VISIBLE : View.GONE);

        findViewById(R.id.contact_ladies).setVisibility(Cache.customerTypeId == Defaults.LADIES ? View.VISIBLE : View.GONE);

        findViewById(R.id.location_gents).setVisibility(Cache.customerTypeId == Defaults.GENTS ? View.VISIBLE : View.GONE);

        findViewById(R.id.location_ladies).setVisibility(Cache.customerTypeId == Defaults.LADIES ? View.VISIBLE : View.GONE);

        findViewById(R.id.web).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEB));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });


        findViewById(R.id.email).setOnClickListener(v -> {
            String[] addresses = {EMAIL};
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Information request");
            intent.putExtra(Intent.EXTRA_TEXT, "Hi \n\n I am " + CustomerService.Create().getCustomer(this).getFullName());
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });



        findViewById(R.id.facebook).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });

        findViewById(R.id.instagram).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });

        findViewById(R.id.snapchat).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SNAPCHAT));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });
    }
}
