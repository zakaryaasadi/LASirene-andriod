package Activity;

import android.app.AlertDialog;
import android.content.Intent;
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

public class SignInActivity extends BaseActivity {

    private EditText email, password;
    private CustomerService customerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        customerService = new CustomerService();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(R.string.please_wait)
                .setTheme(R.style.Custom)
                .build();

        findViewById(R.id.sign_in).setOnClickListener(v->{
            hideKeyboard();
            boolean isNotValidEmail = email.getText().toString().trim().equals("");
            boolean isNotValidPassword = password.getText().toString().trim().equals("");

            if(isNotValidEmail){
                email.setError(getResources().getString(R.string.valid_email));
            }else{
                email.setError(null);
            }

            if(isNotValidPassword){
                password.setError(getResources().getString(R.string.valid_password));
            }else{
                password.setError(null);
            }

            if(isNotValidEmail || isNotValidPassword)
                return;


            Call<Customer> call = api.login(email.getText().toString().trim(),
                    password.getText().toString().trim());

            dialog.show();

            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    dialog.dismiss();
                    Customer resp = response.body();
                    if(resp != null){
                        customerService.save(resp, SignInActivity.this);
                        Intent i = new Intent(SignInActivity.this, CustomerTypesActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(SignInActivity.this, getResources().getString(R.string.email_or_password_incorrent), Toast.LENGTH_LONG).show();
                    }
                }


                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(SignInActivity.this, getResources().getString(R.string.please_check_your_connection), Toast.LENGTH_LONG).show();
                }
            });

        });

    }

}
