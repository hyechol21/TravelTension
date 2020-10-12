package kr.ac.kumoh.s20151155.travelapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class BusinessActivity extends AppCompatActivity implements View.OnClickListener{

    private SQLiteDatabase db;
    private  DBHelper dbHelper;

    EditText cpNameEdit;
    EditText BLNEdit;
    EditText nameEdit;
    EditText passwordEdit;
    EditText phoneEdit;
    EditText carNumEdit;
    EditText areaEdit;
    ImageView picture;
    EditText fightEdit;
    EditText freightEdit;
    EditText specialEdit;

    Bitmap img;

    private final int GALLERY_CODE=1;
    Button image_upload;
    Button uploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        cpNameEdit = (EditText)findViewById(R.id.company_name);
        BLNEdit = (EditText)findViewById(R.id.BLN);
        nameEdit = (EditText)findViewById(R.id.represent_name);
        passwordEdit = (EditText)findViewById(R.id.pw);
        phoneEdit = (EditText)findViewById(R.id.represent_phone);
        carNumEdit = (EditText)findViewById(R.id.car_number);
        areaEdit = (EditText)findViewById(R.id.area);
        picture = (ImageView)findViewById(R.id.image);
        fightEdit = (EditText)findViewById(R.id.fight);
        freightEdit = (EditText)findViewById(R.id.freight);
        specialEdit = (EditText)findViewById(R.id.special);

        picture = (ImageView)findViewById(R.id.image);
        image_upload = (Button)findViewById(R.id.search_image);
        uploadBtn = (Button)findViewById(R.id.upload);

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGallery();
            }
        });


        ((Button)findViewById(R.id.upload)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        DBHandler dbHandler = DBHandler.open(this);

        int fight = 0;
        int freight = 0;
        int special = 0;
        String cpName=null;
        String BLN=null;
        String name=null;
        String password=null;
        String phone=null;
        String carNum=null;
        String area=null;
        byte[] picture = null;

        if (v.getId() == R.id.upload) {

            cpName = cpNameEdit.getText().toString();
            BLN = BLNEdit.getText().toString();
            name = nameEdit.getText().toString();
            password = passwordEdit.getText().toString();
            phone = phoneEdit.getText().toString();
            carNum = carNumEdit.getText().toString();
            area = areaEdit.getText().toString();
            picture = getByteArrayFromBitmap(img);
            if(fightEdit.getText().toString().getBytes().length > 0 ){
                fight = Integer.parseInt(fightEdit.getText().toString());
            }
            if(freightEdit.getText().toString().getBytes().length > 0 ){
                freight = Integer.parseInt(freightEdit.getText().toString());
            }
            if(specialEdit.getText().toString().getBytes().length > 0 ){
                special = Integer.parseInt(specialEdit.getText().toString());
            }


            if(fight==0 || freight==0 || special==0){
                Toast.makeText(this,"요금을 입력하십시오.", Toast.LENGTH_SHORT).show();
            }else {
                long cnt = dbHandler.insert_business(cpName, BLN, password, name, phone, carNum, area, picture, fight, freight, special);

                if(cnt == -1){
                    Toast.makeText(this, "올바르게 입력 하십시오.", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this, cpName + "업체 등록 완료!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }
    }

    //이미지 byte로 변환
    public byte[] getByteArrayFromBitmap(Bitmap bitmap){
        //Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] data = stream.toByteArray();

        return data;
    }



    private void selectGallery () {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_CODE);

    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    picture.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            return;
        }
    }
}
