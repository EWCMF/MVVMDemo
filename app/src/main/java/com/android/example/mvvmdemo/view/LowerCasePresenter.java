package com.android.example.mvvmdemo.view;

import com.android.example.mvvmdemo.model.Model;
import java.util.Observable;
import java.util.Observer;

public class LowerCasePresenter extends Observable {
    private String string;
    private Model model;

    public LowerCasePresenter(Model model) {
        this.model = model;
        observeModel(this.model);
        string = this.model.getInput().toLowerCase();
    }

    private void observeModel(Model model) {
        model.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (o instanceof Model) {
                    String data = ((Model) o).getInput();
                    string = data.toLowerCase();

                    LowerCasePresenter.super.setChanged();
                    LowerCasePresenter.super.notifyObservers();
                }
            }
        });
    }

    String getString() {
        return string;
    }

    public void setString(String string) {
        model.setInput(string);
    }
}
