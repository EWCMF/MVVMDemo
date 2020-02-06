package com.android.example.mvvmdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.mvvmdemo.R;
import com.android.example.mvvmdemo.model.Model;

import java.util.Observable;
import java.util.Observer;

public class AndroidView extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);

        model.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (o instanceof Model) {
                    textView.setText(((Model) o).getInput());
                }
            }
        });
    }
    
    public void changeText(View view) {
        model.setInput(editText.getText().toString());
    }
}
