package kr.ac.kumoh.s20151155.travelapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class reservationActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    ImageView imageView;
    TextView fight_text;
    TextView freight_text;
    TextView special_text;
    EditText nameEdit;
    EditText phoneEdit;
    EditText fightEdit;
    EditText freightEdit;
    EditText specialEdit;

    String BLN;
    String id;
    String companyName;
    String year;
    String month;
    String day;
    int fightCost;
    int freightCost;
    int specialCost;
    int totalCost;

    int fightNum=0;
    int freightNum=0;
    int specialNum=0;

    String startLocation;
    String endLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        imageView = (ImageView)findViewById(R.id.company_image);
        fight_text = (TextView)findViewById(R.id.fight_cost);
        freight_text = (TextView)findViewById(R.id.freight_cost);
        special_text = (TextView)findViewById(R.id.special_cost);
        nameEdit = (EditText)findViewById(R.id.name);
        phoneEdit = (EditText)findViewById(R.id.phone);
        fightEdit = (EditText)findViewById(R.id.fight_num);
        freightEdit = (EditText)findViewById(R.id.freight_num);
        specialEdit = (EditText)findViewById(R.id.special_num);

        ((Button)findViewById(R.id.reservation_btn)).setOnClickListener(this);


        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        companyName = intent.getExtras().getString("companyName");
        year = intent.getExtras().getString("year");
        month = intent.getExtras().getString("month");
        day = intent.getExtras().getString("day");
        fightCost = intent.getExtras().getInt("fightCost");
        freightCost = intent.getExtras().getInt("freightCost");
        specialCost = intent.getExtras().getInt("specialCost");
        double startLat = intent.getExtras().getDouble("startLat");
        double startLon = intent.getExtras().getDouble("startLon");
        double endLat = intent.getExtras().getDouble("endLat");
        double endLon = intent.getExtras().getDouble("endLon");
        Bitmap image = intent.getExtras().getParcelable("image");

        //company_text.setText(companyName);
        imageView.setImageBitmap(image);
        fight_text.setText("기내용   " + fightCost);
        freight_text.setText("화물용   " + freightCost);
        special_text.setText("특대용   " + specialCost);



        startLocation = getAddress(startLat, startLon);
        endLocation = getAddress(endLat, endLon);
    }

    //위도, 경도로 주소 받기
    private String getAddress(double lat, double lon){
        Geocoder geocoder = new Geocoder(this);

        List<Address> address = null;

        String locationAddress = null;

        try {
            if(geocoder != null){
                address = geocoder.getFromLocation(lat, lon, 1);

                if(address != null && address.size() > 0){
                    locationAddress = address.get(0).getAddressLine(0).toString();
                }
            }

        } catch (IOException e) {
            Toast.makeText(reservationActivity.this, "주소획득 실패", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if(address != null){
            if(address.size()==0){
                Toast.makeText(this, "주소를 선택해주세요.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, locationAddress, Toast.LENGTH_SHORT).show();
            }
        }
        return locationAddress;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.reservation_btn){

            String name = nameEdit.getText().toString();
            String phone = phoneEdit.getText().toString();
            String fight = fightEdit.getText().toString();
            String freight = freightEdit.getText().toString();
            String special = specialEdit.getText().toString();


            //입력한 수량 받기
            if(fight.getBytes().length <= 0)
                fightNum = 0;
            else
                fightNum = Integer.parseInt(fight);

            if(freight.getBytes().length <= 0)
                freightNum = 0;
            else
                freightNum = Integer.parseInt(freight);

            if(special.getBytes().length <= 0)
                specialNum = 0;
            else
                specialNum = Integer.parseInt(special);


            totalCost = fightCost*fightNum + freightCost*freightNum + specialCost*specialNum;

            if(name.getBytes().length <= 0)
                ErrorMessage();
            else if(phone.getBytes().length <= 0)
                ErrorMessage();
            else if(fight.getBytes().length > 0 || freight.getBytes().length > 0 || special.getBytes().length > 0)
                showMessage(name, phone);


        }
    }

    public void showMessage(final String name, final String phone) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(companyName + " 예약 가능");
        builder.setMessage(id + "님 \n\n" +  "예약자: "+ name + "\n예약일: " + year +"년  " + month + "월  " + day + "일\n"
                + "총 금액: " + totalCost + "원\n\n예약 하시겠습니까?");


        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cpName;
                String bln;

                DBHandler dbHandler = DBHandler.open(reservationActivity.this);

                String SQL = "select cpName, BLN" + " from " + " business ";

                Cursor cursor = db.rawQuery(SQL,null);

                int count = cursor.getCount();

                cursor.moveToFirst();

                for(int i=0; i<count; i++){

                    cpName = cursor.getString(0);
                    bln = cursor.getString(1);

                    if(cpName.equals(companyName)){
                        BLN = bln;
                        break;
                    }
                    if(i==count-1)
                        break;
                    cursor.moveToNext();
                }

                long cnt = dbHandler.insert_reservation(id, BLN, companyName, name, phone, fightNum, freightNum, specialNum,
                        year, month, day, startLocation, endLocation, totalCost);

                Toast.makeText(reservationActivity.this, id +"님 예약 완료되었습니다. ", Toast.LENGTH_SHORT);


                Intent intent = new Intent(reservationActivity.this, DetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ErrorMessage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(R.drawable.ic_report_problem_yellow_24dp);
        builder.setMessage("정보를 모두 입력하여주세요.");


        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
