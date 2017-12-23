package com.hfad.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKNO = "drinkNo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        //Get the drink from the intent
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        try{
            StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();

            Cursor cursor = db.query("DRINK",
                                      new String[] {"NAME", "DESCRIPTION", "FAVORITE"},
                                      "_id = ?",
                                       new String[]{Integer.toString(drinkNo)},
                                       null,
                                       null,
                                       null
            );
            //Переход к записям
            if(cursor.moveToFirst()){
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
               // int photoId = cursor.getInt(3);
                  boolean isFavorite = (cursor.getInt(2)==1);
                //Populate the drink name
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);
                //Populate the drink description
                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);
                //Populate the drink image
              //  ImageView photo = (ImageView)findViewById(R.id.photo);
              //  photo.setImageResource(photoId);
               // photo.setContentDescription(descriptionText);
                CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        }
        catch (SQLException e){
            Toast toast = Toast.makeText(this, " - Нет данных из БД - ", Toast.LENGTH_LONG);
            toast.show();
        }



        //Drink drink = Drink.drinks[drinkNo];

        //Populate the drink image
        //ImageView photo = (ImageView)findViewById(R.id.photo);
        //photo.setImageResource(drink.getImageResourceId());
        //photo.setContentDescription(drink.getName());

        //Populate the drink name
        //TextView name = (TextView)findViewById(R.id.name);
        //name.setText(drink.getName());

        //Populate the drink description
        //TextView description = (TextView)findViewById(R.id.description);
        //description.setText(drink.getDescription());
    }

    public void onFaviriteClecked(View view) {

        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
        ContentValues contentValues = new ContentValues();
        contentValues.put("FAVORITE", favorite.isChecked());
        ///////////////////////////////////////
        StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
         try{
               db.update("DRINK",
                          contentValues,
                         "_id=?",
                          new String[]{Integer.toString(drinkNo)});
               db.close();

             Toast toast = Toast.makeText(this, " - Обновлен  FAVORITE - ", Toast.LENGTH_LONG);
             toast.show();

         }catch (SQLException e){
             Toast toast = Toast.makeText(this, " - Нет данных из БД Галочка - ", Toast.LENGTH_LONG);
             toast.show();
          }
    }
}
