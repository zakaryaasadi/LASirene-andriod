package Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shahbatech.apiwrapper.model.Customer;
import com.lasirene.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import Utilities.Helper.Defaults;
import Utilities.Service.CustomerService;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetailsActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private EditText fullName, phone;
    private TextView dateOfBirth;
    private RadioGroup gender;
    private RadioButton genderButton;
    private CustomerService customerService;
    private Calendar defaultDate;
    private DatePickerDialog dpd;
    private Calendar minDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        initView();

        initCalender();

        customerService = CustomerService.Create();

        AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(R.string.please_wait)
                .setTheme(R.style.Custom)
                .build();

        Intent i = getIntent();

        String txtEmail = i.getStringExtra(Defaults.EMAIL);
        String txtPassword = i.getStringExtra(Defaults.PASSWORD);








        findViewById(R.id.sign_up).setOnClickListener(v -> {

            genderButton = findViewById(gender.getCheckedRadioButtonId());

            hideKeyboard();


            String txtFullName = fullName.getText().toString().trim();
            String txtPhone = phone.getText().toString().trim();
            String txtDateOfBirth = dateOfBirth.getText().toString().trim();
            int numPhone = Integer.valueOf(txtPhone);
            int numGender = genderButton.getText().equals(getResources().getString(R.string.male)) ? 0 : 1;

            boolean isNotValidFullName = txtFullName.equals("");
            boolean isNotValidPhone = txtPhone.equals("");
            boolean isNotValidDate = txtDateOfBirth.equals("");

            if(isNotValidFullName){
                fullName.setError(getResources().getString(R.string.valid_full_name));
            }else{
                fullName.setError(null);
            }

            if(isNotValidPhone){
                phone.setError(getResources().getString(R.string.valid_phone));
            }else if(numPhone < 500000000 || numPhone > 600000000){
                phone.setError(getResources().getString(R.string.your_phone_is_incorrect));
                isNotValidPhone = true;
            }else{
                phone.setError(null);
            }


            if(isNotValidDate) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.valid_date_of_birth), Toast.LENGTH_LONG)
                .show();
            }

            if(isNotValidDate || isNotValidFullName || isNotValidPhone)
                return;

            txtPhone = "+971" + numPhone;

            Call<Customer> call = api.signUp(txtFullName, txtEmail, txtPassword,
                    txtPhone, numGender, txtDateOfBirth);

            dialog.show();

            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    dialog.dismiss();
                    Customer resp = response.body();
                    if(resp != null){
                        customerService.save(resp, PersonalDetailsActivity.this);
                        Intent intent = new Intent(PersonalDetailsActivity.this, CustomerTypesActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(PersonalDetailsActivity.this, getResources().getString(R.string.please_check_your_connection), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(PersonalDetailsActivity.this, getResources().getString(R.string.please_check_your_connection), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void initView() {
        fullName = findViewById(R.id.full_name);
        phone = findViewById(R.id.phone);
        dateOfBirth = findViewById(R.id.date_of_birth);
        gender = findViewById(R.id.gender);
    }

    private void initCalender() {
        defaultDate = Calendar.getInstance();
        defaultDate.set(Calendar.MONTH, Calendar.JANUARY);
        defaultDate.set(Calendar.DAY_OF_MONTH, 1);
        defaultDate.set(Calendar.YEAR, 2000);


        minDate = Calendar.getInstance();
        minDate.set(Calendar.MONTH, Calendar.JANUARY);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        minDate.set(Calendar.YEAR, 1920);


        dpd = DatePickerDialog.newInstance(this, defaultDate);

        dpd.setTitle(getResources().getString(R.string.date_of_birth));
        dpd.setMinDate(minDate);
        dpd.setMaxDate(Calendar.getInstance());


        dateOfBirth.setOnClickListener(v -> {
            dpd.show(getSupportFragmentManager(), PersonalDetailsActivity.class.getName());
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        dateOfBirth.setText(date);
    }
}
