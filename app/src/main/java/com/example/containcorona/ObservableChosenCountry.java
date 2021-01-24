package com.example.containcorona;

import java.io.Serializable;
import java.util.Observable;

public class ObservableChosenCountry extends Observable implements Serializable {
    private String chosenCountry;

    public String getChosenCountry() {
        return chosenCountry;
    }

    public void setChosenCountry(String country) {
        this.chosenCountry = country;
        this.setChanged();
        this.hasChanged();
        this.notifyObservers(country);
    }
}
