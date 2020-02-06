package com.android.example.mvvmdemo.model;
import java.util.Observable;

public class Model extends Observable {
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        setChanged();
        notifyObservers(input);
        this.input = input;
    }
}