package com.user.atozbasket;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.KernBurns.KenBurnsView;
import com.user.atozbasket.MerchantApp.MerchantActivity;


public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;
    private KenBurnsView mKenBurns;


    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "savedetails";
    public static final String name = "name";
    public static final String mail= "mail";
    public static final String number = "number";
    public static final String rand= "rand";


    SharedPreferences sharedpreferences_full_address;
    public static final String mypreference_full_address = "mypreference_full_address";
    public static final String full_address = "full_address";


    private FirebaseAuth firebaseAuth;
    private FirebaseUser mFirebaseUser;

    SharedPreferences sharedpreferences_data;
    public static final String mypreference_data = "myprefdata";
    public static final String data_url = "data_url";
    public static final String data_name = "data_name";
    public static final String data_cost= "data_cost";

    SharedPreferences sharedpreferences_write_order;
    public static final String mypreference_write_order = "mypreference_write_order";
    public static final String full_order_list = "full_order_list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth=FirebaseAuth.getInstance();
        mFirebaseUser = firebaseAuth.getCurrentUser();

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        sharedpreferences_order_details = getSharedPreferences(mypreference_order_details,
                Context.MODE_PRIVATE);

        sharedpreferences_full_address = getSharedPreferences(mypreference_full_address,
                Context.MODE_PRIVATE);

        sharedpreferences_write_order=getSharedPreferences(mypreference_write_order,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor4=sharedpreferences_write_order.edit();
        editor4.remove(full_order_list);
        editor4.commit();

        SharedPreferences.Editor editor2=sharedpreferences_order_details.edit();
        editor2.remove(order_list);
        editor2.remove(order_cost);
        editor2.commit();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(name);
        editor.remove(mail);
        editor.remove(number);
        editor.remove(rand);
        editor.commit();

        sharedpreferences_data = getSharedPreferences(mypreference_data,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor3=sharedpreferences_data.edit();
        editor3.remove(data_cost);
        editor3.remove(data_name);
        editor3.remove(data_url);
        editor3.commit();

        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/DynamicNumbers");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x=(String) dataSnapshot.getValue();
                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();


                //textView.setText("My Notifications");
                //notification.setText(x);
                //notifications_icon.setImageResource(R.drawable.notifications_bell);

                //textView.setText(s);
                //progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");
            }
        });

        setAnimation();


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mKenBurns.setImageResource(R.drawable.splash_background);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if(mFirebaseUser==null)
                {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
                else {

                    if(sharedpreferences_full_address.getString(full_address,"").equals(""))
                    {
                        startActivity(new Intent(getApplicationContext(),SelectArea.class));
                    }
                    else {
                       Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        //Intent i=new Intent(SplashScreen.this, MerchantActivity.class);
                        startActivity(i);
                    }
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void setAnimation() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();

        findViewById(R.id.imagelogo).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        findViewById(R.id.imagelogo).startAnimation(anim);
    }
}

