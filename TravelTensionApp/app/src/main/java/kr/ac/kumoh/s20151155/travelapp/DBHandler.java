package kr.ac.kumoh.s20151155.travelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;

// 테이블 관련 메소드 (추가 삭제 수정 등)
public class DBHandler {
    private DBHelper helper;
    private SQLiteDatabase db;

    private DBHandler(Context context){
        this.helper = new DBHelper(context);
        this.db = helper.getWritableDatabase();
    }


    public static DBHandler open(Context context) throws SQLException{
        DBHandler handler = new DBHandler(context);

        return handler;
    }

    public void close(){
        helper.close();
    }


    public long insert(String id,  String pw, String name, String birth, String phone, String email){
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("password",pw);
        values.put("name", name);
        values.put("birth", birth);
        values.put("phone", phone);
        values.put("email",email);

        return db.insert("customer",null,values);
    }
    public long insert_business(String cpName, String BLN, String pw, String name, String phone, String carNum, String area, byte[] picture, int fight, int freight, int special){
        ContentValues values = new ContentValues();

        values.put("cpName", cpName);
        values.put("BLN",BLN);
        values.put("password",pw);
        values.put("name", name);
        values.put("phone", phone);
        values.put("carNum", carNum);
        values.put("area", area);
        values.put("picture", picture);
        values.put("fight", fight);
        values.put("freight", freight);
        values.put("special", special);

        return db.insert("business",null,values);
    }

    public long insert_reservation(String id, String BLN, String cpName, String name, String phone, int fight, int freight, int special, String year, String month, String day, String start, String end, int totalCost) {
        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("BLN", BLN);
        values.put("cpName", cpName);
        values.put("name", name);
        values.put("phone", phone);
        values.put("fight_num", fight);
        values.put("freight_num", freight);
        values.put("special_num", special);
        values.put("year", year);
        values.put("month", month);
        values.put("day", day);
        values.put("start_area", start);
        values.put("end_area", end);
        values.put("totalCost", totalCost);

        return db.insert("reservation",null,values);
    }
}
