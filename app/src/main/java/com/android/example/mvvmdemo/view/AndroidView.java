package com.android.example.mvvmdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.mvvmdemo.R;
import com.android.example.mvvmdemo.model.Model;
import com.android.example.mvvmdemo.persistence.DbHelper;

public class AndroidView extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Model model = new Model();
    private LowerCasePresenter lowerCasePresenter = new LowerCasePresenter();
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);

        model.addObserver(lowerCasePresenter);

        dbHelper = new DbHelper(this);

        Cursor cursor = dbHelper.getAllData();
        cursor.moveToNext();

        textView.setText(cursor.getString(cursor.getColumnIndex("currentString")));

        Thread thread = new Thread() {
            @Override
            public void run() {
                setPriority(MIN_PRIORITY);
                while(!isInterrupted()) {

                    try {
                        Thread.sleep(1000);
                        Cursor cursor = dbHelper.getAllData();
                        cursor.moveToNext();

                        if (!textView.getText().equals(cursor.getString(cursor.getColumnIndex("currentString")))) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Cursor cursor = dbHelper.getAllData();
                                    cursor.moveToNext();
                                    textView.setText(cursor.getString(cursor.getColumnIndex("currentString")));
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


    public void changeText(View view) {
        model.setInput(editText.getText().toString());
        Cursor cursor = dbHelper.getAllData();
        cursor.moveToNext();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_2, lowerCasePresenter.getString());

        String selection = DbHelper.COL_2 + " LIKE ?";
        String[] selectionArgs = {cursor.getString(cursor.getColumnIndex("currentString"))};

        db.update(
                DbHelper.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
