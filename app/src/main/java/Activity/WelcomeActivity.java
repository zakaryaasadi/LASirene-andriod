package Activity;

import android.content.Intent;
import android.os.Bundle;

import com.lasirene.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.sign_up).setOnClickListener(v -> {
            Intent i = new Intent(WelcomeActivity.this, SignUpActivity.class);
            startActivity(i);
        });

        findViewById(R.id.sign_in).setOnClickListener(v -> {
            Intent i = new Intent(WelcomeActivity.this, SignInActivity.class);
            startActivity(i);
        });
    }
}
