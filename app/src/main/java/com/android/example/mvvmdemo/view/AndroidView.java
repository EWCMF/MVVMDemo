package com.android.example.mvvmdemo.view;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.mvvmdemo.R;
import com.android.example.mvvmdemo.model.Model;
import com.android.example.mvvmdemo.persistence.SQLiteDB;
import com.android.example.mvvmdemo.persistence.FirebaseDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AndroidView extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Model model = new Model();
    private LowerCasePresenter lowerCasePresenter = new LowerCasePresenter();
    private SQLiteDB sqLiteDB;
    private FirebaseDB firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);

        model.addObserver(lowerCasePresenter);

        firebaseDB = new FirebaseDB();

        DatabaseReference databaseReference = firebaseDB.getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textView.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }


    public void changeText(View view) {
        model.setInput(editText.getText().toString());
        firebaseDB.update(lowerCasePresenter.getString());
    }
}
