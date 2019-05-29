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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    private FirebaseFirestore db;
    TextView tv;
    LoginedUserInformation LUI;
    TextView rtv;
    TextView tv2;

    int count;

    public HomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("WatTheFuck");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv = view.findViewById(R.id.waitStatusTextView);
        rtv = view.findViewById(R.id.waitStatusRemainTextView);
        tv2 = view.findViewById(R.id.waitStatusTextView2);

        //--------------------------------

        db = FirebaseFirestore.getInstance();
        LUI = new LoginedUserInformation();




        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        System.out.println("Thread start");
                        System.out.println(LUI.getEmail());
                        DocumentReference docRef = db.collection("UserInformation").document(LUI.getEmail());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        System.out.println("DocumentSnapshot data: " + document.getData());
                                        if(document.getString("WaitListRestaurantName").equals("none")){
                                            tv2.setText("");
                                            tv.setText("현재 대기 중인 식당이 없습니다.");
                                            rtv.setText("");
                                        }else{
                                            double tempdouble = document.getDouble("WaitListRestaurantNumber");
                                            int tempint = (int) tempdouble;
                                            tv2.setText(document.getString("WaitListRestaurantName"));
                                            tv.setText("대기번호 : " + tempint);
                                            db.collection("RestaurantList").document(LoginedUserInformation.WaitingRestaurantCode).collection("WaitList")
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                //내앞에 몇팀있는지 세기
                                                                count = 0;
                                                                db.collection("RestaurantList").document(LoginedUserInformation.WaitingRestaurantCode).collection("WaitList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            List<String> list = new ArrayList<>();
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                list.add(document.getId());
                                                                            }
                                                                            System.out.println(list.toString());

                                                                            List<Integer> intlist = new ArrayList<>();
                                                                            for(int i=0; i<list.size(); i++){
                                                                                intlist.add(Integer.parseInt(list.get(i)));
                                                                            }

                                                                            for(int i=0; i<list.size(); i++){
                                                                                if(intlist.get(i) < LoginedUserInformation.WaitingNumber){
                                                                                    count++;
                                                                                }
                                                                            }


                                                                            if(count == 0){
                                                                                rtv.setText("드디어 내 차례에요! 매장 앞으로 와주세요!");
                                                                            }else{
                                                                                rtv.setText("내 앞에 " + count + "팀이 있어요.");
                                                                            }
                                                                        } else {
                                                                            System.out.println("error");
                                                                        }
                                                                    }
                                                                });



                                                            } else {
                                                                System.out.println("Error Occured");
                                                            }
                                                        }
                                                    });

                                        }
                                    } else {
                                        System.out.println("No such document");
                                        tv2.setText("");
                                        tv.setText("현재 대기 중인 식당이 없습니다.");
                                        rtv.setText("");
                                    }
                                } else {
                                    System.out.println("get failed with ");
                                    tv2.setText("");
                                    tv.setText("현재 대기 중인 식당이 없습니다.");
                                    rtv.setText("");
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


