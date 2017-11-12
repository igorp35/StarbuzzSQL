package com.hfad.starbuzz;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity {

    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listDrinks = getListView();
        try{
            StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("DRINK",
                                      new String[]{"_id", "NAME"},
                                      null,
                                      null,
                                      null,
                                      null,
                                      null);
            CursorAdapter cursorAdapter = new SimpleCursorAdapter(
                                                                  this,
                                                                  android.R.layout.simple_list_item_1,
                                                                  cursor,
                                                                  new String[]{"NAME"},
                                                                  new int[]{android.R.id.text1},
                                                                  0
                                                                  );
            listDrinks.setAdapter(cursorAdapter);
        }
        catch (SQLException e){
            Toast toast = Toast.makeText(this,"- НЕТ БД - ",Toast.LENGTH_SHORT);
            toast.show();
        }
      // ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this,
      //                                                             android.R.layout.simple_list_item_1,
      //                                                              Drink.drinks);
      // listDrinks.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView listView,
                                View itemView,
                                int position,
                                long id) {
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        cursor.close();
        db.close();
    }
}
