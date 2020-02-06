package com.android.example.mvvmdemo.view;

import com.android.example.mvvmdemo.model.Model;
import java.util.Observable;
import java.util.Observer;

public class LowerCasePresenter implements Observer {
    private String string;

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Model) {
            string = ((Model) o).getInput().toLowerCase();
        }
    }

    String getString() {
        return string;
    }
}
