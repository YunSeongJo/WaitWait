package com.waitwait;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    protected FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private HomeFragment homeFragment = new HomeFragment();
    //private Menu2Fragment menu2Fragment = new Menu2Fragment();
    //private Menu3Fragment menu3Fragment = new Menu3Fragment();

    FragmentTransaction transaction = fragmentManager.beginTransaction();

    TextView tv;
    private FirebaseFirestore db;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // 첫 화면 지정
        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
        tv = findViewById(R.id.waitStatusTextView);
        db = FirebaseFirestore.getInstance();

    }


    public void onClickToQRActivityMain(View view){
        Intent intent = new Intent(getApplicationContext(), QRActivity.class);
        startActivity(intent);
    }

    public void onClickCancelBooked(View view){
        db.collection("RestaurantList").document(LoginedUserInformation.WaitingRestaurantCode).collection("WaitList").document(LoginedUserInformation.WaitingNumber + "").delete();

        Map<String, Object> user = new HashMap<>();
        user.put("Email", LoginedUserInformation.email);
        user.put("Name", LoginedUserInformation.name);
        user.put("Phone", LoginedUserInformation.phone);
        user.put("WaitListRestaurantName", "none");
        user.put("WaitListRestaurantNumber", 0);
        user.put("WaitListRestaurantCode", "none");


        db.collection("UserInformation").document(LoginedUserInformation.email)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }



}

