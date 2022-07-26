package com.example.testhexagongame.Points;

import com.example.testhexagongame.utils.Observer;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PointsManager {
    private final Observer<Integer> points = new Observer<>(0);
    private final PointsCalculator pointsCalculator;

    public PointsManager(PointsCalculator pointsCalculator) {
        this.pointsCalculator = pointsCalculator;
    }

    public Integer getPoints() {
        return points.getValue();
    }

    public void decreasePoints(Integer pointsToDecrease) {
        if (points.getValue() - pointsToDecrease >= 0) {
            points.setValue(points.getValue() - pointsToDecrease);
        }
    }

    public void increasePoints(int regions) {
        points.setValue(points.getValue() + pointsCalculator.calculate(regions));
    }

    public void subscribeOnPointsChange(Function1<Integer, Unit> callback) {
        points.subscribe(callback);
    }
}
