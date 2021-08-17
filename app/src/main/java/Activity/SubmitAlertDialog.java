package Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.shahbatech.apiwrapper.connection.ApiClient;
import com.shahbatech.apiwrapper.connection.RequestFactory;
import com.shahbatech.apiwrapper.model.Booking;
import com.shahbatech.apiwrapper.model.BookingDetail;
import com.lasirene.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import Model.CartModel;
import Utilities.Helper.Cache;
import Utilities.Helper.Defaults;
import Utilities.Service.CustomerService;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmitAlertDialog implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private AlertDialog.Builder  builder;
    private Activity activity;
    private View view;
    private String[] ladies = {"JBR", "Al Wasl"};
    private String[] gents = {"Burj Al Arab"};


    private RadioGroup location;
    private DatePickerDialog dpd;
    private TextView date;
    private TextView time;
    private TimePickerDialog tpd;
    private Calendar now;

    SubmitAlertDialog(Activity activity){
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public AlertDialog create(){
        builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.submit_alert, null);

        now = Calendar.getInstance();

        initView();
        initCalender();

        builder.setView(view);

        builder.setPositiveButton("OK", (dialog, which) -> {
            RadioButton rd = view.findViewById(location.getCheckedRadioButtonId());
            submit(rd.getText().toString());
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }


    private void submit(String location){




        Booking booking = new Booking();
        booking.customer_id = CustomerService.Create().getCustomer(activity).getId();
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
                .setContext(activity)
                .setMessage(R.string.please_wait)
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
                    activity.finish();
                }else{
                    Toast.makeText(activity, activity.getResources().getString(R.string.email_or_password_incorrent), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, activity.getResources().getString(R.string.please_check_your_connection), Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {
        date = view.findViewById(R.id.date);
        date.setText(now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" +  now.get(Calendar.DAY_OF_MONTH));
        time = view.findViewById(R.id.time);
        time.setText(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        location = view.findViewById(R.id.location);

        String[] arr = Cache.customerTypeId == Defaults.GENTS ? gents : ladies;

        for(int i = 0; i < arr.length; i++){
            RadioButton r = createRadioButton(arr[i]);
            r.setChecked(i == 0);
            location.addView(r);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private RadioButton  createRadioButton(String str){
        RadioButton rdbtn = new RadioButton(activity);
        rdbtn.setId(View.generateViewId());
        rdbtn.setText(str);
        rdbtn.setTextColor(activity.getResources().getColor(R.color.white));
        return rdbtn;
    }

    private void initCalender() {

        dpd = DatePickerDialog.newInstance(this);
        dpd.setTitle("Choose date");
        dpd.setMinDate(now);

        tpd = TimePickerDialog.newInstance(this, true);
        tpd.setTitle("Choose time");


        date.setOnClickListener(v -> {
            dpd.show(((AppCompatActivity) activity).getSupportFragmentManager(), "TAG");
        });

        time.setOnClickListener(v -> {
            tpd.show(((AppCompatActivity) activity).getSupportFragmentManager(), "TAG");
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        this.date.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String txtHour = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
        String txtMinute = minute < 10 ? "0" + minute : String.valueOf(minute);
        String t = txtHour+":"+txtMinute;
        time.setText(t);
    }
}
