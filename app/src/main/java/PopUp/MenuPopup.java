package PopUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import Activity.NewsActivity;
import Activity.ProfileActivity;
import Activity.ContactUsActivity;
import Activity.WelcomeActivity;
import Activity.BookingActivity;
import Activity.MemberShipActivity;
import Utilities.Service.CustomerService;

import com.lasirene.R;

public class MenuPopup
{
    private View view;
    private PopupWindow popupWindow;

    private Context mContext;


    public MenuPopup(Context context, View v){

        mContext = context;
        Activity activity = ((Activity)context);
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.popup_menu, null,false);
        popupWindow = new PopupWindow(view, size.x ,-2, true );




        popupWindow.setAnimationStyle(R.style.popup_anim);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_color_edit_rounded));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0,0);


        view.findViewById(R.id.news).setOnClickListener( vv ->{
            Intent i = new Intent(context, NewsActivity.class);
            context.startActivity(i);
            popupWindow.dismiss();
        });

        view.findViewById(R.id.my_bookings).setOnClickListener( vv ->{
            Intent i = new Intent(context, BookingActivity.class);
            context.startActivity(i);
            popupWindow.dismiss();
        });

        view.findViewById(R.id.profile).setOnClickListener( vv ->{
            Intent i = new Intent(context, ProfileActivity.class);
            context.startActivity(i);
            popupWindow.dismiss();
        });

        view.findViewById(R.id.contact_us).setOnClickListener( vv ->{
            Intent i = new Intent(context, ContactUsActivity.class);
            context.startActivity(i);
            popupWindow.dismiss();
        });

        view.findViewById(R.id.membership).setOnClickListener( vv ->{
            Intent i = new Intent(context, MemberShipActivity.class);
            context.startActivity(i);
            popupWindow.dismiss();
        });

        view.findViewById(R.id.logout).setOnClickListener( vv ->{
            CustomerService.Create().remove(context);
            popupWindow.dismiss();

            Intent i = new Intent(context, WelcomeActivity.class);
            context.startActivity(i);
            ((Activity) context).finishAffinity();
        });
    }


}
