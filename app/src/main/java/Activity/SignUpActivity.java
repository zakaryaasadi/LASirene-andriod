package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.lasirene.R;

import Utilities.Helper.Defaults;

public class SignUpActivity extends BaseActivity {

    private EditText email, password, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm_password);


        findViewById(R.id.sign_in).setOnClickListener(v ->{
            Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(i);
            finish();
        });

        findViewById(R.id.sign_up).setOnClickListener(v -> {

            hideKeyboard();

            String txtEmail = email.getText().toString().trim();
            String txtPassword = password.getText().toString().trim();
            String txtConfirm = confirm.getText().toString().trim();

            boolean isNotValidEmail = txtEmail.equals("");
            boolean isNotValidPassword = txtPassword.equals("");
            boolean isNotValidConfirm = txtConfirm.equals("");

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


            if(isNotValidConfirm){
                confirm.setError(getResources().getString(R.string.valid_password));
            }else{
                confirm.setError(null);
            }

            if(isNotValidEmail || isNotValidPassword || isNotValidConfirm)
                return;

            if(!txtPassword.equals(txtConfirm)){
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.password_not_match), Toast.LENGTH_LONG)
                        .show();

                return;
            }


            Intent i = new Intent(SignUpActivity.this, PersonalDetailsActivity.class);
            i.putExtra(Defaults.EMAIL, txtEmail);
            i.putExtra(Defaults.PASSWORD, txtPassword);
            startActivity(i);
            finish();
        });
    }
}
