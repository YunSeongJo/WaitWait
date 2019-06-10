package com.waitwait;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<HashMap<String,String>> Data = new ArrayList<HashMap<String, String>>();
    private HashMap<String,String> InputData1 = new HashMap<>();
    private HashMap<String,String> InputData2 = new HashMap<>();
    private ListView listView;
    private Button button;
    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView =(ListView)findViewById(R.id.List_view);




        //데이터 초기화
        InputData1.put("restaurant","수원 아웃백");
        InputData1.put("place","위도: 37.265946   경도: 127.029853");
        Data.add(InputData1);

        InputData2.put("restaurant","술통밥통");
        InputData2.put("place","위도: 36.831430   경도: 127.177570");
        Data.add(InputData2);

        //simpleAdapter 생성
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,Data,android.R.layout.simple_list_item_2,new String[]{"restaurant","place"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        //
    }
}
