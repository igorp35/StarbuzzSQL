package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLDataException;

/**
 * Created by Home on 22.10.2017.
 */

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        myUpdateBD(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        myUpdateBD(sqLiteDatabase, oldVersion, newVersion);

    }

    private void myUpdateBD(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){

        if(oldVersion < 1) {
            sqLiteDatabase.execSQL(
                    "CREATE TABLE DRINK("
                            + "_id INTEGER PRIMARY KEY AUTOINCRIMENT,"
                            + "NAME TEXT,"
                            + "DESCRIPTION TEXT,"
                            + "IMAGE_RESURS_ID INTEGER);"
            );
            //Вставка данных
            insertDrink(sqLiteDatabase, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
            insertDrink(sqLiteDatabase, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
            insertDrink(sqLiteDatabase, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
        }

        if(oldVersion < 2) {

            sqLiteDatabase.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");

        }

    }

    private static void insertDrink(SQLiteDatabase sqLiteDatabase, String name,
                                    String description, int resurseId){

        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESURS_ID INTEGER", resurseId);
        sqLiteDatabase.insert("DRINK", null, drinkValues);
    }
}
