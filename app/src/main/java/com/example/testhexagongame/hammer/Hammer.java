package com.example.testhexagongame.hammer;

import com.example.testhexagongame.Points.PointsManager;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;
import com.example.testhexagongame.utils.Observer;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Hammer<T extends Shape, U> {
    private final PointsManager pointsManager;
    private final Observer<Boolean> hammerControl = new Observer<>(false);
    private final Observer<Integer> hammerCost = new Observer<>(100);
    private final U baseItem;
    private final Function1<String, Unit> onSendMessage;

    public Hammer(PointsManager pointsManager, U baseItem, Function1<String, Unit> onSendMessage) {
        this.pointsManager = pointsManager;
        this.baseItem = baseItem;
        this.onSendMessage = onSendMessage;
    }

    public void subscribeOnEnable(Function1<Boolean, Unit> callback) {
        hammerControl.subscribe(callback);
    }

    public void subscribeOnCostChange(Function1<Integer, Unit> callback) {
        hammerCost.subscribe(callback);
    }

    public void onEnable() {
        if (hammerControl.getValue()) hammerControl.setValue(false);
        else if (!hammerControl.getValue() && pointsManager.getPoints() - hammerCost.getValue() >= 0) {
            hammerControl.setValue(true);
        }
        else if (!hammerControl.getValue() && pointsManager.getPoints() - hammerCost.getValue() < 0) {
            onSendMessage.invoke("You can't use the hammer... Get more points");
        }
    }

    public void destroy(Box<T, U> box) {
        if (hammerControl.getValue() && box.getData() != baseItem) {
            box.setData(baseItem);
            pointsManager.decreasePoints(hammerCost.getValue());
            hammerCost.setValue(hammerCost.getValue()+100);
            hammerControl.setValue(false);
        }
    }
}
