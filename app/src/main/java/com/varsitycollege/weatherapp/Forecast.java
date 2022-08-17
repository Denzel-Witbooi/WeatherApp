package com.varsitycollege.weatherapp;

public class Forecast {
    private String fDate;
    private String fMax;
    private String fMin;
    private String fLink;

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public void setfMax(String fMax) {
        this.fMax = fMax;
    }

    public void setfMin(String fMin) {
        this.fMin = fMin;
    }

    public void setfLink(String fLink) {
        this.fLink = fLink;
    }


    public String getfDate() {
        return fDate;
    }

    public String getfMax() {
        return fMax;
    }

    public String getfMin() {
        return fMin;
    }

    public String getfLink() {
        return fLink;
    }

}
