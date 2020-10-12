package kr.ac.kumoh.s20151155.travelapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context)
    {
        super(context, "DBManger.db" , null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table =
                "CREATE TABLE customer (" +
                        "id TEXT PRIMARY KEY NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "birth TEXT NOT NULL, " +
                        "phone TEXT NOT NUlL, " +
                        "email TEXT NOT NULL );";

        String businiess_table =
                "CREATE TABLE business (" +
                        "cpName TEXT NOT NULL, " +
                        "BLN TEXT PRIMARY KEY NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "phone TEXT NOT NULL, " +
                        "carNum TEXT NOT NULL, " +
                        "area TEXT NOT NULL, " +
                        "picture BLOB NOT NULL, " +
                        "fight INTEGER NOT NULL, " +
                        "freight INTEGER NOT NULL, " +
                        "special INTEGER  NOT NULL );";

        String reservation_table =
                "CREATE TABLE reservation (" +
                        "num INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id TEXT NOT NULL, " +
                        "BLN TEXT NOT NULL, " +
                        "cpName Text NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "phone TEXT NOT NULL, " +
                        "fight_num INTEGER, " +
                        "freight_num INTEGER, " +
                        "special_num INTEGER, " +
                        "year TEXT NOT NULL, " +
                        "month TEXT NOT NULL, " +
                        "day TEXT NOT NULL, " +
                        "start_area TEXT NOT NULL, " +
                        "end_area TEXT NOT NULL, " +
                        "totalCost INTEGER NOT NULL );";

        db.execSQL(table);
        db.execSQL(businiess_table);
        db.execSQL(reservation_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if(newVersion == oldVersion){
            db.execSQL("DROP TABLE IF EXISTS customer");
            db.execSQL("DROP TABLE IF EXISTS business");
            db.execSQL("DROP TABLE IF EXISTS reservation");
            onCreate(db);
       // }
    }
}
