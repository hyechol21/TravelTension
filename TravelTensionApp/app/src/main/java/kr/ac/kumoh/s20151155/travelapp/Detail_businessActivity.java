package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Detail_businessActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    private int reservation_num;

    Button home;
    TextView idText;
    TextView userNameText;
    TextView userPhoneText;
    TextView totalCostText;
    TextView baggageText;
    TextView nameText;
    TextView phoneText;
    TextView dateText;
    TextView startText;
    TextView endText;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_business);

        idText = (TextView)findViewById(R.id.userId);
        userNameText = (TextView)findViewById(R.id.userName);
        userPhoneText = (TextView)findViewById(R.id.userPhone);
        totalCostText = (TextView)findViewById(R.id.totalCost);
        baggageText = (TextView)findViewById(R.id.baggage);
        nameText = (TextView)findViewById(R.id.name);
        phoneText = (TextView)findViewById(R.id.phone);
        dateText = (TextView)findViewById(R.id.date);
        startText = (TextView)findViewById(R.id.startLocation);
        endText = (TextView)findViewById(R.id.endLocation);


        Intent intent = getIntent();
        reservation_num = intent.getIntExtra("num", 0);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        DBHandler dbHandler = DBHandler.open(this);


        String SQL = "select num, id, name, phone, fight_num, freight_num, special_num, " +
                "start_area, end_area, year, month, day, totalCost" + " from " + " reservation ";

        Cursor cursor = db.rawQuery(SQL,null);

        int count = cursor.getCount();

        cursor.moveToFirst();

        String bln;

        for(int i=0; i<count; i++){
            int num = cursor.getInt(0);
            userId = cursor.getString(1);
            String name = cursor.getString(2);
            String phone = cursor.getString(3);
            int fight = cursor.getInt(4);
            int freight = cursor.getInt(5);
            int special = cursor.getInt(6);
            String start = cursor.getString(7);
            String end = cursor.getString(8);
            int year = cursor.getInt(9);
            int month = cursor.getInt(10);
            int day = cursor.getInt(11);
            int totaclCost = cursor.getInt(12);

            if(num == reservation_num){
                idText.setText(userId + " 님");
                totalCostText.setText("결제금액  " + totaclCost + "원");
                baggageText.setText("기내용  " + fight +"개     " + "화물용  " + freight + "개     " + "특대용  " + special +"개");
                nameText.setText(name);
                phoneText.setText(phone);
                dateText.setText(year + ". " + month + ". " + day);
                startText.setText(start);
                endText.setText(end);
                break;
            }

            if(i==count-1)
                break;
            cursor.moveToNext();
        }

        //cutomer 테이블에서 회원의 이름과 전화번호 가져오기
        String SQL2 = "select id, name, phone" + " from " + " customer ";

        Cursor cursor2 = db.rawQuery(SQL2,null);

        int count2 = cursor2.getCount();

        cursor2.moveToFirst();

        for(int i=0; i<count2; i++){
            String id = cursor2.getString(0);
            String name = cursor2.getString(1);
            String phone = cursor2.getString(2);

            if(id.equals(userId)){
                idText.setText(id);
                userNameText.setText(name);
                userPhoneText.setText(phone);
                break;
            }
        }

        ((Button)findViewById(R.id.home)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Detail_businessActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
