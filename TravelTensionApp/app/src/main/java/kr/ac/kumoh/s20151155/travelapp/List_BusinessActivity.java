package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class List_BusinessActivity extends AppCompatActivity {


    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    int num;
    String BLN;
    String userId;
    int year;
    int month;
    int day;
    int cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__business);


        Intent intent = getIntent();
        BLN = intent.getExtras().getString("BLN");

        ListView listView;
        ListViewAdapter2 adapter;

        adapter = new ListViewAdapter2();

        listView = (ListView)findViewById(R.id.listview2);
        listView.setAdapter(adapter);

        //adapter.addItem("돼라",2019, 6, 17, 20000);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        DBHandler dbHandler = DBHandler.open(this);

        String SQL = "select num, id, BLN, year, month, day, totalCost" + " from " + " reservation ";

        Cursor cursor = db.rawQuery(SQL,null);

        int count = cursor.getCount();

        cursor.moveToFirst();

        String bln;

        for(int i=0; i<count; i++){
            num = cursor.getInt(0);
            bln = cursor.getString(2);
            userId = cursor.getString(1);
            year = cursor.getInt(3);
            month = cursor.getInt(4);
            day = cursor.getInt(5);
            cost = cursor.getInt(6);

            if(bln.equals(BLN)){
                adapter.addItem(num, userId, year, month, day, cost);
            }

            if(i==count-1)
                break;
            cursor.moveToNext();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem2 item = (ListViewItem2) parent.getItemAtPosition(position);

                num = item.getNum();

                Intent intent = new Intent(List_BusinessActivity.this, Detail_businessActivity.class);

                intent.putExtra("num", num);


                startActivity(intent);
            }
        });
    }

}
