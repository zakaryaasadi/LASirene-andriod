package Activity;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.novoda.merlin.Merlin;
import com.shahbatech.apiwrapper.connection.ApiClient;
import com.shahbatech.apiwrapper.connection.RequestFactory;

public class BaseActivity extends AppCompatActivity {

    protected Merlin merlin;
    protected ApiClient api;

    BaseActivity(){

        api = RequestFactory.createService(ApiClient.class);

        merlin = new Merlin.Builder()
                .withConnectableCallbacks()
                .build(this);
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
