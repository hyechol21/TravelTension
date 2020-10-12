package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

//맵 오류

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String resultID=null;

    Button login_button;
    TextView lonin_Text;

    //주소
    List<Address> list = null;

    //선택위치
    double startLat=0;
    double startLon=0;
    double endLat=0;
    double endLon=0;

    //현재 날짜 텍스트
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat now_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat now_month = new SimpleDateFormat("M");
    SimpleDateFormat now_day = new SimpleDateFormat("d");

    String formatMonth = now_month.format(date);
    String formatYear = now_year.format(date);
    String formatDay = now_day.format(date);

    TextView yearTx;
    TextView monthTx;
    TextView dayTx;

    EditText countEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countEdit = (EditText)findViewById(R.id.count);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //네비게이션 헤더
        View nav_header_view = navigationView.getHeaderView(0);

       // lonin_Text = (TextView) nav_header_view.findViewById(R.id.loninID);
        login_button = (Button) nav_header_view.findViewById(R.id.loginBtn);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //수정필요 (데이터 넘기는것)
                startActivityForResult(intent, 100);
            }
        });

        //현재 날짜 (자동 기입)
        yearTx = (TextView)findViewById(R.id.date_year);
        monthTx = (TextView)findViewById(R.id.date_month);
        dayTx = (TextView)findViewById(R.id.date_day);

        yearTx.setText(formatYear);
        monthTx.setText(formatMonth);
        dayTx.setText(formatDay);

        //짐 종류 콤보박스
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_bag);
        String[] str = getResources().getStringArray(R.array.baggage);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, str);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);

        spinner.setAdapter(adapter);



        //시작위치 버튼
        Button startBtn = (Button)findViewById(R.id.departure);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivityForResult(intent,200);
            }
        });

        //도착위치 버튼
        Button EndBtn = (Button)findViewById(R.id.destination);
        EndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivityForResult(intent,300);
            }
        });

        //검색 하기 버튼
        Button searchBtn = (Button) findViewById(R.id.search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String baggage = spinner.getSelectedItem().toString();


                String getCount = countEdit.getText().toString();

                int count;
                if(getCount.getBytes().length <= 0)
                    count = 0;
                else
                    count = Integer.parseInt(countEdit.getText().toString());

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("id", resultID);
                intent.putExtra("startLat", startLat);
                intent.putExtra("startLon", startLon);
                intent.putExtra("endLat", endLat);
                intent.putExtra("endLon", endLon);
                intent.putExtra("year", formatYear);
                intent.putExtra("month", formatMonth);
                intent.putExtra("day", formatDay);
                intent.putExtra("count",count);
                intent.putExtra("baggageType",baggage);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // 액티비티가 정상적으로 종료되었을 경우
        if(resultCode==RESULT_OK){
            if (requestCode == 100) {
                resultID = data.getStringExtra("current_id");
                login_button.setText(resultID+"님");
            }
            else if(requestCode == 200){
                startLat = data.getDoubleExtra("location_lat",0);
                startLon = data.getDoubleExtra("location_lon",0);

            }else if(requestCode == 300){
                endLat= data.getDoubleExtra("location_lat",0);
                endLon = data.getDoubleExtra("location_lon",0);
            }
        }else{
            if(requestCode == 100){
                resultID = null;
                login_button.setText("로그인");
            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_detail) {
            if(resultID != null) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", resultID);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 100);
            }
        } else if (id == R.id.nav_review) {

        } else if (id == R.id.nav_customerList) {
            Intent intent = new Intent(MainActivity.this, CustomerListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_inqury) {
            Intent intent = new Intent(MainActivity.this, noticeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_advertise) {
            Intent intent = new Intent(MainActivity.this, BusinessActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_TaC){
            Intent intent = new Intent(MainActivity.this, tacActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //날짜 달력
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}


