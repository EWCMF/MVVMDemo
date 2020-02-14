package com.android.example.mvvmdemo.view;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.mvvmdemo.R;
import com.android.example.mvvmdemo.persistence.SQLiteDB;
import com.android.example.mvvmdemo.persistence.FirebaseDB;

import java.util.Observable;
import java.util.Observer;

public class AndroidView extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private LowerCasePresenter lowerCasePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);

        FirebaseDB firebaseDB = new FirebaseDB(null);
        lowerCasePresenter = new LowerCasePresenter(firebaseDB.getModel());

        lowerCasePresenter.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (o instanceof LowerCasePresenter) {
                    String string = ((LowerCasePresenter) o).getString();
                    textView.setText(string);
                }
            }
        });

        textView.setText(lowerCasePresenter.getString());
    }


    public void changeText(View view) {
        lowerCasePresenter.setString(editText.getText().toString());
    }
}
