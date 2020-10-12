package kr.ac.kumoh.s20151155.travelapp;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ListViewItem {


    private Bitmap image;
    private String companyName;
    private int fightCost;
    private int freightCost;
    private int specialCost;
    private int chargeCost;


    public void setImage(Bitmap img) {
        image = img;
    }

    public void setCompanyName(String name) {
        Log.d("setName", name);
        companyName = name;
    }

    public void setFightCost(int fight) {
        fightCost = fight;
    }

    public void setFreightCost(int freight) {
        freightCost = freight;
    }

    public void setSpecialCost(int special) {
        specialCost = special;
    }

    public void setChargeCost(int charge) {
        chargeCost = charge;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public String getCompanyName() {
        Log.d("getName",this.companyName);
        return this.companyName;
    }

    public int getFightCost() {
        return this.fightCost;
    }

    public int getFreightCost() {
        return this.freightCost;
    }

    public int getSpecialCost() {
        return this.specialCost;
    }

    public int getChargeCost() {
        return this.chargeCost;
    }


}
