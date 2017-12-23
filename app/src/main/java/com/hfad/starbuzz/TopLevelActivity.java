package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
            new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> listView,
                                        View v,
                                        int position,
                                        long id) {
                    if (position == 0) {
                        Intent intent = new Intent(TopLevelActivity.this,
                                                   DrinkCategoryActivity.class);
                        startActivity(intent);
                    }
                }
            };
        //Add the listener to the list view
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        //Заполнение list_favorites чере курсор
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try {
            SQLiteOpenHelper starbuzzHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzHelper.getReadableDatabase();
            favoritesCursor = db.query(
                    "DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null,
                    null,
                    null,
                    null);

            CursorAdapter favoritesAdapter = new SimpleCursorAdapter(
                    TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);

            listFavorites.setAdapter(favoritesAdapter);
            }
        catch (SQLException e){

            Toast toast = Toast.makeText(this, " - Не выводит любимые напитки - ", Toast.LENGTH_LONG);
            toast.show();
        }

        //Переход в DrinkActivity
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)l);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try {
            SQLiteOpenHelper starbuzzHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzHelper.getReadableDatabase();
            Cursor newCursor = db.query(
                    "DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null,
                    null,
                    null,
                    null);

            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor = newCursor;
        }
        catch (SQLException e){
            Toast toast = Toast.makeText(this, " - Не выводит любимые напитки - ", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
