package com.android.example.mvvmdemo.persistence;

import com.android.example.mvvmdemo.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;
import java.util.Observer;

public class FirebaseDB{
    private Model model;
    DatabaseReference ref;

    public FirebaseDB(Model model) {
        ref = FirebaseDatabase.getInstance().getReference("string");

        if (model == null) {
            this.model = new Model();
        }
        else {
            this.model = model;
            writeData(model.getInput());
        }
        observeModel();
        observeFirebase();
    }

    private void observeModel() {
        model.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (o instanceof Model) {
                    String data = ((Model) o).getInput();
                    writeData(data);
                }
            }
        });
    }

    private void observeFirebase() {
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                if (!data.equals(model.getInput()))
                    model.setInput(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addValueEventListener(dataListener);
    }

    public void writeData(String string) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("string");

        ref.setValue(string);
    }

    public Model getModel() {
        return model;
    }
}
