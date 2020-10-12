package kr.ac.kumoh.s20151155.travelapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static java.sql.DriverManager.deregisterDriver;
import static java.sql.DriverManager.println;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    String compare;

    EditText idEdit;
    EditText pwEdit;
    EditText re_pwEdit;
    EditText nameEdit;
    EditText birthEdit;
    EditText phoneEdit;
    EditText emailEdit;
    ImageView checkImage;

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    private ListView listView;

    //private HashMap<String,String> inputData2 = new HashMap<>();
    private ArrayList<HashMap<String,String>> Data = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idEdit = (EditText)findViewById(R.id.id);
        pwEdit = (EditText)findViewById(R.id.pw);
        re_pwEdit = (EditText)findViewById(R.id.re_pw);
        nameEdit = (EditText)findViewById(R.id.name);
        birthEdit = (EditText)findViewById(R.id.birth);
        phoneEdit = (EditText)findViewById(R.id.phone);
        emailEdit = (EditText)findViewById(R.id.email);
        checkImage = (ImageView)findViewById(R.id.check_pw);


        ((Button)findViewById(R.id.overlap)).setOnClickListener(this);
        ((Button)findViewById(R.id.join_customer)).setOnClickListener(this);


        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();


        // 비밀번호 재확인
        re_pwEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }
            //이모티콘 색상 변화
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(pwEdit.getText().toString().equals(re_pwEdit.getText().toString())){
                    checkImage.setImageResource(R.drawable.ic_done_blue_24dp);
                }else{
                    checkImage.setImageResource(R.drawable.ic_clear_red_24dp);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClick(View v){
        DBHandler dbHandler = DBHandler.open(this);

        if(v.getId() == R.id.join_customer){
            String id = idEdit.getText().toString();
            String pw = pwEdit.getText().toString();
            String name = nameEdit.getText().toString();
            String birth = birthEdit.getText().toString();
            String phone = phoneEdit.getText().toString();
            String email = emailEdit.getText().toString();

            boolean check =id.equals(compare);

            long cnt=0;

            if(check == false){
                Toast.makeText(this, "아이디 중복확인 하십시오.", Toast.LENGTH_LONG).show();
            }else{
                if(pw.getBytes().length <= 0 || name.getBytes().length <= 0 || birth.getBytes().length <= 0
                        || phone.getBytes().length <= 0 || email.getBytes().length <= 0 ) {
                    Toast.makeText(this, "모두 입력하십시오.", Toast.LENGTH_SHORT).show();
                } else {
                    cnt = dbHandler.insert(id, pw, name, birth, phone, email);

                    if(cnt == -1){
                        Toast.makeText(this, name+"올바르게 입력 하십시오.", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(this, name + "님 회원 가입 성공", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }


            }

        }else if(v.getId() == R.id.overlap){
            int compare_num=1;

            //아이디 중복 확인
            String id = idEdit.getText().toString();

            String SQL = "select id " + " from " + " customer ";

            Cursor cursor = db.rawQuery(SQL,null);

            int count = cursor.getCount();

            for(int i=0; i<count; i++){
                cursor.moveToNext();
                String existId = cursor.getString(0);

                if(id.equals(existId)){ compare_num = 0;}
            }

            if(compare_num==1){
                showMessage();
                compare_num=1;
            }else{
                Toast.makeText(this,"사용 불가능한 아이디 입니다.", Toast.LENGTH_LONG).show();
            }


        }

        dbHandler.close();
    }

    private void showMessage(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사용 가능");
        builder.setMessage("사용하시겠습니까?");

        builder.setPositiveButton("사용", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compare = idEdit.getText().toString();
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compare = null;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

//dbhelper 종료하기