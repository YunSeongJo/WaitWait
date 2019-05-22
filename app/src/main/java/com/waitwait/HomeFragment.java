package com.waitwait;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.internal.firebase_auth.zzdb;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    private FirebaseFirestore db;
    TextView tv;

    public HomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("WatTheFuck");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv = view.findViewById(R.id.waitStatusTextView);

        //--------------------------------

        db = FirebaseFirestore.getInstance();




        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        System.out.println("Thread start");
                        DocumentReference docRef = db.collection("UserInformation").document(LoginedUserInformation.email);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        System.out.println("DocumentSnapshot data: " + document.getData());
                                        tv.setText(document.getString("WaitListRestaurantName") + "에서 " + document.getDouble("WaitListRestaurantNumber") + "번으로 대기중");
                                    } else {
                                        System.out.println("No such document");
                                    }
                                } else {
                                    System.out.println("get failed with ");
                                }
                            }
                        });
                        Thread.sleep(5000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }


                }
            }
        }).start();


        return view;
    }

}


