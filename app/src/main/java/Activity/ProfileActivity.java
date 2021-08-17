package Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.shahbatech.apiwrapper.model.Customer;
import com.lasirene.R;

import Utilities.Service.CustomerService;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {

    private EditText fullName, phone, email, password;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        customer = CustomerService.Create().getCustomer(this);

        initView();
        setData();

        AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(R.string.please_wait)
                .build();


        findViewById(R.id.sign_up).setOnClickListener(v -> {
            hideKeyboard();


            String txtEmail = checkString(email.getText().toString().trim());
            String txtPassword = checkString(password.getText().toString().trim());
            String txtFullName = checkString(fullName.getText().toString().trim());
            String txtPhone = checkString(phone.getText().toString().trim());

            Call<Customer> call = api.change(customer.getId(), txtEmail, txtPassword,
                    txtFullName, txtPhone);

            dialog.show();

            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    dialog.dismiss();
                    Customer resp = response.body();
                    if(resp != null){
                        CustomerService.Create().save(resp, ProfileActivity.this);
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.done),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ProfileActivity.this, getResources().getString(R.string.cannot_refresh), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ProfileActivity.this, getResources().getString(R.string.please_check_your_connection), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private String checkString(String str) {
        return str.equals("") ? "null" : str;
    }

    private void initView() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fullName = findViewById(R.id.full_name);
        phone = findViewById(R.id.phone);
    }

    private void setData(){
        email.setText(customer.getEmail());
        fullName.setText(customer.getFullName());
        phone.setText(customer.getPhone());
    }
}
