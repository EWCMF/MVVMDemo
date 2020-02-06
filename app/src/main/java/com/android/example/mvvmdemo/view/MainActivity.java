package com.android.example.mvvmdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.mvvmdemo.R;
import com.android.example.mvvmdemo.model.EditTextInput;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private EditTextInput editTextInput = new EditTextInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);

        editTextInput.addObserver(new MyObserver());
    }
    
    public void changeText(View view) {
        editTextInput.setInput(editText.getText().toString());
    }

    public class MyObserver implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            Log.v("MyObserver", "Used update");
            textView.setText(arg.toString());
        }
    }
}
