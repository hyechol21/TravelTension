package kr.ac.kumoh.s20151155.travelapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListViewItem2  {


    private  int num;
    private String userId;
    private int year;
    private int month;
    private int day;
    private int totalCost;



    public void setNum(int n){ num = n; }
    public void setUserId(String id){
        userId = id;
    }
    public void setYear(int y){ year = y; }
    public void setMonth(int m){
        month = m;
    }
    public void setDay(int d){
        day = d;
    }
    public void setCost(int cost){
        totalCost = cost;
    }


    public int getNum(){ return this.num; }
    public String getUserId(){
        return this.userId;
    }
    public int getYear(){
        return this.year;
    }
    public int getMonth(){
        return this.month;
    }
    public int getDay(){
        return this.day;
    }
    public int getCost(){
        return this.totalCost;
    }

}
