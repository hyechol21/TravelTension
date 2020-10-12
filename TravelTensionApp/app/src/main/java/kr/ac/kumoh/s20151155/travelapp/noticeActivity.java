package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class noticeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        String[] List_MENU = {"친구 추천 이벤트","시스템 점검 공지","SNS 공유하기 이벤트"};

        // ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,List_MENU);
        ListAdapter adapter = new imageAdapter(this,List_MENU);
        ListView listView = (ListView) findViewById(R.id.nlist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pposition = position +1;
                switch (pposition){
                    case 1:
                        Intent mIntent = new Intent(noticeActivity.this,event1Activity.class);
                        startActivityForResult(mIntent,700);
                        break;
                    case 2:
                        Intent nmIntent = new Intent(noticeActivity.this,event2Activity.class);
                        startActivityForResult(nmIntent,750);
                        break;
                    case 3:
                        Intent nnIntent = new Intent(noticeActivity.this,event3Activity.class);
                        startActivityForResult(nnIntent,800);
                        break;
                }
            }

        });

    }

}