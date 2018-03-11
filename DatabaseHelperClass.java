package com.example.steven.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Steven on 3/8/2018.
 */

public class DatabaseHelperClass extends SQLiteOpenHelper
    {
    public static final String DATABASE_NAME = "Plant_List.db";
    public static final String TABLE_NAME = "PlantTable.db";
    public static final String IDNUMBER = "ID";
    public static final String PLANT_NAME = "NAME";
    public static final String SPECIES_NAME = "SPECIES";
    public static final String PICTURE = "PICTURE";
    public static final String FLOWER_COLOR = "FLOWER";
    public static final String SIZE = "SIZE";
    public static final String NATIVITY = "NATIVITY";
    public static final String DROUGHT_TOLERANCE = "DROUGHT";
    public static final String GPS = "GPS";
    public static final String LOCATION = "LOCATION";


    public DatabaseHelperClass(Context context)
    {
        super(context, DATABASE_NAME, null,1);
        SQLiteDatabase db =this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, PLANTNAME TEXT, SPECIESNAME TEXT, FLOWERCOLOR TEXT, SIZE TEXT, NAVITITY TEXT, DROUGHT TEXT, LOCATION TEXT, GPS TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
