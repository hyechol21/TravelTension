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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ListActivity extends AppCompatActivity  {

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    Bitmap image;
    String companyName;
    int fightCost;
    int freightCost;
    int specialCost;
    int chargeCost;
    String area;

    EditText search_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        search_edit = (EditText)findViewById(R.id.search_edit);


        Intent intent = getIntent();

        //사용자 입력 데이터 넘겨주기 -> 예약화면으로 넘겨주기
        final String user_id = intent.getExtras().getString("id");
        final double startLat = intent.getExtras().getDouble("startLat");
        final double startLon = intent.getExtras().getDouble("startLon");
        final double endLat = intent.getExtras().getDouble("endLat");
        final double endLon = intent.getExtras().getDouble("endLon");
        final String year = intent.getExtras().getString("year");
        final String month = intent.getExtras().getString("month");
        final String day = intent.getExtras().getString("day");


        final ListView listView;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);



        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        DBHandler dbHandler = DBHandler.open(this);

        String SQL = "select cpName, fight, freight, special, area, picture" + " from " + " business ";

        Cursor cursor = db.rawQuery(SQL,null);

        int count = cursor.getCount();

        cursor.moveToFirst();

        for(int i=0; i<count; i++){

            companyName = cursor.getString(0);
            fightCost = cursor.getInt(1);
            freightCost = cursor.getInt(2);
            specialCost = cursor.getInt(3);
            chargeCost = TotalChargeCost(fightCost, freightCost, specialCost);
            area= cursor.getString(4);
            byte[] img = cursor.getBlob(5);
            image = getByteImage(img);


            String startAddress;
            String endAddress;

            if(startLat==0&&endLat==0){
                Log.d("list", companyName);
                //지도 입력 안받은 경우: 모두 출력
                adapter.addItem(companyName, fightCost, freightCost, specialCost, chargeCost, image);
            }
            else if(startLat!=0 && endLat==0 ){
                //출발위치만 입력받은 경우: 출발지역 모두 출력
                startAddress = getAddress(startLat, startLon);

                if(startAddress.equals(area)){
                    adapter.addItem(companyName, fightCost, freightCost, specialCost, chargeCost, image);
                }

            }else if(startLat==0 && endLat!=0){
                //도착위치만 입력받은 경우: 도착지역 모두 출력
                 endAddress = getAddress(endLat, endLon);
                if(endAddress.equals(area)){
                    adapter.addItem(companyName, fightCost, freightCost, specialCost, chargeCost, image);
                }
            }else{
                //모두 입력받은 경우
                startAddress = getAddress(startLat, startLon);
                endAddress = getAddress(endLat, endLon);
                if(startAddress.equals(area) && endAddress.equals(area)){
                    adapter.addItem(companyName, fightCost, freightCost, specialCost, chargeCost, image);
                }
            }

            if(i==count-1)
                break;

            cursor.moveToNext();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem)parent.getItemAtPosition(position);

                Bitmap img = item.getImage();
                String companyName = item.getCompanyName();
                int fightCost = item.getFightCost();
                int freightCost = item.getFreightCost();
                int specialCost = item.getSpecialCost();


                Intent intent = new Intent(ListActivity.this, reservationActivity.class);
                intent.putExtra("id", user_id);
                intent.putExtra("companyName", companyName);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
                intent.putExtra("fightCost", fightCost);
                intent.putExtra("freightCost", freightCost);
                intent.putExtra("specialCost", specialCost);
                intent.putExtra("startLat", startLat);
                intent.putExtra("startLon", startLon);
                intent.putExtra("endLat", endLat);
                intent.putExtra("endLon", endLon);
                intent.putExtra("image", img);

                startActivity(intent);
            }
        });

        EditText editTextFilter = (EditText)findViewById(R.id.search_edit) ;

        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                if (filterText.length() > 0) {
                    listView.setFilterText(filterText) ;
                } else {
                    listView.clearTextFilter() ;
                }
            }
            // 코드 계속 ...
        }) ;
    }


    public Bitmap getByteImage(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);

        return bitmap;
    }

    public int TotalChargeCost(int fight, int freight, int special){
        int charge;

        Intent intent = getIntent();
        String baggage = intent.getExtras().getString("baggageType");
        int baggageCount = intent.getExtras().getInt("count");

        if(baggage.equals("기내용")){
            charge = fight*baggageCount;
        }
        else if(baggage.equals("화물용")){
            charge = freight*baggageCount;
        }else if(baggage.equals("특대용") ){
            charge = special*baggageCount;
        }else
            charge = 0;
        return charge;
    }


    public String getAddress(double lat, double lon){
        //행정구역 이름 구하기
        String localAddress;

        Geocoder gCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addr = null;
        try {
            addr = gCoder.getFromLocation(lat, lon,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addr.get(0);

        if(address.getLocality()!=null)
            localAddress = address.getLocality();
        else
            localAddress = address.getAdminArea();

        return localAddress.substring(0,2);
    }


}
