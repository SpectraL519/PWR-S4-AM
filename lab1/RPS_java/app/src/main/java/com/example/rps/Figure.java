package com.example.rps;



public class Figure {
    private String value;
    private String beats;


    public Figure (String value, String beats) {
        this.value = value;
        this.beats = beats;
    }

    public String getValue() {
        return this.value;
    }

    public int hierarchy (String figure) {
        if (figure.equals(this.beats))
            return 1;

        if (figure.equals(this.value))
            return 0;

        return -1;
    }
}
