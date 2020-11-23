package com.example.templater.tempBuilder;


import java.util.Arrays;
import java.util.List;

public enum Fields {
    narrow,
    average,
    wide;

    public List<Double> getNarrowParams() {
        return Arrays.asList(1.27, 1.27, 1.27, 1.27);
    }
    public List<Double> getAverageParams() {
        return Arrays.asList(1.91, 1.91, 2.54, 2.54);
    }
    public List<Double> getWideParams() {
        return Arrays.asList(2.5, 2.5, 3.0, 3.0);
    }
}
