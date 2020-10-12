package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerListActivity extends AppCompatActivity implements View.OnClickListener{

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    EditText BLN_edit;
    EditText pw_edit;
    Button inquiryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        BLN_edit = (EditText)findViewById(R.id.BLN);
        pw_edit = (EditText)findViewById(R.id.PW);
        inquiryBtn = (Button)findViewById(R.id.inquiry);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        ((Button)findViewById(R.id.inquiry)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DBHandler dbHandler = DBHandler.open(this);

        boolean login_check = false;

        if(v.getId() == R.id.inquiry) {

            String BLN = BLN_edit.getText().toString();
            String pw = pw_edit.getText().toString();

            String SQL = "select BLN, password " + " from " + " business ";

            Cursor cursor = db.rawQuery(SQL,null);

            int count = cursor.getCount();

            cursor.moveToFirst();

            for(int i=0; i<count; i++){

                String existBLN = cursor.getString(0);
                String existPW = cursor.getString(1);

                if(BLN.equals(existBLN) || pw.equals(existPW)){
                    login_check = true;
                    Intent intent = new Intent(CustomerListActivity.this, List_BusinessActivity.class);
                    intent.putExtra("BLN", BLN);
                    startActivity(intent);
                }

                if(i==count-1)
                    break;
                cursor.moveToNext();
            }

            if(login_check == false)
                Toast.makeText(this,"사업자번호 또는 비밀번호를 다시 확인하세요.",Toast.LENGTH_LONG).show();

        }

    }
}
