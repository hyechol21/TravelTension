package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    String userId;

    TextView cpNameText;
    TextView nameText;
    TextView dateText;
    TextView startLocationText;
    TextView endLocationText;
    TextView fightText;
    TextView freightText;
    TextView specialText;
    TextView totalText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();


        cpNameText = (TextView)findViewById(R.id.company_name);
        nameText = (TextView)findViewById(R.id.name);
        dateText = (TextView)findViewById(R.id.date);
        startLocationText = (TextView)findViewById(R.id.startLocation);
        endLocationText = (TextView)findViewById(R.id.endLocation);
        fightText = (TextView)findViewById(R.id.fight_num);
        freightText = (TextView)findViewById(R.id.freight_num);
        specialText = (TextView)findViewById(R.id.special_num);
        totalText = (TextView)findViewById(R.id.totalCost);
        Button homeBtn = (Button)findViewById(R.id.home);

        Intent intent = getIntent();
        userId = intent.getExtras().getString("id");

        setDetailInfo();

        //기존 액티비티들 모두 종료후 홈화면으로 화면 전환
        //***************수정 필요******************로그아웃되어버림
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }

    public void setDetailInfo(){
        boolean checkId=false;
        DBHandler dbHandler = DBHandler.open(this);

        String SQL = "select id, cpName, name, year, month, day, start_area, end_area, fight_num, freight_num, special_num, totalCost" + " from " + " reservation ";

        Cursor cursor = db.rawQuery(SQL,null);

        int count = cursor.getCount();

        cursor.moveToFirst();

        String id;
        String companyName;
        String name;
        String year;
        String month;
        String day;
        String start;
        String end;
        int fight=0;
        int freight=0;
        int special=0;
        int totalCost=0;

        for(int i=0; i<count; i++){

            id = cursor.getString(0);

            if(userId.equals(id)){
                checkId = true;

                companyName = cursor.getString(1);
                name = cursor.getString(2);
                year = cursor.getString(3);
                month = cursor.getString(4);
                day = cursor.getString(5);
                start = cursor.getString(6);
                end = cursor.getString(7);
                fight = cursor.getInt(8);
                freight = cursor.getInt(9);
                special = cursor.getInt(10);
                totalCost = cursor.getInt(11);


                cpNameText.setText(companyName);
                nameText.setText(name);
                dateText.setText(year+"/ "+month+"/ "+day);
                startLocationText.setText(start);
                endLocationText.setText(end);
                fightText.setText(fight+" 개");
                freightText.setText(freight+" 개");
                specialText.setText(special+" 개");
                totalText.setText(totalCost+"원");


            }

            if(i==count-1)
                break;
            cursor.moveToNext();
        }

        if(checkId==false){
            cpNameText.setText("내역이 없습니다.");
        }
    }

}
