package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    EditText IDEdit;
    EditText PWEdit;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Button joinBtn = (Button)findViewById(R.id.join);


        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        IDEdit = (EditText)findViewById(R.id.ID);
        PWEdit = (EditText)findViewById(R.id.PW);

        ((Button)findViewById(R.id.login_Enter)).setOnClickListener(this);
        ((Button)findViewById(R.id.join)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DBHandler dbHandler = DBHandler.open(this);

        boolean login_check = false;

        if(v.getId() == R.id.login_Enter) {

            String id = IDEdit.getText().toString();
            String pw = PWEdit.getText().toString();


            String SQL = "select id, password " + " from " + " customer ";


            Cursor cursor = db.rawQuery(SQL,null);

            int count = cursor.getCount();

            for(int i=0; i<count; i++){ ;
                cursor.moveToNext();

                String existID = cursor.getString(0);
                String existPW = cursor.getString(1);

                //if문 안에 안들어감
                if(id.equals(existID) && pw.equals(existPW)){
                    login_check = true;
                    Intent intent = new Intent();
                    intent.putExtra("current_id", id);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }

            if(login_check == false)
                Toast.makeText(this,"아이디 또는 비밀번호를 다시 확인하세요.",Toast.LENGTH_LONG).show();



        }else if( v.getId() == R.id.join){
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
        }
    }
}

