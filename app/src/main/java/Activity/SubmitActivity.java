package Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.shahbatech.apiwrapper.connection.ApiClient;
import com.shahbatech.apiwrapper.connection.RequestFactory;
import com.shahbatech.apiwrapper.model.Booking;
import com.shahbatech.apiwrapper.model.BookingDetail;
import com.lasirene.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Model.CartModel;
import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;
import Utilities.Service.CustomerService;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitActivity extends BaseToolbarActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private String[] ladies = {"JBR", "Al Wasl"};
    private String[] gents = {"Burj Al Arab"};


    private RadioGroup location;
    private DatePickerDialog dpd;
    private TextView date;
    private TextView time;

    private TimePickerDialog tpd;
    private Calendar now;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_submit);
        super.onCreate(savedInstanceState);

        setToolbarTitle(R.string.appointment);


        now = Calendar.getInstance();

        ((ImageView) findViewById(R.id.location_image)).setImageDrawable(getResources().getDrawable(Cache.customerTypeId == Defaults.GENTS ? R.drawable.bg_location_gents : R.drawable.bg_location_ladies));

        initView();
        initCalender();

        findViewById(R.id.submit).setOnClickListener(v -> {
            RadioButton rd = findViewById(location.getCheckedRadioButtonId());
            submit(rd.getText().toString());
        });

        findViewById(R.id.add_more).setOnClickListener(v -> {
            Intent i = new Intent(SubmitActivity.this, ServicesActivity.class);
            startActivity(i);
            finish();
        });

    }


    private void submit(String location){

        Booking booking = new Booking();
        booking.customer_id = CustomerService.Create().getCustomer(this).getId();
        booking.location = location;
        booking.date = date.getText().toString() + " " + time.getText().toString();
        booking.details = new ArrayList<>();

        for(CartModel c : Cache.cartList){
            BookingDetail d = new BookingDetail();
            d.service_id = c.getService().getId();
            d.product_id = c.getProduct().getId();
            d.product_detail_id = c.getProductDetail().getId();
            booking.details.add(d);
        }


        AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(SubmitActivity.this)
                .setMessage(R.string.please_wait)
                .setTheme(R.style.Custom)
                .build();

        ApiClient api = RequestFactory.createService(ApiClient.class);
        Call<Booking> call = api.addBooking(booking);

        dialog.show();

        call.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                dialog.dismiss();
                Booking resp = response.body();
                if(resp != null){
                    Cache.cartList.clear();
                    Intent i = new Intent(SubmitActivity.this, BookingActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(SubmitActivity.this, getResources().getString(R.string.please_check_your_connection), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(SubmitActivity.this, getResources().getString(R.string.please_check_your_connection), Toast.LENGTH_LONG).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {
        if(now.get(Calendar.HOUR_OF_DAY) > 21){
            now.add(Calendar.DAY_OF_WEEK, 1);
        }
        if(now.get(Calendar.HOUR_OF_DAY) < 10 || now.get(Calendar.HOUR_OF_DAY) > 21){
            now.set(Calendar.HOUR_OF_DAY, 10);
        }
        date = findViewById(R.id.date);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date.setText(dateFormat.format(now.getTime()));
        time = findViewById(R.id.time);
        if( now.get(Calendar.MINUTE) < 10){
            time.setText(now.get(Calendar.HOUR_OF_DAY) + ":0" + now.get(Calendar.MINUTE));
        }else{
            time.setText(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        }
        location = findViewById(R.id.location);

        String[] arr = Cache.customerTypeId == Defaults.GENTS ? gents : ladies;

        for(int i = 0; i < arr.length; i++){
            RadioButton r = createRadioButton(arr[i]);
            r.setChecked(i == 0);
            location.addView(r);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private RadioButton  createRadioButton(String str){
        RadioButton rdbtn = new RadioButton(this);
        rdbtn.setId(View.generateViewId());
        rdbtn.setText(str);
        rdbtn.setTextColor(this.getResources().getColor(R.color.white));
        return rdbtn;
    }

    private void initCalender() {

        dpd = DatePickerDialog.newInstance(this);
        dpd.setTitle("Choose date");
        dpd.setMinDate(now);
        dpd.setAccentColor(getResources().getColor(R.color.black_opacity));

        dpd.setDisabledDays(getWeekend());

        tpd = TimePickerDialog.newInstance(this, true);
        tpd.setTitle("Choose time");
        tpd.setAccentColor(getResources().getColor(R.color.black_opacity));

        if(now.get(Calendar.HOUR_OF_DAY) > 21){
            now.add(Calendar.DAY_OF_MONTH, 1);
            dpd.setMinDate(now);
        }


        tpd.setMinTime(10, 0, 0);
        tpd.setMaxTime(21, 0, 0);


        findViewById(R.id.layout_date).setOnClickListener(v -> {
            dpd.show(getSupportFragmentManager(), "TAG");
        });

        findViewById(R.id.layout_time).setOnClickListener(v -> {
            tpd.show(getSupportFragmentManager(), "TAG");
        });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //String date = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, 0, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.date.setText(dateFormat.format(c.getTime()));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String txtHour = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
        String txtMinute = minute < 10 ? "0" + minute : String.valueOf(minute);
        String t = txtHour+":"+txtMinute;
        time.setText(t);
    }


    private Calendar[] getWeekend(){
        List<Calendar> calendars = new ArrayList<>();
        for(int i = 0; i < 365; i++){
            Calendar next = Calendar.getInstance();
            next.add(Calendar.DAY_OF_YEAR, i);
           int day = next.get(Calendar.DAY_OF_WEEK);
           if(day == 6){
               calendars.add(next);
           }
        }
        Calendar[] calendars1 = new Calendar[calendars.size()];
        return calendars.toArray(calendars1);
    }
}
