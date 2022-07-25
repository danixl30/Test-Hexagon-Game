package com.example.testhexagongame.Points;

public class PointsPolity implements PointsCalculator {
    @Override
    public Integer calculate(int numRegions) {
        return Math.toIntExact(20 + Math.round(Math.pow(20, numRegions)));

    }
}
