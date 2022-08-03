package com.example.testhexagongame.trash;

import com.example.testhexagongame.Points.PointsManager;
import com.example.testhexagongame.piece.PieceFlippable;
import com.example.testhexagongame.piece.PieceManager;
import com.example.testhexagongame.utils.Observer;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Trash<T extends PieceFlippable> {
    private final Observer<Boolean> trashControl = new Observer<>(false);
    private final Observer<Integer> trashCost = new Observer<>(50);
    private final PointsManager pointsManager;
    private final PieceManager<T> pieceManager;
    private final Function1<String, Unit> onSendMessage;

    public Trash(PointsManager pointsManager, PieceManager<T> pieceManager, Function1<String, Unit> onSendMessage) {
        this.pointsManager = pointsManager;
        this.pieceManager = pieceManager;
        this.onSendMessage = onSendMessage;
    }

    public void subscribeOnEnable(Function1<Boolean, Unit> callback) {
        trashControl.subscribe(callback);
    }

    public void subscribeOnCostChange(Function1<Integer, Unit> callback) {
        trashCost.subscribe(callback);
    }

    public void onEnable() {
        if (!trashControl.getValue() && pointsManager.getPoints() - trashCost.getValue() >= 0) {
            trashControl.setValue(true);
        }
        else if (trashControl.getValue()) trashControl.setValue(false);
        else if (!trashControl.getValue() && pointsManager.getPoints() - trashCost.getValue() < 0) {
            onSendMessage.invoke("You can't use the trash... Get more points");
        }
    }

    public void delete(T piece) {
        if (trashControl.getValue()) {
            pieceManager.deletePiece(piece);
            pointsManager.decreasePoints(trashCost.getValue());
            trashCost.setValue(trashCost.getValue()+50);
            trashControl.setValue(false);
        }
    }
}
